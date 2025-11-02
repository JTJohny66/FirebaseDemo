package aydin.firebasedemo;

import javafx.fxml.FXML;

public class WelcomeController {

    @FXML
    void onRegisterButtonClicked() throws Exception {
        DemoApp.setRoot("register-view");
    }

    @FXML
    void onLoginButtonClicked() throws Exception {
        DemoApp.setRoot("login-view");
    }
}
