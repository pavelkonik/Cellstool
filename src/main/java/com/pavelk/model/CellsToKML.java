package com.pavelk.model;


import com.google.gson.*;
import com.pavelk.main.Cell;
import com.pavelk.main.Environment;
import com.pavelk.model.kml.kmlForCell;

import com.pavelk.uicontrollers.ControllerCellsToKML;
import com.pavelk.uicontrollers.ControllerPDCellsToKML;
import javafx.scene.paint.Color;

import java.util.List;

public class CellsToKML extends Model {

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;

    @Override
    public void calculate(List<Cell> cellsList) {
        if (cellsList == null) {
            ControllerPDCellsToKML.logInfo.setLogData("Can't get Pd data" + '\n');
            return;
        }
        kmlForCell kmlforcell = new kmlForCell();
        kmlforcell.kmlCreateFile();
        kmlforcell.styleForKmlFile();
        kmlforcell.kmlCreatePlacemark(cellsList);
    }
    @Override
    public String createJson(List<Cell> cellsList){

        JsonObject rootObject = new JsonObject(); // создаем главный объект
        rootObject.addProperty("type", "FeatureCollection"); // записываем текст в поле "message"

        JsonArray jsonArrayFeatures = new JsonArray();
        JsonObject jsonElementType = null;
        for (int i = 0; i < cellsList.size(); i++) {
            jsonElementType = new JsonObject();
            jsonElementType.addProperty("type", "Feature");
            jsonElementType.addProperty("id", i);
            JsonObject jsonElementGeometry = new JsonObject();
            jsonElementGeometry.addProperty("type", "Polygon");
            JsonArray jsonArrayAllCoordinates =  new JsonArray();
            Environment environment = new ControllerCellsToKML().environment;

            JsonArray jsonArrayCoordinatesreal = new JsonArray();
            jsonArrayCoordinatesreal.add(cellsList.get(i).getLongitute());
            jsonArrayCoordinatesreal.add(cellsList.get(i).getLattitute());
//            jsonArrayCoordinatesreal.add(27.8548);
//            jsonArrayCoordinatesreal.add(52.17456);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesreal);

            JsonArray jsonArrayCoordinatesrea2 = new JsonArray();
            jsonArrayCoordinatesrea2.add(cellsList.get(i).newLongitute(cellsList.get(i).getAzimuth() + environment.getBeam()/2,
                    environment.getDistance()));
            jsonArrayCoordinatesrea2.add(cellsList.get(i).newLattitute(cellsList.get(i).getAzimuth() + environment.getBeam()/2,
                    environment.getDistance()));
            // jsonArrayCoordinatesrea2.add(27.857046755878457);
//            jsonArrayCoordinatesrea2.add(52.17340385442918);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesrea2);

            JsonArray jsonArrayCoordinatesrea3 = new JsonArray();
            jsonArrayCoordinatesrea3.add(cellsList.get(i).newLongitute(cellsList.get(i).getAzimuth(), environment.getDistance()));
            jsonArrayCoordinatesrea3.add(cellsList.get(i).newLattitute(cellsList.get(i).getAzimuth(), environment.getDistance()));
//            jsonArrayCoordinatesrea3.add(27.857688373681324);
//            jsonArrayCoordinatesrea3.add(52.17424766888399);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesrea3);

            JsonArray jsonArrayCoordinatesrea4 = new JsonArray();
            jsonArrayCoordinatesrea4.add(cellsList.get(i).newLongitute(cellsList.get(i).getAzimuth() - environment.getBeam()/2,
                    environment.getDistance()));
            jsonArrayCoordinatesrea4.add(cellsList.get(i).newLattitute(cellsList.get(i).getAzimuth() - environment.getBeam()/2,
                    environment.getDistance()));
//            jsonArrayCoordinatesrea4.add(27.857556054088843);
//            jsonArrayCoordinatesrea4.add(52.1751751722091);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesrea4);

            JsonArray jsonArrayAllCoordinatesEmpty = new JsonArray();
            jsonArrayAllCoordinatesEmpty.add(jsonArrayAllCoordinates);
            jsonElementGeometry.add("coordinates", jsonArrayAllCoordinatesEmpty);
            jsonElementType.add("geometry", jsonElementGeometry);
            jsonArrayFeatures.add(jsonElementType);

            JsonObject jsonElementBalloonContent = new JsonObject();
            jsonElementType.add("properties", jsonElementBalloonContent);
            // jsonElementBalloonContent.addProperty("balloonContent",cellsList.get(0).getCellName());
            jsonElementBalloonContent.addProperty("balloonContent",cellsList.get(i).getCellName());
        }

        rootObject.add("features", jsonArrayFeatures);

        Gson gson = new Gson();
        String json = gson.toJson(rootObject);
        json = json.replaceAll("\\{\"","{").
                replaceAll("\":",":").
                replace(",\"",",").
                replaceAll("\"","'");
        return json;
    }

}
