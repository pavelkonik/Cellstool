package model.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import main.Cell;
import model.CellsToKML;
import model.Model;

import java.util.List;

public class JSONCreate {
    //  private Gson gson = new Gson();

    private Model cellsToKml = new CellsToKML();

    public void create(List<Cell> cellsList){

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
            jsonArrayCoordinatesreal.add(123);
            jsonArrayCoordinatesreal.add(124);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesreal);
            JsonArray jsonArrayCoordinatesrea2 = new JsonArray();
            jsonArrayCoordinatesrea2.add(123);
            jsonArrayCoordinatesrea2.add(124);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesrea2);
            JsonArray jsonArrayCoordinatesrea3 = new JsonArray();
            jsonArrayCoordinatesrea3.add(123);
            jsonArrayCoordinatesrea3.add(124);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesrea3);
            JsonArray jsonArrayCoordinatesrea4 = new JsonArray();
            jsonArrayCoordinatesrea4.add(123);
            jsonArrayCoordinatesrea4.add(124);
            jsonArrayAllCoordinates.add(jsonArrayCoordinatesrea4);

            JsonArray jsonArrayAllCoordinatesEmpty = new JsonArray();
            jsonArrayAllCoordinatesEmpty.add(jsonArrayAllCoordinates);
            jsonElementGeometry.add("coordinates", jsonArrayAllCoordinatesEmpty);
            jsonElementType.add("geometry", jsonElementGeometry);
            jsonArrayFeatures.add(jsonElementType);


            JsonObject jsonElementBalloonContent = new JsonObject();
            jsonElementType.add("properties", jsonElementBalloonContent);
           // jsonElementBalloonContent.addProperty("balloonContent",cellsList.get(0).getCellName());
            jsonElementBalloonContent.addProperty("balloonContent",12313);
        }

        rootObject.add("features", jsonArrayFeatures);

        Gson gson = new Gson();
        String json = gson.toJson(rootObject); // генерация json строки
        System.out.println(json);

    }


}
