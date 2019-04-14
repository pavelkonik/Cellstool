package com.pavelk.model.kml;

import java.util.List;

public interface KML {

     void KMLcreatefile();

    void StyleforKMLfile();

    <T> void KMLcreatePlacemark(List<T> list);
}
