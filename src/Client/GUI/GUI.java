package Client.GUI;

import org.cef.CefApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
    Controller controller;

    GUI(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        Browser browser = new Browser(this.controller);
        JFrame frame = new JFrame();

        Component UI = browser.getBrowserUI();
        frame.add(UI, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(1300, 800);
        frame.setLocation(400, 100);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                browser.dispose();
            }
        });
    }
}
