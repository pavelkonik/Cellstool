package com.pavelk.uicontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.pavelk.model.db.DBAccess;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerDBaccessForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField userText;

    @FXML
    private TextField passwordText;

    @FXML
    private Button okButton;

    @FXML
    private TextField hostText;

    private String user;
    private String pass;
    private String host;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    private static final String IP_ADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public void setLogInfo(String s){
        if (ControllerCellsToKML.cellsOrPDWindows != null)
            if (ControllerCellsToKML.cellsOrPDWindows) {
                ControllerCellsToKML.logInfo.setLogData(s);
                ControllerCellsToKML.cellsOrPDWindows = null;
            }
        if (ControllerPDCellsToKML.cellsOrPDWindows != null)
            if (!ControllerPDCellsToKML.cellsOrPDWindows) {
                ControllerPDCellsToKML.logInfo.setLogData(s);
                ControllerPDCellsToKML.cellsOrPDWindows = null;
            }
    }

    @FXML
    void initialize() {
        DBAccess.dbHost = "";
        DBAccess.dbUser = "";
        DBAccess.dbPass = "";
    }

    @FXML
    public void DBaccessWindow() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/DBaccessForm.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    @FXML
    public void okButtonClick() {

        if (userText.getText().equals("")) {
            setLogInfo("User name is empty" + '\n');
            return;
        }

        if (!hostText.getText().matches(IP_ADDRESS_PATTERN) || hostText.getText().equals("")) {
            setLogInfo("Incorrect IP address" + '\n');
            return;
        }
        DBAccess.dbHost = hostText.getText();
        DBAccess.dbUser = userText.getText();
        DBAccess.dbPass = passwordText.getText();

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
