package model;


import main.Cell;
import ui.ControllerCellsToKML;
import ui.ControllerPDCellsToKML;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Model {
    protected boolean isCellsdatafromDB;
    protected boolean isCellsdatafromFile;
    protected boolean isgetCellsDataFromCSV = true;
    private String separator;
    private List<Cell> cellsList = new ArrayList<>();
    public boolean isMySQLConnection;

    public DatabaseHandler dbHandler;// = new DatabaseHandler();

    public List<Cell> getCellsList() {
        return cellsList;
    }

    public void setCellsList(List<Cell> cellsList) {
        this.cellsList = cellsList;
    }

    public boolean isMySQLConnection() {
        return isMySQLConnection;
    }

    public void setMySQLConnection(boolean mySQLConnection) {
        isMySQLConnection = mySQLConnection;
    }

    public boolean isCellsdatafromDB() {
        return isCellsdatafromDB;
    }

    public void setCellsdatafromDB(boolean cellsdatafromDB) {
        isCellsdatafromDB = cellsdatafromDB;
    }

    public boolean isCellsdatafromFile() {
        return isCellsdatafromFile;
    }

    public void setCellsdatafromFile(boolean cellsdatafromFile) {
        isCellsdatafromFile = cellsdatafromFile;
    }

    public boolean isIsgetCellsDataFromCSV() {
        return isgetCellsDataFromCSV;
    }

    public void setIsgetCellsDataFromCSV(boolean isgetCellsDataFromCSV) {
        this.isgetCellsDataFromCSV = isgetCellsDataFromCSV;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }


    public void getCellDataForCalculate() {
        isMySQLConnection = true;
        if (isCellsdatafromFile) getCellsDataForCalculateFromFile();
        if (isCellsdatafromDB) getCellsDataForCalculateFromDB();
    }

    public void setLogInfo(String s) {
        if (ControllerCellsToKML.CellsorPDWindows != null)
            if (ControllerCellsToKML.CellsorPDWindows) {
                ControllerCellsToKML.logInfo.setLogData(s);
             //   ControllerCellsToKML.CellsorPDWindows = null;
            }
        if (ControllerPDCellsToKML.CellsorPDWindows != null)
            if (!ControllerPDCellsToKML.CellsorPDWindows) {
                ControllerPDCellsToKML.logInfo.setLogData(s);
           //     ControllerPDCellsToKML.CellsorPDWindows = null;
            }
    }

    public void getCellsDataForCalculateFromDB() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        try {
            cellsList = dbHandler.t_net_sectorSQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (cellsList == null) setLogInfo("CellsList is null" + '\n');
    }

    private void getCellsDataForCalculateFromFile() {
        if (isgetCellsDataFromCSV) getCellsDataForCalculateFromCSV();
    }

    private void getCellsDataForCalculateFromCSV() {
        GetDataFromCSV getDataFromCSV = new GetDataFromCSV();
        cellsList = getDataFromCSV.getDatafromCSVfile(separator);
        if (cellsList == null) setLogInfo("CellsList is null" + '\n');

    }

    public void calculate(List<Cell> cellsList) {
    }

}
