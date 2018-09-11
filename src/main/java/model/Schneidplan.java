package model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * Ein model.Schneidplan definiert verschiedene Informationen bestehnd aus einem Kopf und mehreren Einzelteilen
 */

public class Schneidplan extends Parsable {
    //Informationen aus dem Kopf
    String programmname;
    String material_id;
    String zuschnitt;
    String mindestzuschnitt;
    String gewicht;
    String maschinenzeit;
    String gesamtschnittlaenge;
    String anzahl_programmdurchlaeufe;
    String verschnitt;

    private ArrayList<Einzelteil> einzelteile;

    public Schneidplan() {
        triggerSetFieldMap = new HashMap<>();
        try {
            initMap();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    void initMap() throws NoSuchFieldException {
        Class thisclass = this.getClass();
        triggerSetFieldMap.put("PROGRAMMNAME:", thisclass.getDeclaredField("programmname"));
        triggerSetFieldMap.put("MATERIAL-ID (TAFEL):", thisclass.getDeclaredField("material_id"));
        triggerSetFieldMap.put("ZUSCHNITT:", thisclass.getDeclaredField("zuschnitt"));
        triggerSetFieldMap.put("MINDESTZUSCHNITT:", thisclass.getDeclaredField("mindestzuschnitt"));
        triggerSetFieldMap.put("GEWICHT:", thisclass.getDeclaredField("gewicht"));
        triggerSetFieldMap.put("MASCHINENZEIT:", thisclass.getDeclaredField("maschinenzeit"));
        triggerSetFieldMap.put("GESAMTSCHNITTLAENGE:", thisclass.getDeclaredField("gesamtschnittlaenge"));
        triggerSetFieldMap.put("ANZAHL DER PROGRAMMDURCHLAEUFE:", thisclass.getDeclaredField("anzahl_programmdurchlaeufe"));
        triggerSetFieldMap.put("VERSCHNITT:", thisclass.getDeclaredField("verschnitt"));
    }

    @Override
    public List<String[]> toCSV() {
        //turn fields into rows
        ArrayList<String[]> list = new ArrayList<>(getCSVFromMap());
        //add divider
        String[] row = new String[2];
        row[0] = "Einzelteile:";
        row[1] = "";
        list.add(row);
        //add einzelteile
        einzelteile.forEach(einzelteil -> list.addAll(einzelteil.toCSV()));
        return list;
    }


    public String getProgrammname() {
        return programmname;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public String getZuschnitt() {
        return zuschnitt;
    }

    public String getMindestzuschnitt() {
        return mindestzuschnitt;
    }

    public String getGewicht() {
        return gewicht;
    }

    public String getMaschinenzeit() {
        return maschinenzeit;
    }

    public String getGesamtschnittlaenge() {
        return gesamtschnittlaenge;
    }

    public String getAnzahl_programmdurchlaeufe() {
        return anzahl_programmdurchlaeufe;
    }

    public String getVerschnitt() {
        return verschnitt;
    }

    public ArrayList<Einzelteil> getEinzelteile() {
        return einzelteile;
    }

    public void setEinzelteile(ArrayList<Einzelteil> einzelteile) {
        this.einzelteile = einzelteile;
    }
}
