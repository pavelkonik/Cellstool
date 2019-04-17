package com.pavelk.model;


import com.pavelk.main.Cell;
import com.pavelk.model.db.DatabaseHandler;
import com.pavelk.uicontrollers.ControllerCellsToKML;
import com.pavelk.uicontrollers.ControllerPDCellsToKML;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Model implements ToKML{
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

    public void getCellsDataForCalculateFromFile() {
        if (isgetCellsDataFromCSV) getCellsDataForCalculateFromCSV();
    }

    public void getCellsDataForCalculateFromCSV() {
        GetDataFromCSV getDataFromCSV = new GetDataFromCSV();
        cellsList = getDataFromCSV.getDatafromCSVfile(separator);
        if (cellsList == null) setLogInfo("CellsList is null" + '\n');

    }

    public void calculate(List<Cell> cellsList) {
    }

    public String createJson(List<Cell> cellsList){

        return null;}

    private String htmlString;

    public void setHtmlString(String htmlString) {
        this.htmlString = htmlString;
    }

    public String getHtmlString() {
        return htmlString;
    }

    public String createHtml(List<Cell> cellsList) {
        String jsonstring = createJson(cellsList);

        htmlString = "";
        String coordorder = "&coordorder=longlat";
        // coordorder = "";
        htmlString = "<!DOCTYPE createHtml>\n" +
                "<createHtml xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <title>Карта</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/createHtml; charset=utf-8\" />\n" +
                "    <script src=\"https://api-maps.yandex.ru/2.1/?apikey=a388bd91-d955-4c69-a88d-53443e31412c&lang&lang=ru_RU" + coordorder + "\" type=\"text/javascript\">\n" +
                "    </script>\n" +
                "    <script type=\"text/javascript\">\n" +
                initmap() +
                getPolygons(jsonstring) +
                "    </script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id=\"map\" style=\"width: 600px; height: 400px\"></div>\n" +
                "</body>\n" +
                "</createHtml>";
        return htmlString;
    }

    public String getPolygons(String jsonstring) {
        return "    var objects = ymaps.geoQuery(" + jsonstring + ");\n" +
                " myMap.geoObjects.add(objects);" +
                "}";
    }

    public String initmap() {
        return "ymaps.ready(init);    \n" +
                "        function init(){ \n" +
                "            var myMap = new ymaps.Map(\"map\", {\n" +
                "                center: [27.8548,52.17456],\n" +
                "                zoom: 10\n" +
                "            }); \n";
    }


}
