package org.dmiit3iy.сontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.dmiit3iy.App;
import org.dmiit3iy.model.User;
import org.dmiit3iy.model.UserFile;
import org.dmiit3iy.retrofit.FileUploadRepository;
import org.dmiit3iy.retrofit.UserFileRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

public class MainController {
    @FXML
    public TextField pathTextField;
    @FXML
    public TableView tableViewFiles;
    Preferences preferences = Preferences.userRoot().node("ufc");

    private FileUploadRepository fileUploadRepository;
    private UserFileRepository userFileRepository;
    public File pathFile;
    private String login = preferences.get("userLogin", "-1");
    private String password = preferences.get("userPassword", "-1");
    private String userId = preferences.get("userID", "-1");


    public void initialize() {
        userFileRepository = new UserFileRepository(login, password);
        try {
            List<UserFile> list = userFileRepository.get();
           // List<UserFile> list = userFileRepository.get(Long.parseLong(userId));
            ObservableList<UserFile> data = FXCollections.observableArrayList(list);

            TableColumn<UserFile, String> fileNameCol = new TableColumn<>("File name");
            fileNameCol.setCellValueFactory(new PropertyValueFactory<>("filename"));

            TableColumn<UserFile, String> versionCol = new TableColumn<>("Version");
            versionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
            tableViewFiles.setItems(data);
            tableViewFiles.getColumns().setAll(fileNameCol, versionCol);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


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
            fileUploadRepository = new FileUploadRepository(login, password);
            if (pathFile != null) {
                fileUploadRepository.uploadFile(Long.parseLong(userId), pathFile);
                App.showMessage("Success", "File uploaded successfully", Alert.AlertType.INFORMATION);
                List<UserFile> list = userFileRepository.get();
                //List<UserFile> list = userFileRepository.get(Long.parseLong(userId));
                ObservableList<UserFile> data = FXCollections.observableArrayList(list);
                tableViewFiles.setItems(data);
            } else {
                App.showMessage("Warning", "Select the file before sending", Alert.AlertType.WARNING);
            }
        } catch (IOException e) {
            App.showMessage("Warning", e.getMessage(), Alert.AlertType.WARNING);
        } finally {
            pathTextField.clear();
        }
    }

    public void downloadButton(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<UserFile> selectionModel = tableViewFiles.getSelectionModel();
        if (selectionModel.getSelectedItem() != null) {
            UserFile userFile = selectionModel.getSelectedItem();
            try {
                fileUploadRepository = new FileUploadRepository(login, password);
                fileUploadRepository.downloadFile(userFile.getFilename(), Long.parseLong(userId), userFile.getVersion());
                App.showMessage("Success", "The file has been successfully uploaded to the directory:" +
                        "C:\\client\\downloaded", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                App.showMessage("Warning", e.getMessage(), Alert.AlertType.WARNING);
            }
        } else {
            App.showMessage("Warning", "Select the file to download it", Alert.AlertType.WARNING);
        }


    }
}
