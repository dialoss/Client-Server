package Server;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.conf.JavaNgrokConfig;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Tunnel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.logging.Logger;

public class NgrokHttpServer {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(NgrokHttpServer.class));

    public static void start() throws Exception {
        int port = 3000;
        final HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        final NgrokClient ngrokClient = new NgrokClient.Builder()
                .withJavaNgrokConfig(new JavaNgrokConfig.Builder()
                .withAuthToken("2cqm4VEMT2TfsVEXxSPZxG0VsuZ_79UMNoBkcRPVQECF8AAna").build()).build();

        final CreateTunnel createNamedTunnel = new CreateTunnel.Builder()
                .withHostname("opossum-wanted-mammal.ngrok-free.app")
                .withAddr(3000)
                .build();
        final Tunnel tunnel = ngrokClient.connect(createNamedTunnel);

        LOGGER.info(String.format("ngrok tunnel \"%s\" -> \"http://127.0.0.1:%d\"", tunnel.getPublicUrl(), port));

        server.start();
        server.createContext("/app", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                System.out.println(exchange);

                String x = new JSONObject(Map.of("ok",true)).toJSONString();
//                Response x = new Response("PRIVET", Status.OK);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                ObjectOutputStream out = new ObjectOutputStream(bos);
//                out.writeObject(x);
//                out.flush();

                OutputStream os = exchange.getResponseBody();
                exchange.sendResponseHeaders(200, x.getBytes().length);

                os.write(x.getBytes());
                exchange.close();
            }
        });
    }
}