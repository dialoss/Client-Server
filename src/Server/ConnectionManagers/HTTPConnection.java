package Server.ConnectionManagers;

import Common.Connection.ObjectIO;
import Common.Connection.Request;
import Common.Connection.Response;
import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.conf.JavaNgrokConfig;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HTTPConnection extends ConnectionManager {
    final int port = 3000;
    HttpServer server = null;

    public HTTPConnection() {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);

            final NgrokClient ngrokClient = new NgrokClient.Builder()
                    .withJavaNgrokConfig(new JavaNgrokConfig.Builder()
                            .withAuthToken("2cqm4VEMT2TfsVEXxSPZxG0VsuZ_79UMNoBkcRPVQECF8AAna").build()).build();

            final CreateTunnel createNamedTunnel = new CreateTunnel.Builder()
                    .withHostname("gnu-stirring-wolf.ngrok-free.app")
                    .withAddr(port)
                    .build();
            ngrokClient.connect(createNamedTunnel);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void response(Response response) {
        try {
            HttpExchange exchange = (HttpExchange) this.clients.get(response.getClient());
            OutputStream os = exchange.getResponseBody();

            ByteArrayOutputStream bos = ObjectIO.writeObject(response);
            exchange.sendResponseHeaders(200, bos.toByteArray().length);
            os.write(bos.toByteArray());
            exchange.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        server.start();
        server.createContext("/request", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) {
                try {
                    InputStream inputStream = exchange.getRequestBody();
                    Request request = (Request) ObjectIO.readObject(inputStream.readAllBytes());
                    HTTPConnection.this.clients.put(request.getClient().sessionId, exchange);
                    requestCallback.call(request);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }
}