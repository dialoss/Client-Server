package Server.Connection.Managers;

import Server.Connection.Request;
import Server.Connection.Response;

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
    int port = 3000;
    Selector selector;

    public TCPConnection() {
        try {
            InetSocketAddress address = new InetSocketAddress("127.0.0.1", port);

            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.bind(address);
            channel.configureBlocking(false);

            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void response(Response response) {
        try {
            while (true) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> iter = selected.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isWritable()) {
                        try {
                            SocketChannel client = (SocketChannel) key.channel();
                            client.configureBlocking(false);

                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                            objectOutputStream.writeObject(response);
                            objectOutputStream.flush();
                            objectOutputStream.close();
                            ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

                            while (buffer.hasRemaining()) {
                                client.write(buffer);
                            }

                            client.register(selector, SelectionKey.OP_READ);
                            buffer.clear();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    iter.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                selector.select();
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
                        client.configureBlocking(false);

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        client.read(buffer);

                        ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
                        ObjectInputStream oi = new ObjectInputStream(bi);
                        Request request = (Request) oi.readObject();
                        this.clients.put(request.getClient(), key);
                        this.requestCallback.call(request);

                        client.register(selector, SelectionKey.OP_WRITE);
                    }
                    iter.remove();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}