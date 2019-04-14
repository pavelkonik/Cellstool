package com.pavelk.model;


import com.pavelk.main.Cell;
import com.pavelk.main.Const;
import com.pavelk.uicontrollers.ControllerCellsToKML;
import com.pavelk.uicontrollers.ControllerPDCellsToKML;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GetDataFromCSV extends Model {
    private String[] header;
    private String filename;
    private char separator;
    private List<String[]> allRows = new LinkedList<>();
    public int cellIDindex;
    public int cellnameindex;
    public int cellazindex;
    public int celllongindex;
    public int celllattindex;
    public int cellheightindex;
    public int cellpscindex;
    public int cellcpichindex;


    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public char separatorStrtoChar(String value) {
        char separator = 0;
        switch (value) {
            case ";":
                separator = ';';
                break;
            case ",":
                separator = ',';
                break;
            case "tab":
                separator = '\t';
                break;
        }
        return separator;
    }

    public List<Cell> getDatafromCSVfile(String strseparator) {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        separator = separatorStrtoChar(strseparator);
        if (selectedFile == null || separator == 0) {
            return null;
        }
        filename = selectedFile.getAbsolutePath();
        List<Cell> cells = null;
        try (FileReader fr = new FileReader(filename); BufferedReader br = new BufferedReader(fr)) {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(separator).
                    withIgnoreQuotations(true).
                    build();
            CSVReader csvReader =
                    new CSVReaderBuilder(br)
                            //  .withSkipLines(1)
                            .withCSVParser(csvParser).build();
            String[] firstline;
            String[] nextline;
            String[] line;
            try {
                while ((firstline = csvReader.readNext()) != null) {
                    header = firstline;
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < header.length; i++) {
                if (header[i].equalsIgnoreCase(Const.CELLID))
                    cellIDindex = i;
                if (header[i].equalsIgnoreCase(Const.CELLNAME))
                    cellnameindex = i;
                if (header[i].equalsIgnoreCase(Const.AZIMUTH))
                    cellazindex = i;
                if (header[i].equalsIgnoreCase(Const.LONGITUDE))
                    celllongindex = i;
                if (header[i].equalsIgnoreCase(Const.LATITUDE))
                    celllattindex = i;
                if (header[i].equalsIgnoreCase(Const.HEIGHT))
                    cellheightindex = i;
                if (header[i].equalsIgnoreCase(Const.PSC))
                    cellpscindex = i;
                if (header[i].equalsIgnoreCase(Const.CPICHPOWRE))
                    cellcpichindex = i;
            }
            if (cellIDindex == 0 && cellnameindex == 0 && cellazindex == 0
                    && celllongindex == 0 && celllattindex == 0) {
                return null;
            }
            cells = new ArrayList<>();
            while ((nextline = csvReader.readNext()) != null) {
                if (nextline != null) {
                    line = new String[]{nextline[cellIDindex], nextline[cellnameindex],
                            nextline[cellazindex], nextline[celllongindex], nextline[celllattindex], nextline[cellheightindex],
                            nextline[cellpscindex], nextline[cellcpichindex]};
                    allRows.add(line);
                    cells.add(new Cell(nextline[cellnameindex], nextline[cellIDindex], nextline[cellpscindex], nextline[cellazindex],
                            nextline[celllattindex], nextline[celllongindex], nextline[cellcpichindex], nextline[cellheightindex]));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ControllerCellsToKML.CellsorPDWindows != null)
            if (ControllerCellsToKML.CellsorPDWindows) {
                ControllerCellsToKML.logInfo.setLogData(cells.size() + " cells were got from csv file" + '\n');
                ControllerCellsToKML.CellsorPDWindows = null;
            }
        if (ControllerPDCellsToKML.CellsorPDWindows != null)
            if (!ControllerPDCellsToKML.CellsorPDWindows) {
                ControllerPDCellsToKML.logInfo.setLogData(cells.size() + " cells were got from csv file" + '\n');
                ControllerPDCellsToKML.CellsorPDWindows = null;
            }
        return cells;
    }


}
