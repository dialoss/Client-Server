package Server;

import Server.Commands.CommandManager;
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

public class ConnectionManager {
    ConnectionManager() throws IOException, Exception {
        int port = 3333;
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", port);

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(address);
        channel.configureBlocking(false);

        Selector selector = Selector.open();

        channel.register(selector, SelectionKey.OP_ACCEPT);
        Response r = null;

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
                    r = CommandManager.execute(request);

                    client.register(selector, SelectionKey.OP_WRITE);
                }
                if (key.isWritable()) {
                    System.out.println("Writing");

                    SocketChannel client = (SocketChannel) key.channel();
                    client.configureBlocking(false);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(r);
                    objectOutputStream.close();
                    ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }

                    client.register(selector, SelectionKey.OP_READ);
                    buffer.clear();
                    System.out.println("Request was sent to " + client.socket().toString());
                }
                iter.remove();
            }
        }
    }
}
