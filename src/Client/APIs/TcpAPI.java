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
        int port = 3333;
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", port);

        try {
            socket = SocketChannel.open();
            socket.connect(address);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void request(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        objectOutputStream.close();
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

        while (buffer.hasRemaining()) {
            socket.write(buffer);
        }

        objectOutputStream.flush();
        buffer = ByteBuffer.allocate(2024);

        socket.read(buffer);

        int bytesRead = socket.read(buffer);
        while (bytesRead == -1) {
            socket.read(buffer);
            bytesRead = socket.read(buffer);
        }

        ByteArrayInputStream bi = new ByteArrayInputStream(buffer.array());
        ObjectInputStream oi = new ObjectInputStream(bi);
        System.out.println(oi.available());

        try {
            Response response = (Response) oi.readObject();
            System.out.println("Server answer: \n" + response.getBody());
            this.responseCallback.call(response);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
