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

    public void connect() {
        try {
            socket = SocketChannel.open();
            socket.connect(address);
            socket.configureBlocking(false);
            System.out.println("Connected to Server " + address.getAddress());
        } catch (IOException e) {
            System.out.println("Reconnecting to the server in %s ms".formatted(reconnectTimeout));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            connect();
        }
    }

    public Response request(Request request) throws RequestError, IOException {
        ByteArrayOutputStream bos = ObjectIO.writeObject(request);
        ByteBuffer b = ByteBuffer.wrap(bos.toByteArray());

        try {
            while (b.hasRemaining()) {
                this.socket.write(b);
            }
        } catch (IOException e) {
            throw new RequestError("No connection to the server");
        }
        b.clear();
        ByteBuffer buffer = ByteBuffer.allocate(100000);

        int a = this.socket.read(buffer);
        while (a < 1) {
            a = this.socket.read(buffer);
        }

        try {
            return (Response) ObjectIO.readObject(buffer.array());
        } catch (Exception e) {
            throw new RequestError("Failed to read response");
        }
    }
}