package com.pavelk.uicontrollers;

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
    public static String html = "12312";

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @FXML
   private WebView webView;



    private WebEngine webEngine;
 //   private CellsToKML cellsToKML = new CellsToKML();

    public void loadYandexMap(String htmlstring) {
        html = htmlstring;
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



    @FXML
    void initialize() {

        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.loadContent(html);

    }

    public String initmap() {
        return "ymaps.ready(init);    \n" +
                "        function init(){ \n" +
                "            var myMap = new ymaps.ControllerMap(\"map\", {\n" +
                "                center: [27.8548,52.17456],\n" +
                "                zoom: 10\n" +
                "            }); \n";
    }

    public String getPolygons() {
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
                "    myMap.geoObjects.add(myPolygon);" +
                "}";
    }

    public String getScript2() {
        return
                "ymaps.ready(init);\n" +
                        "\n" +
                        "function init() {\n" +
                        "    var myMap = new ymaps.Map(\"map\", {\n" +
                        "            center: [55.73, 37.75],\n" +
                        "            zoom: 10\n" +
                        "        }, {\n" +
                        "            searchControlProvider: 'yandex#search'\n" +
                        "        });\n" +
                        "\n" +
                        "    // Создаем многоугольник, используя класс GeoObject.\n" +
                        "    var myGeoObject = new ymaps.GeoObject({\n" +
                        "        // Описываем геометрию геообъекта.\n" +
                        "        geometry: {\n" +
                        "            // Тип геометрии - \"Многоугольник\".\n" +
                        "            type: \"Polygon\",\n" +
                        "            // Указываем координаты вершин многоугольника.\n" +
                        "            coordinates: [\n" +
                        "                // Координаты вершин внешнего контура.\n" +
                        "                [\n" +
                        "                    [55.75, 37.80],\n" +
                        "                    [55.80, 37.90],\n" +
                        "                    [55.75, 38.00],\n" +
                        "                    [55.70, 38.00],\n" +
                        "                    [55.70, 37.80]\n" +
                        "                ],\n" +
                        "                // Координаты вершин внутреннего контура.\n" +
                        "                [\n" +
                        "                    [55.75, 37.82],\n" +
                        "                    [55.75, 37.98],\n" +
                        "                    [55.65, 37.90]\n" +
                        "                ]\n" +
                        "            ],\n" +
                        "            // Задаем правило заливки внутренних контуров по алгоритму \"nonZero\".\n" +
                        "            fillRule: \"nonZero\"\n" +
                        "        },\n" +
                        "        // Описываем свойства геообъекта.\n" +
                        "        properties:{\n" +
                        "            // Содержимое балуна.\n" +
                        "            balloonContent: \"Многоугольник\"\n" +
                        "        }\n" +
                        "    }, {\n" +
                        "        // Описываем опции геообъекта.\n" +
                        "        // Цвет заливки.\n" +
                        "        fillColor: '#00FF00',\n" +
                        "        // Цвет обводки.\n" +
                        "        strokeColor: '#0000FF',\n" +
                        "        // Общая прозрачность (как для заливки, так и для обводки).\n" +
                        "        opacity: 0.5,\n" +
                        "        // Ширина обводки.\n" +
                        "        strokeWidth: 5,\n" +
                        "        // Стиль обводки.\n" +
                        "        strokeStyle: 'shortdash'\n" +
                        "    });\n" +
                        "\n" +
                        "    // Добавляем многоугольник на карту.\n" +
                        "    myMap.geoObjects.add(myGeoObject);\n" +
                        "\n" +
                        "    // Создаем многоугольник, используя вспомогательный класс Polygon.\n" +
                        "    var myPolygon = new ymaps.Polygon([\n" +
                        "        // Указываем координаты вершин многоугольника.\n" +
                        "        // Координаты вершин внешнего контура.\n" +
                        "        [\n" +
                        "            [55.75, 37.50],\n" +
                        "            [55.80, 37.60],\n" +
                        "            [55.75, 37.70],\n" +
                        "            [55.70, 37.70],\n" +
                        "            [55.70, 37.50]\n" +
                        "        ],\n" +
                        "        // Координаты вершин внутреннего контура.\n" +
                        "        [\n" +
                        "            [55.75, 37.52],\n" +
                        "            [55.75, 37.68],\n" +
                        "            [55.65, 37.60]\n" +
                        "        ]\n" +
                        "    ], {\n" +
                        "        // Описываем свойства геообъекта.\n" +
                        "        // Содержимое балуна.\n" +
                        "        hintContent: \"Многоугольник\"\n" +
                        "    }, {\n" +
                        "        // Задаем опции геообъекта.\n" +
                        "        // Цвет заливки.\n" +
                        "        fillColor: '#00FF0088',\n" +
                        "        // Ширина обводки.\n" +
                        "        strokeWidth: 5\n" +
                        "    });\n" +
                        "\n" +
                        "    // Добавляем многоугольник на карту.\n" +
                        "    myMap.geoObjects.add(myPolygon);\n" +
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
//                "    // Создадим объекты на основе JSON-описания геометрий.\n" +
                "    var objects = ymaps.geoQuery(" + getjSONtext() + ");\n" +
                " objects.addToMap(myMap);" +
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



}