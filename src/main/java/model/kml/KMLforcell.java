package model.kml;

import javafx.stage.FileChooser;
import main.Cell;
import uicontrollers.ControllerCellsToKML;
import main.Environment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class KMLforcell /*extends KMLClass*/ implements KML {

    private Environment environment = null;
    private String filename = "";
    private Document doc = null;
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;

    public DocumentBuilderFactory getDocFactory() {
        return docFactory;
    }

    public void setDocFactory(DocumentBuilderFactory docFactory) {
        this.docFactory = docFactory;
    }

    public DocumentBuilder getDocBuilder() {
        return docBuilder;
    }

    public void setDocBuilder(DocumentBuilder docBuilder) {
        this.docBuilder = docBuilder;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public KMLforcell() {
        docFactory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        docFactory.setNamespaceAware(true);
        doc = docBuilder.newDocument();
    }

    @Override
    public void KMLcreatefile() {
        try {
            //save file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(//
                    new FileChooser.ExtensionFilter("kml", "*.kml"));
            File selectedFile = fileChooser.showSaveDialog(null);
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("kml", ".kml"));

            if (selectedFile == null) {
                ControllerCellsToKML.logInfo.setLogData("File for saving wasn't select");
                filename = "";
                return;
            }
            // String filename = selectedFile.getAbsolutePath();
            filename = selectedFile.getAbsolutePath();
            //save file



            Element kml = doc.createElement("kml");
            kml.setAttribute("xmlns", "http://www.opengis.net/kml/2.2");
            doc.appendChild(kml);

            Element Document = doc.createElement("Document");
            kml.appendChild(Document);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(selectedFile.getName()));
            Document.appendChild(name);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);

            //  System.out.println("Done");
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    @Override
    public void StyleforKMLfile() {
        if (filename == "") return;
        try {

            doc = docBuilder.parse(new File(filename));
            NodeList documentElement = doc.getElementsByTagName("Document");

            Element Style1 = doc.createElement("Style");
            Style1.setAttribute("id", "choose_color2");
            documentElement.item(0).appendChild(Style1);

            Element PolyStyle1 = doc.createElement("PolyStyle");
            Style1.appendChild(PolyStyle1);

            Element color1 = doc.createElement("color");
            color1.appendChild(doc.createTextNode("7d0000ff"));
            PolyStyle1.appendChild(color1);

            Element LineStyle1 = doc.createElement("LineStyle");
            Style1.appendChild(LineStyle1);
            Element width1 = doc.createElement("width");
            width1.appendChild(doc.createTextNode("1.5"));
            LineStyle1.appendChild(width1);

            Element Style2 = doc.createElement("Style");
            Style2.setAttribute("id", "choose_color1");
            documentElement.item(0).appendChild(Style2);

            Element PolyStyle2 = doc.createElement("PolyStyle");
            Style2.appendChild(PolyStyle2);

            Element color2 = doc.createElement("color");
            //   ControllerCellsToKML controllerCelltokml = new ControllerCellsToKML();
            environment = new ControllerCellsToKML().environment;
            String red = environment.getColor().toString().substring(2, 4);
            String green = environment.getColor().toString().substring(4, 6);
            String blue = environment.getColor().toString().substring(6, 8);
            String opacity = environment.getColor().toString().substring(8, 10);
            String colorstring = String.format("%s%s%s%s", opacity, blue, green, red);
            color2.appendChild(doc.createTextNode(colorstring));
            PolyStyle2.appendChild(color2);

            Element LineStyle2 = doc.createElement("LineStyle");
            Style2.appendChild(LineStyle2);
            Element width2 = doc.createElement("width");
            width2.appendChild(doc.createTextNode("1.5"));
            LineStyle2.appendChild(width2);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void KMLcreatePlacemark(List<T> list) {
        List<Cell> listcell;
        listcell = (List<Cell>) list;

        if (listcell.size() == 0) return;
        environment = new ControllerCellsToKML().environment;

        try {

            doc = docBuilder.parse(new File(filename));
            NodeList documentElement = doc.getElementsByTagName("Document");
            for (int i = 0; i < listcell.size(); i++) {
                Element Placemark = doc.createElement("Placemark");
                documentElement.item(0).appendChild(Placemark);

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(listcell.get(i).getCellID() + ", " + listcell.get(i).getCellName()));
                Placemark.appendChild(name);

                Element description = doc.createElement("description");
                description.appendChild(doc.createTextNode(listcell.get(i).getCellID() + ", " + listcell.get(i).getCellName()));
                Placemark.appendChild(description);

                Element styleUrl = doc.createElement("styleUrl");
                styleUrl.appendChild(doc.createTextNode("#choose_color1"));
                Placemark.appendChild(styleUrl);

                Element Polygon = doc.createElement("Polygon");
                Placemark.appendChild(Polygon);

                Element extrude = doc.createElement("extrude");
                Polygon.appendChild(extrude);
                extrude.appendChild(doc.createTextNode("0"));

                Element tessellate = doc.createElement("tessellate");
                Polygon.appendChild(tessellate);
                tessellate.appendChild(doc.createTextNode("0"));

                Element outerBoundaryIs = doc.createElement("outerBoundaryIs");
                Polygon.appendChild(outerBoundaryIs);

                Element LinearRing = doc.createElement("LinearRing");
                outerBoundaryIs.appendChild(LinearRing);

                Element coordinates = doc.createElement("coordinates");
                LinearRing.appendChild(coordinates);

                coordinates.appendChild(doc.createTextNode(listcell.get(i).polygonCoordinates(environment.getDistance(), environment.getBeam())));
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
            ControllerCellsToKML.logInfo.setLogData("kml createJson" + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
}
