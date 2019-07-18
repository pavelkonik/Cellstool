package com.pavelk.model;


import com.pavelk.main.Cell;
import com.pavelk.main.Pd;
import com.pavelk.model.db.DatabaseHandler;
import com.pavelk.model.kml.kmlForPd;

import com.pavelk.uicontrollers.ControllerPDCellsToKML;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PDCellsToKml extends Model {
    private double beam;
    private double cellsize;

    private LocalDate startTime;
    private LocalDate endTime;
    private boolean agregate;
    private List<Pd> pdList = new ArrayList<>();

    public boolean isAgregate() {
        return agregate;
    }

    public List<Pd> getPdList() {
        return pdList;
    }

    public void setPdList(List<Pd> pdList) {
        this.pdList = pdList;
    }

    public void setAgregate(boolean agregate) {
        this.agregate = agregate;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    @Override
    public void calculate(List<Cell> cellsList) {
        getPDDataForCalculate(cellsList);
        if (pdList == null) {
            ControllerPDCellsToKML.logInfo.setLogData("Can't get PD data" + '\n');
            return;
        }
        if (pdList.size() == 0) {
            ControllerPDCellsToKML.logInfo.setLogData("PD data isn't" + '\n');
            return;
        }
        kmlForPd kmlforcell = new kmlForPd();
        kmlforcell.kmlCreateFile();
        kmlforcell.styleForKmlFile();
        kmlforcell.kmlCreatePlacemark(pdList);
    }

    private void getPDDataForCalculate(List<Cell> PDCellsList) {
        setMySQLConnection(false);
        dbHandler = new DatabaseHandler();
        try {
            pdList = dbHandler.getPDSQL(PDCellsList, startTime, endTime, agregate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
