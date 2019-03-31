package model.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import main.Cell;

import java.util.List;

public class jSONCreate {
    //  private Gson gson = new Gson();

    public void create() {//(List<Cell> cellsList){

//        jsonObject.addProperty("type","FeatureCollection");
//        jsonObject.addProperty("features",);
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
            JsonArray jsonArrayCoordinatesempty = new JsonArray();

            JsonArray jsonArrayCoordinatesreal = new JsonArray();
            jsonArrayCoordinatesreal.add(123);
            jsonArrayCoordinatesreal.add(124);
            jsonArrayCoordinatesempty.add(jsonArrayCoordinatesreal);
            JsonArray jsonArrayCoordinatesrea2 = new JsonArray();
            jsonArrayCoordinatesrea2.add(123);
            jsonArrayCoordinatesrea2.add(124);
            jsonArrayCoordinatesempty.add(jsonArrayCoordinatesrea2);
            JsonArray jsonArrayCoordinatesrea3 = new JsonArray();
            jsonArrayCoordinatesrea3.add(123);
            jsonArrayCoordinatesrea3.add(124);
            jsonArrayCoordinatesempty.add(jsonArrayCoordinatesrea3);
            JsonArray jsonArrayCoordinatesrea4 = new JsonArray();
            jsonArrayCoordinatesrea4.add(123);
            jsonArrayCoordinatesrea4.add(124);
            jsonArrayCoordinatesempty.add(jsonArrayCoordinatesrea4);
          //  JsonArray jsonArrayCoordinatesnext = new JsonArray();
          //  jsonArrayCoordinatesnext.add(jsonArrayCoordinatesreal);
          //  jsonArrayCoordinatesempty.add(jsonArrayCoordinatesnext);


            jsonElementGeometry.add("coordinates", jsonArrayCoordinatesempty);
            jsonElementType.add("geometry", jsonElementGeometry);
            jsonArrayFeatures.add(jsonElementType);

        }


        rootObject.add("features", jsonArrayFeatures);


//
//        childObject.addProperty("name", "World!"); // записываем текст в поле "name" у объект Place
//        rootObject.add("place", childObject); // сохраняем дочерний объект в поле "place"

        Gson gson = new Gson();
        String json = gson.toJson(rootObject); // генерация json строки
        System.out.println(json);

    }


}
