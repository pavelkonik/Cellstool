package model.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import main.Cell;

import java.util.List;

public class jSONCreate {
  //  private Gson gson = new Gson();

    public void create (){//(List<Cell> cellsList){

//        jsonObject.addProperty("type","FeatureCollection");
//        jsonObject.addProperty("features",);
        JsonObject rootObject = new JsonObject(); // создаем главный объект
        rootObject.addProperty("type", "FeatureCollection"); // записываем текст в поле "message"

        JsonArray jsonArrayFeatures = new JsonArray();
        JsonObject jsonElementType = null;
        for (int i = 0; i <10 ; i++) {
            jsonElementType = new JsonObject();
            jsonElementType.addProperty("type", "Feature");
            jsonElementType.addProperty("id", i);
            jsonArrayFeatures.add(jsonElementType);
        }


        rootObject.add("features",jsonArrayFeatures);



//
//        childObject.addProperty("name", "World!"); // записываем текст в поле "name" у объект Place
//        rootObject.add("place", childObject); // сохраняем дочерний объект в поле "place"

        Gson gson = new Gson();
        String json = gson.toJson(rootObject); // генерация json строки
        System.out.println(json);

    }



}
