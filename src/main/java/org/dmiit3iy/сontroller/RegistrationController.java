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

public class RegistrationController {
    private UserRepository userRepository;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField logintextField;
    @FXML
    public TextField fioTextField;

    @FXML
    public void RegistrationButton(ActionEvent actionEvent) {
        userRepository = new UserRepository();
        String password = passwordField.getText();
        String fio = fioTextField.getText();
        String login = logintextField.getText();

        try {
            User user = userRepository.post(fio,login,password);

            App.showMessage("Success", "the user: "+user.getLogin()+"  has been successfully registered", Alert.AlertType.INFORMATION);
            App.closeWindow(actionEvent);
        } catch (IOException e) {
            App.showMessage("Warning", "User is not added", Alert.AlertType.WARNING);
        } catch (IllegalArgumentException e){
            App.showMessage("Warning",e.getMessage(), Alert.AlertType.WARNING);
        }
        finally {
            passwordField.clear();
            fioTextField.clear();
            logintextField.clear();
        }
    }
}
