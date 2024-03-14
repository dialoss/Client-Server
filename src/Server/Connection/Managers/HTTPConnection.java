package Server.Connection.Managers;

import Server.Connection.Request;
import Server.Connection.Response;
import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.conf.JavaNgrokConfig;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class HTTPConnection extends ConnectionManager {
    final int port = 3000;
    HttpServer server = null;

    HTTPConnection() {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);

            final NgrokClient ngrokClient = new NgrokClient.Builder()
                    .withJavaNgrokConfig(new JavaNgrokConfig.Builder()
                            .withAuthToken("2cqm4VEMT2TfsVEXxSPZxG0VsuZ_79UMNoBkcRPVQECF8AAna").build()).build();

            final CreateTunnel createNamedTunnel = new CreateTunnel.Builder()
                    .withHostname("opossum-wanted-mammal.ngrok-free.app")
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
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(response);
            out.flush();

            HttpExchange exchange = (HttpExchange) this.clients.get(response.getClient());
            OutputStream os = exchange.getResponseBody();
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
        server.createContext("/app", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) {
                try {
                    InputStream inputStream = exchange.getRequestBody();
                    ByteArrayInputStream bis = new ByteArrayInputStream(inputStream.readAllBytes());
                    ObjectInputStream in = new ObjectInputStream(bis);
                    in.readObject();
                    Request request = (Request) in.readObject();
                    HTTPConnection.this.clients.put(request.getClient(), exchange);
                    requestCallback.call(request);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }
}