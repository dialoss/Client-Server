package Server.ConnectionManagers;

import Common.Connection.ObjectIO;
import Common.Connection.Request;
import Common.Connection.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class TCPConnection extends ConnectionManager {
    static int port = 3003;
    Selector selector;
    ServerSocketChannel channel;

    public TCPConnection() {
        try {
            InetSocketAddress address = new InetSocketAddress("127.0.0.1", port);

            channel = ServerSocketChannel.open();
            channel.bind(address);
            channel.configureBlocking(false);

            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            System.out.println(e);
            try {
                if (selector != null) selector.close();
                channel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void read(SelectionKey key) throws Exception {
        SocketChannel client = (SocketChannel) key.channel();
        client.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(10000);
        try {
            client.read(buffer);
        } catch (Exception e) {
            System.out.println(e);
            key.cancel();
            return;
        }

        Request request = (Request) ObjectIO.readObject(buffer.array());
        response = this.requestCallback.call(request);
        System.out.println("request " + request.getCommandName());
        client.register(selector, SelectionKey.OP_WRITE);
    }

    private void send(SelectionKey key) throws Exception {
        System.out.println("Writing...");

        SocketChannel client = (SocketChannel) key.channel();
        client.configureBlocking(false);

        ByteArrayOutputStream bos = ObjectIO.writeObject(response);
        ByteBuffer b = ByteBuffer.wrap(bos.toByteArray());
        ByteBuffer capacity = ByteBuffer.allocateDirect(4);
        capacity.putInt(b.limit());
        capacity.position(0);
        while (capacity.hasRemaining()) {
            client.write(capacity);
        }
        while (b.hasRemaining()) {
            client.write(b);
        }
        System.out.println("Buffer send size " + capacity.limit());
        capacity.clear();
        b.clear();
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("Response was sent to " + client.socket().toString());
    }

    private void accept(SelectionKey key) throws Exception {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel client = serverChannel.accept();
        System.out.println("new client: " + client.socket().toString());
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        Response response = null;
        while (true) {
            try {
                if (selector.select() == 0) continue;
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> iter = selected.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        send(key);
                    }
                    iter.remove();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}