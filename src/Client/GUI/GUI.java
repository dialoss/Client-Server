package Client.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;


public class GUI {
    public void start(Stage stage) throws IOException, InterruptedException {
        URL fxmlURL = Paths.get("C:\\Users\\dialoss\\IdeaProjects\\lab5\\src\\Client\\GUI\\text.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(fxmlURL);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("JavaFX Application");
        stage.setWidth(400);
        stage.setHeight(400);
        stage.show();

        Thread.sleep(1000);

    }
}
