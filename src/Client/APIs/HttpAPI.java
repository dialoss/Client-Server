package Client.APIs;

import Server.Connection.Request;
import Server.Connection.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpAPI extends ClientAPI {
    public void request(Request request) throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://opossum-wanted-mammal.ngrok-free.app/request")).build();

        try {
            HttpResponse<byte[]> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            ByteArrayInputStream bis = new ByteArrayInputStream(httpResponse.body());
            ObjectInputStream objectInputStream = new ObjectInputStream(bis);
            objectInputStream.readObject();
            objectInputStream.close();
            Response response = (Response) objectInputStream.readObject();

            this.responseCallback.call(response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
