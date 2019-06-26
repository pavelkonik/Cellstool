package com.pavelk.uicontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class ControllerMap {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    public static String html = "12312";

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @FXML
    private WebView webView;
    private WebEngine webEngine;

    public void loadYandexMap(String htmlstring) {
        html = htmlstring;
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/fxml/MapForm.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void initialize() {
        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.loadContent(html);
    }

}