package model.json;

import model.Model;

public class HtmlCreate extends Model {

    private String html;
    public String getHtml() {
        return html;
    }

    public String createHtml() {
        html = "";
        String coordorder = "&coordorder=longlat";
        // coordorder = "";
        html = "<!DOCTYPE createHtml>\n" +
                "<createHtml xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <title>Карта</title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/createHtml; charset=utf-8\" />\n" +
                "    <script src=\"https://api-maps.yandex.ru/2.1/?apikey=a388bd91-d955-4c69-a88d-53443e31412c&lang&lang=ru_RU" + coordorder + "\" type=\"text/javascript\">\n" +
                "    </script>\n" +
                "    <script type=\"text/javascript\">\n" +
                initmap() +
                getPolygons() +
                //  getScript2()+
                "    </script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id=\"map\" style=\"width: 600px; height: 400px\"></div>\n" +
                "</body>\n" +
                "</createHtml>";
        return html;
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

    public String initmap() {
        return "ymaps.ready(init);    \n" +
                "        function init(){ \n" +
                "            var myMap = new ymaps.ControllerMap(\"map\", {\n" +
                "                center: [27.8548,52.17456],\n" +
                "                zoom: 10\n" +
                "            }); \n";
    }

}
