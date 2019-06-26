package com.pavelk.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pd {

    public LocalDate getEndTime() {
        return endTime;
    }

    public Cell getCell() {
        return cell;
    }

    public void setEndTime(LocalDate end_time) {
        this.endTime = end_time;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int[] getPdArray() {
        return pdArray;
    }

    public void setPdArray(int[] pdArray) {
        this.pdArray = pdArray;
    }

    private LocalDate startTime;
    private LocalDate endTime;
    private Cell cell;
    private int ci;
    private int totalCount;
    private int[] pdArray = new int[42];

    public int getCi() {
        return ci;
    }


    public void setCi(int ci) {
        this.ci = ci;
    }

    public Pd(Cell cell, String stringDate, String totalCount, String[] stringPdArray) {
        this.endTime = LocalDate.parse(stringDate.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.ci = cell.getCellId();
        this.totalCount = Integer.parseInt(totalCount);
        for (int i = 0; i < stringPdArray.length; i++) {
            pdArray[i] = Integer.parseInt(stringPdArray[i]);
        }
        this.cell = cell;
    }

}
