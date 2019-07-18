package com.pavelk.uicontrollers;

import java.net.URL;
import java.util.*;


import com.pavelk.main.Cell;
import com.pavelk.main.Environment;
import com.pavelk.main.LogInfo;
import com.pavelk.model.CellsToKML;
import com.pavelk.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


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
    private ColorPicker colorSelect;

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
    public static Boolean cellsOrPDWindows = null;

    public static Environment environment = new Environment();

    @FXML
    void toMapButton(ActionEvent event) {

        ControllerMap controllerMap = new ControllerMap();
        List<Cell> celllisttokml = getcellsListfromTextArea();
        if (celllisttokml != null)
            if (celllisttokml.size() > 0) {
                controllerMap.loadYandexMap(cellsToKml.createHtml(celllisttokml));
            } else
                logInfo.setLogData("Cells wasn't find" + '\n');
    }

    @FXML
    void initialize() {
        cellsOrPDWindows = true;//if cellstoPD -> trueCellsorPDWindows = false;
        separatorcombobox.getItems().addAll(",", ";", "tab");
        separatorcombobox.getSelectionModel().select(0);
        colorSelect.setValue(Color.valueOf("ff00007d"));
        textOut.textProperty().bind(logInfo.logDataProperty());

    }

    @FXML
    public void openCellsDataClick(ActionEvent event) {
        cellsOrPDWindows = true;
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
        environment.setColor(colorSelect.getValue());
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
            List<Cell> celllisttokml = new ArrayList<>();
            for (int i = 0; i < cellsfromTextarea.length; i++) {
                if (cellsfromTextarea[i] != null || cellsfromTextarea[i] != "")
                    for (int j = 0; j < cellsToKml.getCellsList().size(); j++) {
                        if (bynameRadioButton.isSelected())
                            if (cellsToKml.getCellsList().get(j).getCellName().equalsIgnoreCase(cellsfromTextarea[i])) {
                                celllisttokml.add(cellsToKml.getCellsList().get(j));
                                break;
                            }
                        if (byCIRadioButton.isSelected())
                            if (cellsToKml.getCellsList().get(j).getCellId() == Integer.parseInt(cellsfromTextarea[i])) {
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
