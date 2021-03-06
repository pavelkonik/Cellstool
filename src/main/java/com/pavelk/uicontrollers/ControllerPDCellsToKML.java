package com.pavelk.uicontrollers;

import java.net.URL;
import java.util.*;


import com.pavelk.main.Cell;
import com.pavelk.main.Environment;
import com.pavelk.main.LogInfo;
import com.pavelk.model.Model;
import com.pavelk.model.PDCellsToKml;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControllerPDCellsToKML {

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
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private RadioButton fromFileRadioButton;

    @FXML
    private ToggleGroup fromFileorDBCells;

    @FXML
    private RadioButton fromDBRadioButton;

    @FXML
    private ComboBox<String> separatorcombobox;

    @FXML
    private Button openDataButton;

    @FXML
    private TextArea textAreatextOut;

    @FXML
    private TextArea textAreacellList;

    @FXML
    private TextField beamValue;

    @FXML
    private CheckBox agregateCheckBox;

    @FXML
    private Button toMapButton;

    public static LogInfo logInfo = new LogInfo();
    public static Boolean cellsOrPDWindows = null;

    public static Environment environment;
    private Model pdCellsToKml = new PDCellsToKml();

    @FXML
    void openCellsDataClick(ActionEvent event) {
        if (fromDBRadioButton.isSelected()) {
            pdCellsToKml.setCellsdatafromDB(true);
            pdCellsToKml.setCellsdatafromFile(false);
            pdCellsToKml.setMySQLConnection(true);
        }
        if (fromFileRadioButton.isSelected()) {
            pdCellsToKml.setCellsdatafromFile(true);
            pdCellsToKml.setCellsdatafromDB(false);
            pdCellsToKml.setSeparator(separatorcombobox.getValue());
        }
        pdCellsToKml.getCellDataForCalculate();
        //  pdCellsToKml.getCellsList()
//        if (Cell.CellList.size() == 0) {
        if (pdCellsToKml.getCellsList().size() == 0) {
            logInfo.setLogData("No data for cells" + '\n');
            buttonRun.setDisable(true);
            toMapButton.setDisable(true);
        } else {
            buttonRun.setDisable(false);
            toMapButton.setDisable(false);
            logInfo.setLogData("Add cells for next action" + '\n');
        }
    }

    @FXML
    void runPDtokml(ActionEvent event) {
        if (pdCellsToKml.getCellsList().size() == 0) {
            logInfo.setLogData("Cells isn't" + '\n');
            return;
        }
        environment = new Environment();
        if (beamValue.getText().matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$"))
            environment.setBeam(Double.parseDouble(beamValue.getText()));
        else return;

        if (startDate.getValue() == null) return;
        else
            ((PDCellsToKml) pdCellsToKml).setStartTime(startDate.getValue());
        if (endDate.getValue() == null) return;
        else
            ((PDCellsToKml) pdCellsToKml).setEndTime(endDate.getValue());

        if (agregateCheckBox.isSelected()) ((PDCellsToKml) pdCellsToKml).setAgregate(true);
        else ((PDCellsToKml) pdCellsToKml).setAgregate(false);

        List<Cell> celllisttokml;
        celllisttokml = getcellsListfromTextArea();
        if (celllisttokml != null)
            if (celllisttokml.size() > 0)
                pdCellsToKml.calculate(celllisttokml);
            else
                logInfo.setLogData("Cells wasn't find" + '\n');
    }

    private List<Cell> getcellsListfromTextArea() {
        if (textAreacellList.getText().equals("Add cells for next action") || textAreacellList.getText().equals("")) {
            return pdCellsToKml.getCellsList();
        } else {
            String[] cellsfromTextarea = textAreacellList.getText().split("\n");
            List<Cell> celllisttokml = new ArrayList<>();
            for (int i = 0; i < cellsfromTextarea.length; i++) {
                if (cellsfromTextarea[i] != null || cellsfromTextarea[i] != "")
                    for (int j = 0; j < pdCellsToKml.getCellsList().size(); j++) {
                        if (bynameRadioButton.isSelected())
                            if (pdCellsToKml.getCellsList().get(j).getCellName().equalsIgnoreCase(cellsfromTextarea[i])) {
                                celllisttokml.add(pdCellsToKml.getCellsList().get(j));
                                break;
                            }
                        if (byCIRadioButton.isSelected())
                            if (pdCellsToKml.getCellsList().get(j).getCellId() == Integer.parseInt(cellsfromTextarea[i])) {
                                celllisttokml.add(pdCellsToKml.getCellsList().get(j));
                                break;
                            }
                    }
            }
            return celllisttokml;
        }
    }

    @FXML
    void initialize() {
        cellsOrPDWindows = false;//if cellstoPD -> true
        separatorcombobox.getItems().addAll(",", ";", "tab");
        separatorcombobox.getSelectionModel().select(0);
        textAreatextOut.textProperty().bind(logInfo.logDataProperty());
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
