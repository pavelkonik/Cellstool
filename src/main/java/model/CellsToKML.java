package model;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.Cell;
import model.kml.KMLforcell;

import uicontrollers.ControllerPDCellsToKML;
import javafx.scene.paint.Color;

import java.util.List;

public class CellsToKML extends Model {
    public double getBeam() {
        return beam;
    }

    public void setBeam(double beam) {
        this.beam = beam;
    }

    public double getCellsize() {
        return cellsize;
    }

    public void setCellsize(double cellsize) {
        this.cellsize = cellsize;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private double beam;
    private double cellsize;
    private Color color;

    @Override
    public void calculate(List<Cell> cellsList) {
        if (cellsList == null) {
            ControllerPDCellsToKML.logInfo.setLogData("Can't get main.PD data" + '\n');
            return;
        }
        KMLforcell kmlforcell = new KMLforcell();
        kmlforcell.KMLcreatefile();
        kmlforcell.StyleforKMLfile();
        kmlforcell.KMLcreatePlacemark(cellsList);
    }
    @Override
    public JsonObject createJson(List<Cell> cellsList){

        JsonObject rootObject = new JsonObject(); // создаем главный объект
        rootObject.addProperty("type", "FeatureCollection"); // записываем текст в поле "message"

        JsonArray jsonArrayFeatures = new JsonArray();
        JsonObject jsonElementType = null;
        for (int i = 0; i < 2; i++) {
            jsonElementType = new JsonObject();
            jsonElementType.addProperty("type", "Feature");
            jsonElementType.addProperty("id", i);
            JsonObject jsonElementGeometry = new JsonObject();
            jsonElementGeometry.addProperty("type", "Polygon");
            JsonArray jsonArrayAllCoordinates =  new JsonArray();

            JsonArray jsonArrayCoordinatesreal = new JsonArray();
            jsonArrayCoordinatesreal.add(27.8548);
            jsonArrayCoordinatesreal.add(52.17456);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesreal);
            JsonArray jsonArrayCoordinatesrea2 = new JsonArray();
            jsonArrayCoordinatesrea2.add(27.857046755878457);
            jsonArrayCoordinatesrea2.add(52.17340385442918);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesrea2);
            JsonArray jsonArrayCoordinatesrea3 = new JsonArray();
            jsonArrayCoordinatesrea3.add(27.857688373681324);
            jsonArrayCoordinatesrea3.add(52.17424766888399);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesrea3);
            JsonArray jsonArrayCoordinatesrea4 = new JsonArray();
            jsonArrayCoordinatesrea4.add(27.857556054088843);
            jsonArrayCoordinatesrea4.add(52.1751751722091);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesrea4);

            JsonArray jsonArrayAllCoordinatesEmpty = new JsonArray();
            jsonArrayAllCoordinatesEmpty.add(jsonArrayAllCoordinates);
            jsonElementGeometry.add("coordinates", jsonArrayAllCoordinatesEmpty);
            jsonElementType.add("geometry", jsonElementGeometry);
            jsonArrayFeatures.add(jsonElementType);


            JsonObject jsonElementBalloonContent = new JsonObject();
            jsonElementType.add("properties", jsonElementBalloonContent);
            // jsonElementBalloonContent.addProperty("balloonContent",cellsList.get(0).getCellName());
            jsonElementBalloonContent.addProperty("balloonContent",10721);
        }

        rootObject.add("features", jsonArrayFeatures);

        Gson gson = new Gson();
        String json = gson.toJson(rootObject); // генерация json строки
        System.out.println(json);
        //return rootObject;
        return rootObject;
    }

}
