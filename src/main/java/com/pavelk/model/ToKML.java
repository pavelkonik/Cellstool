package com.pavelk.model;

import com.pavelk.main.Cell;

import java.util.List;

public interface ToKML {
    void getCellDataForCalculate();

    void getCellsDataForCalculateFromDB();

    void getCellsDataForCalculateFromFile();

    void getCellsDataForCalculateFromCSV();

    void calculate(List<Cell> cellsList);

}
