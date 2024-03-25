package Server.ConnectionManagers;

import Common.Connection.ObjectIO;
import Common.Connection.Request;
import Common.Connection.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPConnection extends ConnectionManager {
    int port = 3003;
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
                        ServerSocketChannel ch = (ServerSocketChannel) key.channel();
                        SocketChannel client = ch.accept();
                        if (client == null) continue;
                        System.out.println("new client: " + client.socket().toString());
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        System.out.println("Reading");

                        SocketChannel client = (SocketChannel) key.channel();
//                        if (!client.isConnected())
                        client.configureBlocking(false);

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        try {
                            client.read(buffer);
                        } catch (IOException e) {
                            channel.close();
                            iter.remove();
                            continue;
                        }

                        ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
                        ObjectInputStream oi = new ObjectInputStream(bi);
                        Request request = (Request) oi.readObject();
                        response = this.requestCallback.call(request);
                        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }
                    if (key.isWritable() && key.isValid()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        client.configureBlocking(false);

                        ByteBuffer buffer = ByteBuffer.wrap(ObjectIO.writeObject(response).toByteArray());

                        while (buffer.hasRemaining()) {
                            client.write(buffer);
                        }

                        client.register(selector, SelectionKey.OP_READ);
                    }

                    iter.remove();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}