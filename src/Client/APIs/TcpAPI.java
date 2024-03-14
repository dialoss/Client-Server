package Client.APIs;

import Server.Connection.Request;
import Server.Connection.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TcpAPI extends ClientAPI {
    SocketChannel socket;

    public TcpAPI() {
        int port = 3000;
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", port);

        try {
            socket = SocketChannel.open();
            socket.connect(address);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void request(Request request) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(ObjectIO.writeObject(request).toByteArray());

        while (buffer.hasRemaining()) {
            socket.write(buffer);
        }

        buffer = ByteBuffer.allocate(2024);

        socket.read(buffer);

        int bytesRead = socket.read(buffer);
        while (bytesRead == -1) {
            socket.read(buffer);
            bytesRead = socket.read(buffer);
        }

        try {
            Response response = (Response) ObjectIO.readObject(buffer.array());
            System.out.println("Server answer: \n" + response.getBody());
            this.responseCallback.call(response);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}