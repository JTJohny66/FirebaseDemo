package aydin.firebasedemo;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    void onRegisterClicked() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);

            UserRecord userRecord = DemoApp.fauth.createUser(request);
            messageLabel.setText("✅ Registered: " + userRecord.getEmail());

        } catch (FirebaseAuthException e) {
            messageLabel.setText("❌ Error: " + e.getMessage());
        }
    }

    @FXML
    void onBackClicked() throws Exception {
        DemoApp.setRoot("welcome-view");
    }
}
