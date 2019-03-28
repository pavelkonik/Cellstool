package model;


import main.Cell;
import model.kml.KMLforcell;

import ui.ControllerPDCellsToKML;
import javafx.scene.paint.Color;

import java.util.List;

public class CellsToKML extends Model {
    public double getBeam() {
        return beam;
    }

    public void setBeam(double beam) {
        this.beam = beam;
    }

    public double getCellsize() {
        return cellsize;
    }

    public void setCellsize(double cellsize) {
        this.cellsize = cellsize;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private double beam;
    private double cellsize;
    private Color color;

    @Override
    public void calculate(List<Cell> cellsList) {
        if (cellsList == null) {
            ControllerPDCellsToKML.logInfo.setLogData("Can't get main.PD data" + '\n');
            return;
        }
        KMLforcell kmlforcell = new KMLforcell();
        kmlforcell.KMLcreatefile();
        kmlforcell.StyleforKMLfile();
        kmlforcell.KMLcreatePlacemark(cellsList);
    }


}
