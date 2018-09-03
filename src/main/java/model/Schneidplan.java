package model;

import java.util.ArrayList;

/**
 * Ein model.Schneidplan definiert verschiedene Informationen bestehnd aus einem Kopf und mehreren Einzelteilen
 */

public class Schneidplan {

    //Informationen aus dem Kopf
    private String programmname;
    private String material_id;
    private String zuschnitt;
    private String mindestzuschnitt;
    private String gewicht;
    private String maschinenzeit;
    private String gesamtschnittlaenge;
    private String anzahl_programmdurchlaeufe;
    private String verschnitt;

    private ArrayList<Einzelteil> einzelteile;


}
