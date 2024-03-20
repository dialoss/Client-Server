package Client.GUI;

import Client.UserInterface;


public class GUIManager extends UserInterface {
    @Override
    public void start() {
        Controller controller = new Controller(this.requestCallback);
        GUI gui = new GUI(controller);
        try {
            gui.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
