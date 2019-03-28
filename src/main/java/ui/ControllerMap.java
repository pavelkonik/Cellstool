package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class ControllerMap {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private WebView webView;

    @FXML
    void initialize() {
        String html = "";
        String coordorder = "&coordorder=longlat";
       // coordorder = "";
        html = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <title>Карта</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <script src=\"https://api-maps.yandex.ru/2.1/?apikey=a388bd91-d955-4c69-a88d-53443e31412c&lang&lang=ru_RU" + coordorder + "\" type=\"text/javascript\">\n" +
                "    </script>\n" +
                "    <script type=\"text/javascript\">\n" +
//                initmap()+
//                getPolygons() +
                getScript1()+
                "    </script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id=\"map\" style=\"width: 600px; height: 400px\"></div>\n" +
                "</body>\n" +
                "</html>";

        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(html);
    }

    public String initmap() {
        return "ymaps.ready(init);    \n" +
                "        function init(){ \n" +
                "            var myMap = new ymaps.ControllerMap(\"map\", {\n" +
                "                center: [27.8548,52.17456],\n" +
                "                zoom: 10\n" +
                "            }); \n" ;
    }

    public String getPolygons(){
        return "var myPolygon = new ymaps.Polygon([\n" +
                "        [\n" +
                "            [27.8548,52.17456],\n" +
                "            [27.857046755878457,52.17340385442918],\n" +
                "            [27.857688373681324,52.17424766888399],\n" +
                "            [27.857556054088843,52.1751751722091]\n" +
                "        ],\n" +
                "    ], {\n" +
                "        hintContent: \"Сектор\"\n" +
                "    });\n" +
                "    myMap.geoObjects.add(myPolygon);}";
    }

    public String getScript2() {
        return
                "var objects = ymaps.geoQuery([" + getjSONtext() + "]);\n" +
                "    objects.searchInside(myMap).addToMap(myMap);" +
                "myMap.events.add('boundschange', function () {\n" +
                "        var visibleObjects = objects.searchInside(myMap).addToMap(myMap);\n" +
                "        objects.remove(visibleObjects).removeFromMap(myMap);\n" +
                "    });" +
                "\t\t\n" +
                "}";
    }

    public String getScript1() {
        return "ymaps.ready(init);\n" +
                "\n" +
                "function init() {\n" +
                "    var myMap = new ymaps.ControllerMap(\"map\", {\n" +
                "        center: [27.8548, 52.17456],\n" +
                "        zoom: 8\n" +
                "    }, {\n" +
                "        searchControlProvider: 'yandex#search'\n" +
                "    });\n" +
                "    \n" +
                "    // Создадим объекты на основе JSON-описания геометрий.\n" +
                "    var objects = ymaps.geoQuery(" +getjSONtext()+").addToMap(myMap);\n" +
//                "    \n" +
//                "        // Найдем объекты, попадающие в видимую область карты.\n" +
//                "    objects.searchInside(myMap)\n" +
//                "        // И затем добавим найденные объекты на карту.\n" +
//                "        .addToMap(myMap);\n" +
//                "    \n" +
//                "    myMap.events.add('boundschange', function () {\n" +
//                "        // После каждого сдвига карты будем смотреть, какие объекты попадают в видимую область.\n" +
//                "        var visibleObjects = objects.searchInside(myMap).addToMap(myMap);\n" +
//                "        // Оставшиеся объекты будем удалять с карты.\n" +
//                "        objects.remove(visibleObjects).removeFromMap(myMap);\n" +
//                "    });\n" +
                "}";
    }

    public String getjSONtext() {
        return "{\n" +
                "  \"type\": \"FeatureCollection\",\n" +
                "  \"features\": [\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"id\": 0,\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Polygon\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            [\n" +
                "              27.8548,\n" +
                "              52.17456\n" +
                "            ],\n" +
                "            [\n" +
                "              27.857046755878457,\n" +
                "              52.17340385442918\n" +
                "            ],\n" +
                "            [\n" +
                "              27.857688373681324,\n" +
                "              52.17424766888399\n" +
                "            ],\n" +
                "            [\n" +
                "              27.857556054088843,\n" +
                "              52.1751751722091\n" +
                "            ]\n" +
                "          ]\n" +
                "        ]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"balloonContent\": \"10721\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public void loadYandexMap() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MapForm.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


}