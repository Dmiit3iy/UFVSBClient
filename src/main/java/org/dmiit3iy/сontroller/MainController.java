package org.dmiit3iy.сontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.dmiit3iy.App;
import org.dmiit3iy.retrofit.FileUploadRepository;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class MainController {
    @FXML
    public TextField pathTextField;
    Preferences preferences = Preferences.userRoot().node("ufc");

    private FileUploadRepository fileUploadRepository;

    public File pathFile;
    private String login = preferences.get("userLogin", "-1");
    private String password = preferences.get("userPassword", "-1");
    private String userId = preferences.get("userID", "-1");

    @FXML
    public void exitButton(ActionEvent actionEvent) {
        preferences.remove("userID");
        preferences.remove("userLogin");
        preferences.remove("userPassword");
        try {
            App.openWindow("authorization.fxml", "", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        App.closeWindow(actionEvent);
    }

    @FXML
    public void broseFileButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        pathFile = file;
        if (file == null) {
            return;
        } else {
            pathTextField.setText(file.getAbsolutePath());
        }

    }

    public void sendFileButton(ActionEvent actionEvent) {
        try {

            fileUploadRepository = new FileUploadRepository(login,password);
            fileUploadRepository.uploadFile(Long.parseLong(userId),pathFile);
        } catch (IOException e) {
            App.showMessage("Warning", e.getMessage(), Alert.AlertType.WARNING);
        }
    }
}
