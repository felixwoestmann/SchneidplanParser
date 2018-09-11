package model;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * Klasse beschreibt ein model.Einzelteil eines Schneidplans
 */
public class Einzelteil extends Parsable {


     String anazhl;
     String abmessungen;
     String flaeche;
     String bearbeitungszeit;
     String schneidlaenge;
     String gewicht;
     String anzahl_einstechpunkte;
     String einstechzeit;
     String geofilename;


    public Einzelteil() {
        triggerSetFieldMap = new HashMap<>();
        try {
            initMap();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inits the map with trigger phrases an decides wich method to call, according to those
     *
     * @throws NoSuchMethodException
     */
    void initMap() throws NoSuchFieldException {
        Class thisclass = this.getClass();
        triggerSetFieldMap.put("ANZAHL:", thisclass.getDeclaredField("anazhl"));
        triggerSetFieldMap.put("ABMESSUNGEN:", thisclass.getDeclaredField("abmessungen"));
        triggerSetFieldMap.put("FLAECHE:", thisclass.getDeclaredField("flaeche"));
        triggerSetFieldMap.put("BEARBEITUNGSZEIT:", thisclass.getDeclaredField("bearbeitungszeit"));
        triggerSetFieldMap.put("SCHNEIDLAENGE:", thisclass.getDeclaredField("schneidlaenge"));
        triggerSetFieldMap.put("GEWICHT:", thisclass.getDeclaredField("gewicht"));
        triggerSetFieldMap.put("ANZAHL EINSTECHPUNKTE:", thisclass.getDeclaredField("anzahl_einstechpunkte"));
        triggerSetFieldMap.put("EINSTECHZEIT", thisclass.getDeclaredField("einstechzeit"));
        triggerSetFieldMap.put("GEOFILENAME:", thisclass.getDeclaredField("geofilename"));
    }

    @Override
    List<String[]> toCSV() {
       return getCSVFromMap();
    }


    public String getAnazhl() {
        return anazhl;
    }

    public String getAbmessungen() {
        return abmessungen;
    }


    public String getFlaeche() {
        return flaeche;
    }


    public String getBearbeitungszeit() {
        return bearbeitungszeit;
    }


    public String getSchneidlaenge() {
        return schneidlaenge;
    }


    public String getGewicht() {
        return gewicht;
    }


    public String getAnzahl_einstechpunkte() {
        return anzahl_einstechpunkte;
    }

    public String getEinstechzeit() {
        return einstechzeit;
    }

    public String getGeofilename() {
        return geofilename;
    }

}
