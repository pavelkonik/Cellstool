package com.pavelk.uicontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.*;


import com.pavelk.main.Cell;
import com.pavelk.main.Environment;
import com.pavelk.main.LogInfo;
import com.pavelk.model.CellsToKML;
import com.pavelk.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class ControllerCellsToKML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonRun;

    @FXML
    private RadioButton byCIRadioButton;

    @FXML
    private ToggleGroup byCIorNamegroup;

    @FXML
    private RadioButton bynameRadioButton;

    @FXML
    private RadioButton fromFileRadioButton;

    @FXML
    private ToggleGroup fromFileorDB;

    @FXML
    private RadioButton fromDBRadioButton;

    @FXML
    private ColorPicker ColorSelect;

    @FXML
    private TextField sizeValue;

    @FXML
    private TextField beamValue;

    @FXML
    private ComboBox<String> separatorcombobox;

    @FXML
    private Button openDataButton;

    @FXML
    private TextArea textAreacellList;

    @FXML
    private TextArea textOut;

    @FXML
    private Button toMapButton;


    private Model cellsToKml = new CellsToKML();

    public static LogInfo logInfo = new LogInfo();
    public static Boolean CellsorPDWindows = null;

    public static Environment environment = new Environment();

    @FXML
    void toMapButton(ActionEvent event) {

        ControllerMap controllerMap = new ControllerMap();
        List<Cell> celllisttokml = getcellsListfromTextArea();
        if (celllisttokml != null)
            if (celllisttokml.size() > 0) {
           //     System.out.println(cellsToKml.createJson(celllisttokml));
                cellsToKml.createHtml(celllisttokml);
                controllerMap.loadYandexMap(cellsToKml.createHtml(celllisttokml));
               // new ControllerMap().loadYandexMap(cellsToKml.createHtml());

            } else
                logInfo.setLogData("Cells wasn't find" + '\n');

    }

    @FXML
    void initialize() {
        CellsorPDWindows = true;//if cellstoPD -> trueCellsorPDWindows = false;
        separatorcombobox.getItems().addAll(",", ";", "tab");
        separatorcombobox.getSelectionModel().select(0);
        ColorSelect.setValue(Color.valueOf("ff00007d"));
        textOut.textProperty().bind(logInfo.logDataProperty());

    }

    @FXML
    public void openCellsDataClick(ActionEvent event) {
        CellsorPDWindows = true;
        if (fromDBRadioButton.isSelected()) {
            cellsToKml.setCellsdatafromDB(true);
            cellsToKml.setCellsdatafromFile(false);
            cellsToKml.setMySQLConnection(true);

        }
        if (fromFileRadioButton.isSelected()) {
            cellsToKml.setCellsdatafromFile(true);
            cellsToKml.setCellsdatafromDB(false);
            cellsToKml.setSeparator(separatorcombobox.getValue());
        }
        cellsToKml.getCellDataForCalculate();

        if (cellsToKml.getCellsList().size() == 0) {
            logInfo.setLogData("No data for cells" + '\n');
            buttonRun.setDisable(true);
            toMapButton.setDisable(true);
        } else {
            buttonRun.setDisable(false);
            toMapButton.setDisable(false);
            logInfo.setLogData("Add cells for next action" + '\n');
        }

    //    environment = new Environment();
        getEnvironment();
    }

    public Environment getEnvironment() {
        if (beamValue.getText().matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$"))
            environment.setBeam(Double.parseDouble(beamValue.getText()));
        else return null;
        if (sizeValue.getText().matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$"))
            environment.setDistance(Double.parseDouble(sizeValue.getText()));
        else return null;
        environment.setColor(ColorSelect.getValue());
        return environment;
    }

    @FXML
    void RunCelltokmlClick(ActionEvent event) {
        if (cellsToKml.getCellsList().size() == 0) {
            logInfo.setLogData("Cells isn't" + '\n');
            return;
        }
        getEnvironment();

        List<Cell> celllisttokml;
        celllisttokml = getcellsListfromTextArea();
        if (celllisttokml != null)
            if (celllisttokml.size() > 0)
                cellsToKml.calculate(celllisttokml);
            else
                logInfo.setLogData("Cells wasn't find" + '\n');
    }

    public List<Cell> getcellsListfromTextArea() {
        if (textAreacellList.getText().equals("Add cells for next action") || textAreacellList.getText().equals("")) {
            return cellsToKml.getCellsList();
        } else {
            String[] cellsfromTextarea = textAreacellList.getText().split("\n");
            List<com.pavelk.main.Cell> celllisttokml = new ArrayList<>();
            for (int i = 0; i < cellsfromTextarea.length; i++) {
                if (cellsfromTextarea[i] != null || cellsfromTextarea[i] != "")
                    for (int j = 0; j < cellsToKml.getCellsList().size(); j++) {
                        if (bynameRadioButton.isSelected())
                            if (cellsToKml.getCellsList().get(j).getCellName().equalsIgnoreCase(cellsfromTextarea[i])) {
                                celllisttokml.add(cellsToKml.getCellsList().get(j));
                                break;
                            }
                        if (byCIRadioButton.isSelected())
                            if (cellsToKml.getCellsList().get(j).getCellID() == Integer.parseInt(cellsfromTextarea[i])) {
                                celllisttokml.add(cellsToKml.getCellsList().get(j));
                                break;
                            }
                    }
            }
            return celllisttokml;
        }
    }

    @FXML
    void fromFileRadioButtonSelect(ActionEvent event) {
        if (fromFileRadioButton.isSelected()) {
            separatorcombobox.setDisable(false);
        }
    }

    @FXML
    void fromDBRadioButtonSelect(ActionEvent event) {
        if (fromDBRadioButton.isSelected()) {
            separatorcombobox.setDisable(true);
        }
    }
}
