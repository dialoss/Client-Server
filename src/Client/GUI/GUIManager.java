package Client.GUI;

import Client.UserInterface;
import javafx.stage.Stage;


public class GUIManager extends UserInterface {
    private final Stage stage;

    public GUIManager(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void start() {
        Controller controller = new Controller(this.requestCallback);
        GUI gui = new GUI(controller);
        try {
            gui.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
