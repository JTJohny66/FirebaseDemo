package aydin.firebasedemo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    void onLoginClicked() throws Exception {
        try {
            // This checks if the user exists in Firebase
            FirebaseAuth.getInstance().getUserByEmail(emailField.getText().trim());

            // If exists → go to the data screen
            DemoApp.setRoot("primary");

        } catch (FirebaseAuthException e) {
            // If email not found → show message
            messageLabel.setText("❌ User not found.");
        }
    }

    @FXML
    void onBackClicked() throws Exception {
        DemoApp.setRoot("welcome-view");
    }
}
