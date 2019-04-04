package model.kml;

import main.Const;
import main.Environment;
import main.PD;
import uicontrollers.ControllerPDCellsToKML;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
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

public class KMLforPD implements KML {
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

    public KMLforPD() {
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
                ControllerPDCellsToKML.logInfo.setLogData("Save file wasn't select");
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

            for (int i = 0; i < 20; i++) {
                Element Style1 = doc.createElement("Style");
                Style1.setAttribute("id", "color" + i);
                documentElement.item(0).appendChild(Style1);
                Element lineStyle = doc.createElement("lineStyle");
                Element width = doc.createElement("width");
                width.appendChild(doc.createTextNode("1.5"));
                lineStyle.appendChild(width);
                Element polyStyle = doc.createElement("PolyStyle");
                Element color = doc.createElement("color");
                // String red = Color.color(255,255,255).toString();
                String red = Color.color(1f, (255 - i * 12) / 255f, (255 - i * 12) / 255f, (175) / 255f).toString().substring(2, 4);
                String green = Color.color(1f, (255 - i * 12) / 255f, (255 - i * 12) / 255f, (175) / 255f).toString().substring(4, 6);
                String blue = Color.color(1f, (255 - i * 12) / 255f, (255 - i * 12) / 255f, (175) / 255f).toString().substring(6, 8);
                String opacity = Color.color(1f, (255 - i * 12) / 255f, (255 - i * 12) / 255f, (175) / 255f).toString().substring(8, 10);
                String colorstring = String.format("%s%s%s%s", opacity, blue, green, red);
                color.appendChild(doc.createTextNode(colorstring));
                //  color.appendChild(doc.createTextNode('bf' + '{:02x}'.format((255 - i * 12)) + '{:02x}'.format((255 - i * 12)) + '{:02x}'.format((255))));
                polyStyle.appendChild(color);
                Style1.appendChild(polyStyle);
                Style1.appendChild(lineStyle);
            }

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
        List<PD> listcell = (List<PD>) list;

        if (list.size() == 0) return;
        try {
            doc = docBuilder.parse(new File(filename));
            NodeList documentElement = doc.getElementsByTagName("Document");
            Element folder_ci = doc.createElement("Folder");
            documentElement.item(0).appendChild(folder_ci);

            Element folder_ci_next = null;
            for (int i = 0; i < listcell.size(); i++) {
                if (i == 0) {
                    Element name_in_ci = doc.createElement("name");
                    name_in_ci.appendChild(doc.createTextNode(listcell.get(i).getCell().getCellName()));
                    folder_ci.appendChild(name_in_ci);
                    Element folder_date = doc.createElement("Folder");
                    folder_ci.appendChild(folder_date);
                    Element name_in_date = doc.createElement("name");
                    name_in_date.appendChild(doc.createTextNode("date: " + listcell.get(i).getEnd_time()));
                    folder_date.appendChild(name_in_date);
                    PDPlacemark(listcell.get(i), folder_date);
                } else {
                    if (listcell.get(i).getCell().getCellID() != listcell.get(i - 1).getCell().getCellID()) {   //CI<>CI
                        folder_ci_next = doc.createElement("Folder");
                        documentElement.item(0).appendChild(folder_ci_next);
                        Element name_in_next = doc.createElement("name");
                        name_in_next.appendChild(doc.createTextNode(listcell.get(i).getCell().getCellName()));
                        folder_ci_next.appendChild(name_in_next);
                        Element folder_date_next = doc.createElement("Folder");
                        folder_ci_next.appendChild(folder_date_next);
                        Element name_in_date = doc.createElement("name");
                        name_in_date.appendChild(doc.createTextNode("date: " + listcell.get(i).getEnd_time()));
                        folder_date_next.appendChild(name_in_date);
                        PDPlacemark(listcell.get(i), folder_date_next);
                    } else {   //CI=CI
                        Element folder_date_next = doc.createElement("Folder");
                        if (folder_ci_next != null) folder_ci_next.appendChild(folder_date_next);
                        else folder_ci.appendChild(folder_date_next);
                        Element name_in_date = doc.createElement("name");
                        name_in_date.appendChild(doc.createTextNode("date: " + listcell.get(i).getEnd_time()));
                        folder_date_next.appendChild(name_in_date);
                        PDPlacemark(listcell.get(i), folder_date_next);
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
            ControllerPDCellsToKML.logInfo.setLogData("kml createJson" + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    void PDPlacemark(PD pd, Element folder_date) {

        String placemarkcoord = "";

        for (int j = 0; j < 42; j++) {
            if (pd.getPdarray()[j] > 0) {
                if (j == 0)
                    placemarkcoord = pd.getCell().polygonCoordinates(Const.PD_length[j], 0, 60);
                else
                    placemarkcoord = pd.getCell().polygonCoordinates(Const.PD_length[j], Const.PD_length[j - 1], 60);

                Element Placemark = doc.createElement("Placemark");
                folder_date.appendChild(Placemark);

                Element name1 = doc.createElement("name");
                name1.appendChild(doc.createTextNode(pd.getCell().getCellName()));
                Placemark.appendChild(name1);
                Element description = doc.createElement("description");
                String desc = "date = " + pd.getEnd_time() + "; main.PD = " + Math.round(100 * (pd.getPdarray()[j]) / pd.getTotalCount()) + "%";
                description.appendChild(doc.createTextNode(desc));
                Placemark.appendChild(description);
                Element styleUrl = doc.createElement("styleUrl");
                String style_color = "";
                for (int k = 0; k < 20; k++) {
                    if ((100.0 * pd.getPdarray()[j] / pd.getTotalCount()) > k * 5.0 && (100.0 * pd.getPdarray()[j] / pd.getTotalCount()) <= (k + 1) * 5.0)
                        style_color = "#color" + k;
                }
                styleUrl.appendChild(doc.createTextNode(style_color));
                Placemark.appendChild(styleUrl);
                Element Polygon = doc.createElement("Polygon");
                Element extrude = doc.createElement("extrude");
                extrude.appendChild(doc.createTextNode("0"));
                Polygon.appendChild(extrude);
                Element tessellate = doc.createElement("tessellate");
                tessellate.appendChild(doc.createTextNode("0"));
                Polygon.appendChild(tessellate);
                Element outerBoundaryIs = doc.createElement("outerBoundaryIs");
                Element LinearRing = doc.createElement("LinearRing");
                Element coordinates = doc.createElement("coordinates");
                coordinates.appendChild(doc.createTextNode(placemarkcoord));
                LinearRing.appendChild(coordinates);
                outerBoundaryIs.appendChild(LinearRing);
                Polygon.appendChild(outerBoundaryIs);
                Placemark.appendChild(Polygon);
            }
        }
    }
}
