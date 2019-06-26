package com.pavelk.main;

public class Cell {

    private String cellName;
    private int cellId;
    private int psc;
    private double azimuth;
    private double lattitute;
    private double longitute;
    private double cpich;
    private double height;

    public Cell(String cellName, int cellId, int psc, double azimuth, double lattitute, double longitute, double cpich, double height) {
        this.cellName = cellName;
        this.cellId = cellId;
        this.psc = psc;
        this.azimuth = azimuth;
        this.lattitute = lattitute;
        this.longitute = longitute;
        this.cpich = cpich;
        this.height = height;
    }

    public Cell(String cellName, String cellId, String psc, String azimuth, String lattitute, String longitute, String cpich, String height) {

        if (cellId == null) this.cellId = 0;
        else if (!cellId.matches("\\d+")) this.cellId = 0;
        else this.cellId = Integer.parseInt(cellId);

        if (cellName == null) this.cellName = "";
        else this.cellName = cellName;

        if (lattitute == null) this.lattitute = 0;
        else if (!lattitute.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$") || lattitute.equals("")) this.lattitute = 0;
        else this.lattitute = Double.parseDouble(lattitute);

        if (longitute == null) this.longitute = 0;
        else if (!longitute.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$") || longitute.equals("")) this.longitute = 0;
        else this.longitute = Double.parseDouble(longitute);

        if (azimuth == null) this.azimuth = 0;
        else if (!azimuth.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$")) this.azimuth = 0;
        else this.azimuth = Double.parseDouble(azimuth);

        if (cpich == null) this.cpich = 0;
        else if (!cpich.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$")) this.cpich = 0;
        else this.cpich = Double.parseDouble(cpich);

        if (psc == null) this.psc = 0;
        else if (!psc.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$")) this.psc = 0;
        else this.psc = Integer.parseInt(psc);

        if (height == null) this.height = 0;
        else if (!height.matches("^((-|\\+)?[0-9]+(\\.[0-9]+)?)+$")) this.height = 0;
        else this.height = Double.parseDouble(height);
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public int getPsc() {
        return psc;
    }

    public void setPsc(int psc) {
        this.psc = psc;
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

    public double getCpich() {
        return cpich;
    }

    public void setCpich(double cpich) {
        this.cpich = cpich;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double newLattitute(double newAzimuth, double distance) {
        return lattitute + distance * Math.cos(newAzimuth * Math.PI / 180) / (6371000 * Math.PI / 180);
    }

    public double newLongitute(double new_azimuth, double distance) {
        return longitute + distance * Math.sin(new_azimuth * Math.PI / 180) / Math.cos(lattitute * Math.PI / 180) / (6371000 * Math.PI / 180);
    }

    public String polygonCoordinates(double distance, double beam) {
        StringBuilder polygon = new StringBuilder("");
        polygon = polygon.append(longitute).append(",").append(lattitute).append(",0 ");
        polygon = polygon.append(newLongitute((azimuth + beam / 2), distance)).append(",");
        polygon = polygon.append(newLattitute((azimuth + beam / 2), distance)).append(",0 ");
        polygon = polygon.append(newLongitute((azimuth), distance)).append(",");
        polygon = polygon.append(newLattitute((azimuth), distance)).append(",0 ");
        polygon = polygon.append(newLongitute((azimuth - beam / 2), distance)).append(",");
        polygon = polygon.append(newLattitute((azimuth - beam / 2), distance)).append(",0 ");
        polygon = polygon.append(longitute).append(",").append(lattitute).append(",0");
        return polygon.toString();
    }

    public String polygonCoordinatesForJson(double distance, double beam) {
        StringBuilder polygon = new StringBuilder("");
        polygon = polygon.append("[[[").append(longitute).append(",").append(lattitute).append("],");
        polygon = polygon.append("[").append(newLongitute((azimuth + beam / 2), distance)).append(",");
        polygon = polygon.append(newLattitute((azimuth + beam / 2), distance)).append("],");
        polygon = polygon.append("[").append(newLongitute((azimuth), distance)).append(",");
        polygon = polygon.append(newLattitute((azimuth), distance)).append("],");
        polygon = polygon.append("[").append(newLongitute((azimuth - beam / 2), distance)).append(",");
        polygon = polygon.append(newLattitute((azimuth - beam / 2), distance)).append("]]]");
        return polygon.toString();
    }

    public String polygonCoordinates(double distance, double distance_before, double beam) {
        StringBuilder polygon = new StringBuilder("");
        polygon = polygon.append(newLongitute((azimuth + beam / 2), distance_before * 1000)).append(",");
        polygon = polygon.append(newLattitute((azimuth + beam / 2), distance_before * 1000)).append(",0 ");
        polygon = polygon.append(newLongitute((azimuth + beam / 2), distance * 1000)).append(",");
        polygon = polygon.append(newLattitute((azimuth + beam / 2), distance * 1000)).append(",0 ");
        polygon = polygon.append(newLongitute((azimuth), distance * 1000)).append(",");
        polygon = polygon.append(newLattitute((azimuth), distance * 1000)).append(",0 ");
        polygon = polygon.append(newLongitute((azimuth - beam / 2), distance * 1000)).append(",");
        polygon = polygon.append(newLattitute((azimuth - beam / 2), distance * 1000)).append(",0 ");
        polygon = polygon.append(newLongitute((azimuth - beam / 2), distance_before * 1000)).append(",");
        polygon = polygon.append(newLattitute((azimuth - beam / 2), distance_before * 1000)).append(",0 ");
        polygon = polygon.append(newLongitute((azimuth), distance_before * 1000)).append(",");
        polygon = polygon.append(newLattitute((azimuth), distance_before * 1000)).append(",0 ");
        polygon = polygon.append(newLongitute((azimuth + beam / 2), distance_before * 1000)).append(",");
        polygon = polygon.append(newLattitute((azimuth + beam / 2), distance_before * 1000)).append(",0 ");
        return polygon.toString();

    }
}
