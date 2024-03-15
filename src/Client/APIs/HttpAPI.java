package Client.APIs;

import Common.Connection.ObjectIO;
import Common.Connection.Request;
import Common.Connection.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpAPI extends ClientAPI {
    public void request(Request request) throws IOException {
        HttpClient client = HttpClient.newHttpClient();

        ByteArrayOutputStream bos = ObjectIO.writeObject(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://gnu-stirring-wolf.ngrok-free.app/request"))
                .POST(HttpRequest.BodyPublishers.ofByteArray(bos.toByteArray()))
                .build();
        try {
            HttpResponse<byte[]> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            Response response = (Response) ObjectIO.readObject(httpResponse.body());

            this.responseCallback.call(response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}