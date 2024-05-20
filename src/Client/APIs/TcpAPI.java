package Client.APIs;

import Client.Exceptions.RequestError;
import Common.Connection.ObjectIO;
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
    static boolean connectionStarted = false;

    public void connect() {
        if (connectionStarted) return;
        connectionStarted = true;
        new Thread(() -> {
            while (true) {
                try {
                    socket = SocketChannel.open();
                    socket.connect(address);
                    socket.configureBlocking(false);
                    System.out.println("Connected to Server " + address.getAddress());
                    connectionStarted = false;
                    break;
                } catch (IOException e) {
                    System.out.println("Reconnecting to the server in %s ms".formatted(reconnectTimeout));
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }).start();
    }

    public Response request(Request request) throws RequestError, IOException {
        ByteArrayOutputStream bos = ObjectIO.writeObject(request);
        ByteBuffer b = ByteBuffer.wrap(bos.toByteArray());

        try {
            while (b.hasRemaining()) {
                this.socket.write(b);
            }
        } catch (IOException e) {
            b.clear();
            this.connect();
            throw new RequestError("No connection to the server");
        }
        b.clear();
        ByteBuffer capacity = ByteBuffer.allocate(4);
        int a = 0;
        while (a < 1) {
            a = this.socket.read(capacity);
        }

        capacity.position(0);
        int size = capacity.getInt();
        System.out.println("Buffer size received " + size);

        ByteBuffer buffer = ByteBuffer.allocate(size);

        while (buffer.position() < size) {
            this.socket.read(buffer);
        }
        try {
            Response r = (Response) ObjectIO.readObject(buffer.array());
            buffer.clear();
            return r;
        } catch (Exception e) {
            throw new RequestError("Failed to read response");
        }
    }
}