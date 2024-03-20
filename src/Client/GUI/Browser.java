package Client.GUI;

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
        final CefBrowser browser = client.createBrowser("file:///C:\\Users\\dialoss\\IdeaProjects\\lab5\\src\\Client\\GUI\\test.html", false, false);

        this.browserUI = browser.getUIComponent();

        class MessageRouterHandler extends CefMessageRouterHandlerAdapter {
            @Override
            public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request, boolean persistent, CefQueryCallback callback) {
                String[] tokens = request.split(" ");
                try {
                    String result = controller.call(new BridgeRequest(tokens[0], tokens[1]));
                    callback.success(result);
                } catch (Exception e) {
                    callback.failure(0, e.getMessage());
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