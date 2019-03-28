package main;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private String CellName;
    private int CellID;
    private int PSC;
    private double azimuth;
    private double lattitute;
    private double longitute;
    private double CPICH;
    private double height;

    public Cell(String cellName, int cellID, int PSC, double azimuth, double lattitute, double longitute, double CPICH, double height) {
        this.CellName = cellName;
        this.CellID = cellID;
        this.PSC = PSC;
        this.azimuth = azimuth;
        this.lattitute = lattitute;
        this.longitute = longitute;
        this.CPICH = CPICH;
        this.height = height;
    }

    public Cell(String cellName, String cellID, String PSC, String azimuth, String lattitute, String longitute, String CPICH, String height) {

        if (cellID == null) this.CellID = 0;
        else if (!cellID.matches("\\d+")) this.CellID = 0;
        else this.CellID = Integer.parseInt(cellID);

        if (cellName == null) CellName = "";
        else this.CellName = cellName;

        if (lattitute == null) this.lattitute = 0;
        else if (!lattitute.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$") || lattitute.equals("")) this.lattitute = 0;
        else this.lattitute = Double.parseDouble(lattitute);

        if (longitute == null) this.longitute = 0;
        else if (!longitute.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$") || longitute.equals("")) this.longitute = 0;
        else this.longitute = Double.parseDouble(longitute);

        if (azimuth == null) this.azimuth = 0;
        else if (!azimuth.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$")) this.azimuth = 0;
        else this.azimuth = Double.parseDouble(azimuth);

        if (CPICH == null) this.CPICH = 0;
        else if (!CPICH.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$")) this.CPICH = 0;
        else this.CPICH = Double.parseDouble(CPICH);

        if (PSC == null) this.PSC = 0;
        else if (!PSC.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$")) this.PSC = 0;
        else this.PSC = Integer.parseInt(PSC);

        if (height == null) this.height = 0;
        else if (!height.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$")) this.height = 0;
        else this.height = Double.parseDouble(height);
    }

    public String getCellName() {
        return CellName;
    }

    public void setCellName(String cellName) {
        CellName = cellName;
    }

    public int getCellID() {
        return CellID;
    }

    public void setCellID(int cellID) {
        CellID = cellID;
    }

    public int getPSC() {
        return PSC;
    }

    public void setPSC(int PSC) {
        this.PSC = PSC;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(double azimuth) {
        this.azimuth = azimuth;
    }

    public double getLattitute() {
        return lattitute;
    }

    public void setLattitute(double lattitute) {
        this.lattitute = lattitute;
    }

    public double getLongitute() {
        return longitute;
    }

    public void setLongitute(double longitute) {
        this.longitute = longitute;
    }

    public double getCPICH() {
        return CPICH;
    }

    public void setCPICH(double CPICH) {
        this.CPICH = CPICH;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double newlattitute(double new_azimuth, double distance) {
        return lattitute + distance * Math.cos(new_azimuth * Math.PI / 180) / (6371000 * Math.PI / 180);
    }

    public double newlongitute(double new_azimuth, double distance) {
        return longitute + distance * Math.sin(new_azimuth * Math.PI / 180) / Math.cos(lattitute * Math.PI / 180) / (6371000 * Math.PI / 180);
    }

    public String polygonCoordinates(double distance, double beam) {
        StringBuilder polygon = new StringBuilder("");
        polygon = polygon.append(longitute).append(",").append(lattitute).append(",0 ");
        polygon = polygon.append(newlongitute((azimuth + beam / 2), distance)).append(",");
        polygon = polygon.append(newlattitute((azimuth + beam / 2), distance)).append(",0 ");
        polygon = polygon.append(newlongitute((azimuth), distance)).append(",");
        polygon = polygon.append(newlattitute((azimuth), distance)).append(",0 ");
        polygon = polygon.append(newlongitute((azimuth - beam / 2), distance)).append(",");
        polygon = polygon.append(newlattitute((azimuth - beam / 2), distance)).append(",0 ");
        polygon = polygon.append(longitute).append(",").append(lattitute).append(",0");
        return polygon.toString();
    }

    public String polygonCoordinatesForyMap(double distance, double beam) {
        StringBuilder polygon = new StringBuilder("");
        polygon = polygon.append("[[").append(longitute).append(",").append(lattitute).append("],");
        polygon = polygon.append("[").append(newlongitute((azimuth + beam / 2), distance)).append(",");
        polygon = polygon.append(newlattitute((azimuth + beam / 2), distance)).append("],");
        polygon = polygon.append("[").append(newlongitute((azimuth), distance)).append(",");
        polygon = polygon.append(newlattitute((azimuth), distance)).append("],");
        polygon = polygon.append("[").append(newlongitute((azimuth - beam / 2), distance)).append(",");
        polygon = polygon.append(newlattitute((azimuth - beam / 2), distance)).append("]]");
        return polygon.toString();
    }

    public String polygonCoordinates(double distance, double distance_before, double beam) {
        StringBuilder polygon = new StringBuilder("");
        polygon = polygon.append(newlongitute((azimuth + beam / 2), distance_before * 1000)).append(",");
        polygon = polygon.append(newlattitute((azimuth + beam / 2), distance_before * 1000)).append(",0 ");
        polygon = polygon.append(newlongitute((azimuth + beam / 2), distance * 1000)).append(",");
        polygon = polygon.append(newlattitute((azimuth + beam / 2), distance * 1000)).append(",0 ");
        polygon = polygon.append(newlongitute((azimuth), distance * 1000)).append(",");
        polygon = polygon.append(newlattitute((azimuth), distance * 1000)).append(",0 ");
        polygon = polygon.append(newlongitute((azimuth - beam / 2), distance * 1000)).append(",");
        polygon = polygon.append(newlattitute((azimuth - beam / 2), distance * 1000)).append(",0 ");
        polygon = polygon.append(newlongitute((azimuth - beam / 2), distance_before * 1000)).append(",");
        polygon = polygon.append(newlattitute((azimuth - beam / 2), distance_before * 1000)).append(",0 ");
        polygon = polygon.append(newlongitute((azimuth), distance_before * 1000)).append(",");
        polygon = polygon.append(newlattitute((azimuth), distance_before * 1000)).append(",0 ");
        polygon = polygon.append(newlongitute((azimuth + beam / 2), distance_before * 1000)).append(",");
        polygon = polygon.append(newlattitute((azimuth + beam / 2), distance_before * 1000)).append(",0 ");
        return polygon.toString();

    }
}
