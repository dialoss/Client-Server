package Client.APIs;

import Common.Connection.Request;
import Common.Connection.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TcpAPI extends ClientAPI {
    SocketChannel socket;
    static int port = 3003;
    static int reconnectTimeout = 3000;
    static InetSocketAddress address = new InetSocketAddress("127.0.0.1", port);

    public TcpAPI() {
        connect(0);
    }

    public void connect(int reconnects) {
        try {
            socket = SocketChannel.open();
            socket.connect(address);
            socket.configureBlocking(false);
            System.out.println("Connected to Server " + address.getAddress());
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Reconnecting to the server in %s ms".formatted(reconnectTimeout));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            connect(reconnects + 1);
        }
    }

    public Response request(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(request);
        objectOutputStream.flush();
        objectOutputStream.close();
        ByteBuffer b = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

        while (b.hasRemaining()) {
            this.socket.write(b);
        }
        b.clear();
        ByteBuffer buffer = ByteBuffer.allocate(100000);

        int a = this.socket.read(buffer);
        System.out.println(a);
        while (a < 1) {
            a = this.socket.read(buffer);
        }

        ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
        ObjectInputStream oi = new ObjectInputStream(bi);
        try {
            return (Response) oi.readObject();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}