package Client.GUI;

import javafx.scene.web.WebEngine;

public class JSBridge {
    Controller controller;

    JSBridge(WebEngine engine) {
        controller = new Controller(engine);
    }

    public Controller getController() {
        return this.controller;
    }
}
