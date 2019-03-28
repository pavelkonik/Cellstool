package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PD {

    public void setStart_time(LocalDate start_time) {
        this.start_time = start_time;
    }

    public LocalDate getStart_time() {
        return start_time;
    }

    public LocalDate getEnd_time() {
        return end_time;
    }

    public Cell getCell() {
        return cell;
    }

    public void setEnd_time(LocalDate end_time) {
        this.end_time = end_time;
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

    public int[] getPdarray() {
        return Pdarray;
    }

    public void setPdarray(int[] pdarray) {
        Pdarray = pdarray;
    }

    private LocalDate start_time;
    private LocalDate end_time;
    private Cell cell;
    private int CI;
    private int totalCount;
    private int[] Pdarray = new int[42];

    public int getCI() {
        return CI;
    }


    public void setCI(int CI) {
        this.CI = CI;
    }

    public PD(Cell cell,String strdate, String totalCount, String[] StrPDarray) {
        this.end_time = LocalDate.parse(strdate.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.CI = cell.getCellID();
        this.totalCount = Integer.parseInt(totalCount);
        for (int i = 0; i < StrPDarray.length; i++) {
            Pdarray[i] = Integer.parseInt(StrPDarray[i]);
        }
        this.cell = cell;
    }

}
