package com.pavelk.model.kml;

import java.util.List;

public interface KML {

     void kmlCreateFile();

    void styleForKmlFile();

    <T> void kmlCreatePlacemark(List<T> list);
}
