package aydin.firebasedemo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class PrimaryController {

    @FXML private TextField nameTextField;
    @FXML private TextField ageTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField passwordTextField;

    @FXML private TextArea outputTextArea;

    @FXML private Button readButton;
    @FXML private Button writeButton;
    @FXML private Button registerButton;
    @FXML private Button switchSecondaryViewButton;

    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    @FXML
    void readButtonClicked(ActionEvent event) {
        readFirebase();
    }

    @FXML
    void writeButtonClicked(ActionEvent event) {
        addData();
    }

    @FXML
    void registerButtonClicked(ActionEvent event) {
        String email = nameTextField.getText().trim() + "@gmail.com";
        String password = passwordTextField.getText().trim();

        if (password.length() < 6) {
            outputTextArea.setText("Password must be at least 6 characters.");
            return;
        }

        registerUser(email, password);
    }

    @FXML
    private void switchToSecondary() throws IOException {
        DemoApp.setRoot("secondary");
    }

    public boolean readFirebase() {
        key = false;
        ApiFuture<QuerySnapshot> future = DemoApp.fstore.collection("Persons").get();
        List<QueryDocumentSnapshot> documents;

        try {
            documents = future.get().getDocuments();
            if(documents.size() > 0) {
                outputTextArea.clear();
                listOfUsers.clear();
                for (QueryDocumentSnapshot document : documents) {
                    outputTextArea.appendText(
                            document.getData().get("Name") + " , Age: " +
                                    document.getData().get("Age") + " , Phone: " +
                                    document.getData().get("Phone") + "\n"
                    );

                    person = new Person(
                            String.valueOf(document.getData().get("Name")),
                            Integer.parseInt(document.getData().get("Age").toString()),
                            String.valueOf(document.getData().get("Phone"))
                    );

                    listOfUsers.add(person);
                }
            }
            key = true;

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return key;
    }

    public boolean registerUser(String email, String password) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisabled(false);

        try {
            UserRecord userRecord = DemoApp.fauth.createUser(request);
            outputTextArea.setText("Created: " + userRecord.getUid());
            return true;
        } catch (FirebaseAuthException ex) {
            outputTextArea.setText("Error creating user.");
            return false;
        }
    }

    public void addData() {
        DocumentReference docRef = DemoApp.fstore.collection("Persons")
                .document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameTextField.getText());
        data.put("Age", Integer.parseInt(ageTextField.getText()));
        data.put("Phone", phoneTextField.getText());

        ApiFuture<WriteResult> result = docRef.set(data);
    }
}
