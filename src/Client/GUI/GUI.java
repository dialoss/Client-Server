package Client.GUI;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.cef.CefApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;

public class GUI {
    Controller controller;

    GUI(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        Browser browser = new Browser(this.controller);
        JFrame frame = new JFrame();
        Component UI = browser.getBrowserUI();
        new JFXPanel();

        JFrame auth = new JFrame();

        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\dialoss\\IdeaProjects\\lab5\\src\\Client\\GUI\\auth.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fileInputStream);
            Scene scene = new Scene(root);
            JFXPanel panel = new JFXPanel();
            panel.setScene(scene);
            auth.add(panel);
            SceneController controller = loader.getController();
            controller.setRequestCallback(this.controller.request);
            controller.setOwner(scene.getWindow());
            controller.authCallback = () -> {
                auth.setVisible(false);
                frame.setVisible(true);
                return null;
            };
        } catch (IOException e) {
        }
        auth.pack();
        auth.setSize(600, 600);
        auth.setLocation(400, 100);
        auth.setVisible(true);
        frame.add(UI, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(1300, 800);
        frame.setLocation(400, 100);
        frame.setVisible(false);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                browser.dispose();
            }
        });
    }
}
