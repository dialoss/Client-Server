package Server.ConnectionManagers;

import Common.Connection.ObjectIO;
import Common.Connection.Request;
import Common.Connection.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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

    @Override
    public void run() {
        Response response = null;
        while (true) {
            try {
                if (selector.select(3000) == 0) continue;
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> iter = selected.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel(); // используется для доступа к серверному каналу
                        SocketChannel client = serverChannel.accept(); // позволяет вашему серверу принять новое входящее соединение и дает вам возможность взаимодействовать с клиентом, используя этот SocketChannel
                        System.out.println("new client: " + client.socket().toString());
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        client.configureBlocking(false);

                        ByteBuffer buffer = ByteBuffer.allocate(10000);
                        client.read(buffer);

                        Request request = (Request) ObjectIO.readObject(buffer.array());
                        response = this.requestCallback.call(request);
                        System.out.println("request " + request.getCommandName());
                        client.register(selector, SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        System.out.println("Writing...");

                        SocketChannel client = (SocketChannel) key.channel();
                        client.configureBlocking(false);

                        ByteArrayOutputStream bos = ObjectIO.writeObject(response);
                        ByteBuffer b = ByteBuffer.wrap(bos.toByteArray());

                        while (b.hasRemaining()) {
                            client.write(b);
                        }

                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println("Response was sent to " + client.socket().toString());
                    }
                    iter.remove();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}