package Client.GUI;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class GUI {
    public void start(Stage stage) throws IOException, InterruptedException {
        URL html = Paths.get("C:\\Users\\dialoss\\IdeaProjects\\lab5\\src\\Client\\GUI\\test.html").toUri().toURL();

        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        engine.load(html.toExternalForm());

        VBox root = new VBox(webView);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("App");
        stage.setWidth(1300);
        stage.setHeight(800);
        stage.show();

        engine.setOnAlert((event) -> {
            JSObject w = (JSObject) engine.executeScript("window");
            w.setMember("bridge", controller);
        });
    }
}
