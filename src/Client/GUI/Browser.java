package Client.GUI;

import Common.Models.MObject;
import Common.Tools;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefAppHandlerAdapter;
import org.cef.handler.CefMessageRouterHandlerAdapter;

import javax.swing.*;
import java.awt.*;

class BridgeData {
    String method;
    MObject data;
}

public class Browser extends JFrame {
    private final Component browserUI;

    public Browser(Controller controller) {
        CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(CefAppState state) {
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });

        final CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = false;

//        settings.log_severity = CefSettings.LogSeverity.LOGSEVERITY_DISABLE;
        final CefApp cefApp = CefApp.getInstance(settings);

        final CefClient client = cefApp.createClient();
        final CefBrowser browser = client.createBrowser("file:///C:\\Users\\dialoss\\IdeaProjects\\lab5\\src\\Client\\GUI\\app.html", false, false);

        this.browserUI = browser.getUIComponent();

        class MessageRouterHandler extends CefMessageRouterHandlerAdapter {
            @Override
            public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request, boolean persistent, CefQueryCallback callback) {
                try {
                    new Thread(() -> {
                        try {
                            BridgeData data = (BridgeData) Tools.parse(request, BridgeData.class);
                            String result = controller.call(new BridgeRequest(data.method, Tools.stringify(data.data)));
                            callback.success(result);
                        } catch (Exception e) {
                            callback.failure(0, e.getMessage());
                        }
                    }).start();
                } catch (Exception e) {
                }
                return true;
            }
        }

        CefMessageRouter msgRouter = CefMessageRouter.create();
        msgRouter.addHandler(new MessageRouterHandler(), true);
        client.addMessageRouter(msgRouter);
    }

    public Component getBrowserUI() {
        return browserUI;
    }
}