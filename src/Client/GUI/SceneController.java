package Client.GUI;

import Client.UserManager;
import Common.Connection.ICallback;
import Common.Connection.Request;
import Common.Connection.UserClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.concurrent.Callable;


public class SceneController {
    ICallback<Request> request;
    Window owner;
    Callable authCallback;

    public void setRequestCallback(ICallback<Request> request) {
        this.request = request;
    }

    public void setOwner(Window owner) {
        this.owner = owner;
    }

    @FXML
    private TextField authLogin;

    @FXML
    private TextField authPassword;

    @FXML
    private TextField regName;

    @FXML
    private TextField regLogin;

    @FXML
    private TextField regPassword;

    private boolean isLogin = true;

    public void setLogin() {
        isLogin = true;
    }

    public void setRegister() {
        isLogin = false;
    }

    public void auth() throws Exception {
        System.out.println("AUTH");
        String type = isLogin ? "login" : "register";
        if (isLogin) {
            auth(new UserInfo("", authLogin.getText(), authPassword.getText()), type);
        } else {
            auth(new UserInfo(regName.getText(), regLogin.getText(), regPassword.getText()), type);
        }
    }

    private void popup(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Ошибка");
        alert.setContentText(text);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(this.owner);
        alert.initStyle(StageStyle.UTILITY);
        alert.show();
    }

    private void auth(UserInfo data, String type) {
        UserManager.setClient(new UserClient(data).withId(0));
        try {
            request.call(new Request(type)
                    .withArgument("login", data.login())
                    .withArgument("name", data.name())
                    .withArgument("password", data.password()));
            this.authCallback.call();
        } catch (Exception e) {
            popup(e.getMessage());
        }
//        if (data.remember()) UserManager.setCookie();
//        r.setBody(UserManager.getClient());
    }
}
