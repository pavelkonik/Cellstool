package com.pavelk.main;

import javafx.stage.FileChooser;

import java.io.*;
import java.util.List;

public class CreateFile {
    public void create1File() {
        FileChooser selectedopenFiles = new FileChooser();
        List<File> fileopen = selectedopenFiles.showOpenMultipleDialog(null);
        if (fileopen == null || fileopen.size() == 0) return;

        FileChooser selectedsaveFile = new FileChooser();
        File filesave = selectedsaveFile.showSaveDialog(null);
        if (filesave == null) return;

        String s = "";

        try (FileWriter fileWriter = new FileWriter(filesave);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (int i = 0; i < fileopen.size(); i++) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileopen.get(i)));
                while ((s = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
