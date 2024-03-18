package Client.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;


public class GUI {

    public void start(Stage stage) throws IOException, InterruptedException {
        URL html = Paths.get("C:\\Users\\dialoss\\IdeaProjects\\lab5\\src\\Client\\GUI\\webview.html").toUri().toURL();

        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        engine.load(html.toExternalForm());

        VBox root = new VBox(webView);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.setTitle("App");
        stage.setWidth(400);
        stage.setHeight(400);
        stage.show();

    }
}
