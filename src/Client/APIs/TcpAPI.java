package Client.APIs;

import Common.Connection.Request;
import Common.Connection.ObjectIO;
import Common.Connection.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TcpAPI extends ClientAPI {
    SocketChannel socket;

    public TcpAPI() {
        int port = 3003;
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", port);

        try {
            socket = SocketChannel.open();
            socket.connect(address);
        } catch (IOException e) {
            System.out.println(e);
            try {
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Response request(Request request) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(ObjectIO.writeObject(request).toByteArray());

        while (buffer.hasRemaining()) {
            socket.write(buffer);
        }

        buffer = ByteBuffer.allocate(100000);

        int bytesRead = socket.read(buffer);
        while (bytesRead == -1) {
            bytesRead = socket.read(buffer);
        }

        try {
            return (Response) ObjectIO.readObject(buffer.array());
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}