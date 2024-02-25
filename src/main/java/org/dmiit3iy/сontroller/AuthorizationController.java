package org.dmiit3iy.сontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.dmiit3iy.App;
import org.dmiit3iy.model.User;
import org.dmiit3iy.retrofit.UserRepository;

import java.io.IOException;
import java.net.ConnectException;
import java.util.prefs.Preferences;

public class AuthorizationController {
    Preferences preferences = Preferences.userRoot().node("ufc");
    private String username=preferences.get("userLogin","-1");
    private String password=preferences.get("userPassword", "-1");
    @FXML
    public PasswordField passwordFieldPassword;
    @FXML
    public TextField textAreaLogin;
    UserRepository userRepository;
    @FXML
    public void buttonLogin(ActionEvent actionEvent) {
        try {
            username = textAreaLogin.getText();
            password = passwordFieldPassword.getText();
            userRepository = new UserRepository(username,password);
            preferences.put("userLogin", username);
            preferences.put("userPassword", password);
            User user = null;
            user = userRepository.get();
            if (user != null) {
                preferences.putLong("userID", user.getId());
                App.openWindow("main.fxml", "Main window", null);
                App.closeWindow(actionEvent);
            } else {
                App.showMessage("Mistake", "Incorrect login or password", Alert.AlertType.ERROR);
            }
        } catch (ConnectException e) {
            App.showMessage("Warning", "There is no connection to the server", Alert.AlertType.WARNING);
        } catch (IOException e) {
            App.showMessage("Warning", e.getMessage(), Alert.AlertType.WARNING);
        }
    }
    @FXML
    public void buttonRegistration(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("registration.fxml","Registration",null);
    }
}
