package model;


import main.Cell;
import main.PD;
import model.kml.KMLforPD;

import ui.ControllerPDCellsToKML;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PDCellsToKml extends Model {
    private double beam;
    private double cellsize;

    private LocalDate start_time;
    private LocalDate end_time;
    private boolean agregate;
    private List<PD> pdList = new ArrayList<>();

    public boolean isAgregate() {
        return agregate;
    }

    public List<PD> getPdList() {
        return pdList;
    }

    public void setPdList(List<PD> pdList) {
        this.pdList = pdList;
    }

    public void setAgregate(boolean agregate) {
        this.agregate = agregate;
    }

    public LocalDate getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDate start_time) {
        this.start_time = start_time;
    }

    public LocalDate getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDate end_time) {
        this.end_time = end_time;
    }

    @Override
    public void calculate(List<Cell> cellsList) {
        getPDDataForCalculate(cellsList);
        if (pdList == null) {
            ControllerPDCellsToKML.logInfo.setLogData("Can't get main.PD data" + '\n');
            return;
        }
        if (pdList.size() == 0) {
            ControllerPDCellsToKML.logInfo.setLogData("main.PD data isn't" + '\n');
            return;
        }
        KMLforPD kmlforcell = new KMLforPD();
        kmlforcell.KMLcreatefile();
        kmlforcell.StyleforKMLfile();
        kmlforcell.KMLcreatePlacemark(pdList);
    }

    private void getPDDataForCalculate(List<Cell> PDCellsList) {
        setMySQLConnection(false);
        dbHandler = new DatabaseHandler();
        try {
            pdList = dbHandler.getPDSQL(PDCellsList, start_time, end_time, agregate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
