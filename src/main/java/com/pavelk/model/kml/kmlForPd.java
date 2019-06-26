package com.pavelk.model.kml;

import com.pavelk.main.Const;
import com.pavelk.main.Environment;
import com.pavelk.main.Pd;
import com.pavelk.uicontrollers.ControllerPDCellsToKML;
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

public class kmlForPd implements KML {
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

    public kmlForPd() {
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
    public void kmlCreateFile() {
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

            Element document = doc.createElement("Document");
            kml.appendChild(document);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(selectedFile.getName()));
            document.appendChild(name);

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
    public void styleForKmlFile() {
        if (filename.equals("")) return;
        try {

            doc = docBuilder.parse(new File(filename));
            NodeList documentElement = doc.getElementsByTagName("Document");

            for (int i = 0; i < 20; i++) {
                Element style1 = doc.createElement("Style");
                style1.setAttribute("id", "color" + i);
                documentElement.item(0).appendChild(style1);
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
                style1.appendChild(polyStyle);
                style1.appendChild(lineStyle);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);

        } catch (IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void kmlCreatePlacemark(List<T> list) {
        List<Pd> listcell = (List<Pd>) list;

        if (list.size() == 0) return;
        try {
            doc = docBuilder.parse(new File(filename));
            NodeList documentElement = doc.getElementsByTagName("Document");
            Element folder_ci = doc.createElement("Folder");
            documentElement.item(0).appendChild(folder_ci);

            Element folderCiNext = null;
            for (int i = 0; i < listcell.size(); i++) {
                if (i == 0) {
                    Element nameInCi = doc.createElement("name");
                    nameInCi.appendChild(doc.createTextNode(listcell.get(i).getCell().getCellName()));
                    folder_ci.appendChild(nameInCi);
                    Element folderDate = doc.createElement("Folder");
                    folder_ci.appendChild(folderDate);
                    Element nameInDate = doc.createElement("name");
                    nameInDate.appendChild(doc.createTextNode("date: " + listcell.get(i).getEndTime()));
                    folderDate.appendChild(nameInDate);
                    pdPlacemark(listcell.get(i), folderDate);
                } else {
                    if (listcell.get(i).getCell().getCellId() != listcell.get(i - 1).getCell().getCellId()) {   //CI<>CI
                        folderCiNext = doc.createElement("Folder");
                        documentElement.item(0).appendChild(folderCiNext);
                        Element nameInNext = doc.createElement("name");
                        nameInNext.appendChild(doc.createTextNode(listcell.get(i).getCell().getCellName()));
                        folderCiNext.appendChild(nameInNext);
                        Element folderDateNext = doc.createElement("Folder");
                        folderCiNext.appendChild(folderDateNext);
                        Element nameInDate = doc.createElement("name");
                        nameInDate.appendChild(doc.createTextNode("date: " + listcell.get(i).getEndTime()));
                        folderDateNext.appendChild(nameInDate);
                        pdPlacemark(listcell.get(i), folderDateNext);
                    } else {   //CI=CI
                        Element folderDateNext = doc.createElement("Folder");
                        if (folderCiNext != null) folderCiNext.appendChild(folderDateNext);
                        else folder_ci.appendChild(folderDateNext);
                        Element nameInDate = doc.createElement("name");
                        nameInDate.appendChild(doc.createTextNode("date: " + listcell.get(i).getEndTime()));
                        folderDateNext.appendChild(nameInDate);
                        pdPlacemark(listcell.get(i), folderDateNext);
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

    void pdPlacemark(Pd pd, Element folder_date) {

        String placemarkcoord = "";

        for (int j = 0; j < 42; j++) {
            if (pd.getPdArray()[j] > 0) {
                if (j == 0)
                    placemarkcoord = pd.getCell().polygonCoordinates(Const.PD_length[j], 0, 60);
                else
                    placemarkcoord = pd.getCell().polygonCoordinates(Const.PD_length[j], Const.PD_length[j - 1], 60);

                Element placemark = doc.createElement("Placemark");
                folder_date.appendChild(placemark);

                Element name1 = doc.createElement("name");
                name1.appendChild(doc.createTextNode(pd.getCell().getCellName()));
                placemark.appendChild(name1);
                Element description = doc.createElement("description");
                String desc = "date = " + pd.getEndTime() + "; com.pavelk.main.Pd = " + Math.round(100 * (pd.getPdArray()[j]) / pd.getTotalCount()) + "%";
                description.appendChild(doc.createTextNode(desc));
                placemark.appendChild(description);
                Element styleUrl = doc.createElement("styleUrl");
                String style_color = "";
                for (int k = 0; k < 20; k++) {
                    if ((100.0 * pd.getPdArray()[j] / pd.getTotalCount()) > k * 5.0 && (100.0 * pd.getPdArray()[j] / pd.getTotalCount()) <= (k + 1) * 5.0)
                        style_color = "#color" + k;
                }
                styleUrl.appendChild(doc.createTextNode(style_color));
                placemark.appendChild(styleUrl);
                Element polygon = doc.createElement("Polygon");
                Element extrude = doc.createElement("extrude");
                extrude.appendChild(doc.createTextNode("0"));
                polygon.appendChild(extrude);
                Element tessellate = doc.createElement("tessellate");
                tessellate.appendChild(doc.createTextNode("0"));
                polygon.appendChild(tessellate);
                Element outerBoundaryIs = doc.createElement("outerBoundaryIs");
                Element linearRing = doc.createElement("LinearRing");
                Element coordinates = doc.createElement("coordinates");
                coordinates.appendChild(doc.createTextNode(placemarkcoord));
                linearRing.appendChild(coordinates);
                outerBoundaryIs.appendChild(linearRing);
                polygon.appendChild(outerBoundaryIs);
                placemark.appendChild(polygon);
            }
        }
    }
}
