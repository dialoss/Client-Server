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
    public static JFrame mainWindow;
    public static JFrame authWindow;

    GUI(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        Browser browser = new Browser(this.controller);
        mainWindow = new JFrame();
        Component UI = browser.getBrowserUI();
        new JFXPanel();

        authWindow = new JFrame();

        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\dialoss\\IdeaProjects\\lab5\\src\\Client\\GUI\\auth.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fileInputStream);
            Scene scene = new Scene(root);
            JFXPanel panel = new JFXPanel();
            panel.setScene(scene);
            authWindow.add(panel);
            SceneController controller = loader.getController();
            controller.setRequestCallback(this.controller.request);
            controller.setOwner(scene.getWindow());
            controller.authCallback = () -> {
                authWindow.setVisible(false);
                mainWindow.setVisible(true);
                return null;
            };
        } catch (IOException e) {
        }
        authWindow.pack();
        authWindow.setSize(600, 600);
        authWindow.setLocation(400, 100);
        authWindow.setVisible(true);
        authWindow.setResizable(false);
        mainWindow.add(UI, BorderLayout.CENTER);

        mainWindow.pack();
        mainWindow.setSize(1300, 800);
        mainWindow.setLocation(400, 100);
        mainWindow.setVisible(false);

        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                browser.dispose();
            }
        });
    }
}
