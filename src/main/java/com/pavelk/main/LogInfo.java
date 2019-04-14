package com.pavelk.main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class LogInfo {
        private  StringProperty logData = new SimpleStringProperty("");
        // methods that set/format logData based on changes from your com.pavelk.uicontrollers

        // provide public access to the property
        public  StringProperty logDataProperty() { return logData; }
        public  void setLogData(String data) {
                logData.set(logData.get() + data);
        }
        public  String getLogData() { return logData.get(); }
    }

