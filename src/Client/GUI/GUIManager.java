package Client.GUI;

import Client.UserInterface;
import Common.Connection.Response;
import Common.EventBus.Callback;
import javafx.stage.Stage;


public class GUIManager extends UserInterface {
    private Stage stage;

    public GUIManager(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void start() {
        GUI gui = new GUI();
        try {
            gui.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Callback<Response> getInterface() {
        return null;
    }
}
