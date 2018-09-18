package model;


import java.util.HashMap;
import java.util.List;

/**
 * Klasse beschreibt ein model.Einzelteil eines Schneidplans
 */
public class Einzelteil extends Parsable {


    private String anzahl;
    private String abmessungen;
    private String flaeche;
    private String bearbeitungszeit;
    private String schneidlaenge;
    private String gewicht;
    private String anzahl_einstechpunkte;
    private String einstechzeit;
    private String geofilename;


    public Einzelteil() {
        triggerSetFieldMap = new HashMap<>();
        try {
            initMap();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inits the map with trigger phrases and corresponding fields.
     *
     * @throws NoSuchFieldException | if no field with this name is found
     */
    void initMap() throws NoSuchFieldException {
        Class c = this.getClass();
        triggerSetFieldMap.put("ANZAHL:", c.getDeclaredField("anzahl"));
        triggerSetFieldMap.put("ABMESSUNGEN:", c.getDeclaredField("abmessungen"));
        triggerSetFieldMap.put("FLAECHE:", c.getDeclaredField("flaeche"));
        triggerSetFieldMap.put("BEARBEITUNGSZEIT:", c.getDeclaredField("bearbeitungszeit"));
        triggerSetFieldMap.put("SCHNEIDLAENGE:", c.getDeclaredField("schneidlaenge"));
        triggerSetFieldMap.put("GEWICHT:", c.getDeclaredField("gewicht"));
        triggerSetFieldMap.put("ANZAHL EINSTECHPUNKTE:", c.getDeclaredField("anzahl_einstechpunkte"));
        triggerSetFieldMap.put("EINSTECHZEIT", c.getDeclaredField("einstechzeit"));
        triggerSetFieldMap.put("GEOFILENAME:", c.getDeclaredField("geofilename"));
    }


    @Override
    List<String[]> toCSV() {
        return getCSVFromMap();
    }


    public String getAnzahl() {
        return anzahl;
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
