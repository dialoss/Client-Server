package Server;

import Common.Connection.ObjectIO;
import Common.Connection.Request;
import Common.Connection.Response;
import Server.Commands.CommandExecutor;

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

public class Test {
    Test() throws IOException {
        int port = 3003;
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", port); // ������� ����� ������ (IP-����� � ����)

        ServerSocketChannel channel = ServerSocketChannel.open(); // ����� ��� �������, ������� ������� ����� � ������� ������ ��� ��������
        channel.bind(address); // ������ ����� ������� �� ������������� ������
        channel.configureBlocking(false); // ������������� �����

        Selector selector = Selector.open();
        try {
            channel.register(selector, SelectionKey.OP_ACCEPT);

            Response response = null;
            while (true) {
                selector.select(); // ���������� ������, ��� ������ ������ � ��������. ���������, ���� �� ����� ������
                Set<SelectionKey> selectedKeys = selector.selectedKeys(); // �������� ������ ������ �� �������, ������� � ������while (iter.hasNext()) {
                Iterator<SelectionKey> iter = selectedKeys.iterator(); // �������� �������� ������

                while (iter.hasNext()) {

                    SelectionKey key = iter.next();
                    try {
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel(); // ������������ ��� ������� � ���������� ������
                            SocketChannel client = serverChannel.accept(); // ��������� ������ ������� ������� ����� �������� ���������� � ���� ��� ����������� ����������������� � ��������, ��������� ���� SocketChannel
                            System.out.println("new client: " + client.socket().toString());
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            client.configureBlocking(false);

                            ByteBuffer buffer = ByteBuffer.allocate(10000);
                            client.read(buffer);

                            Request request = (Request) ObjectIO.readObject(buffer.array());
                            response = CommandExecutor.execute(request);
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
                    } catch (IOException e) {
                        System.out.println(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Something wrong with server");
        }
    }
}
