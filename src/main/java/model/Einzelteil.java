package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Klasse beschreibt ein model.Einzelteil eines Schneidplans
 */
public class Einzelteil extends Parsable {

    private String anazhl;
    private String abmessungen;
    private String flaeche;
    private String bearbeitungszeit;
    private String schneidlaenge;
    private String gewicht;
    private String anzahl_einstechpunkte;
    private String einstechzeit;
    private String geofilename;


    public Einzelteil() {
        triggerSetMethodMap = new HashMap<>();
        try {
            initMap();
        } catch (NoSuchMethodException e) {
            System.out.println("Diese Methode gibt es nicht.");
            e.printStackTrace();
        }
    }

    /**
     * Inits the map with trigger phrases an decides wich method to call, according to those
     *
     * @throws NoSuchMethodException
     */
    void initMap() throws NoSuchMethodException {
        triggerSetMethodMap.put("ANZAHL:", this.getClass().getMethod("setAnazhl", String.class));
        triggerSetMethodMap.put("ABMESSUNGEN:", this.getClass().getMethod("setAbmessungen", String.class));
        triggerSetMethodMap.put("FLAECHE:", this.getClass().getMethod("setFlaeche", String.class));
        triggerSetMethodMap.put("BEARBEITUNGSZEIT:", this.getClass().getMethod("setBearbeitungszeit", String.class));
        triggerSetMethodMap.put("SCHNEIDLAENGE:", this.getClass().getMethod("setSchneidlaenge", String.class));
        triggerSetMethodMap.put("GEWICHT:", this.getClass().getMethod("setGewicht", String.class));
        triggerSetMethodMap.put("ANZAHL EINSTECHPUNKTE:", this.getClass().getMethod("setAnzahl_einstechpunkte", String.class));
        triggerSetMethodMap.put("EINSTECHZEIT", this.getClass().getMethod("setEinstechzeit", String.class));
        triggerSetMethodMap.put("GEOFILENAME:", this.getClass().getMethod("setGeofilename", String.class));
    }


    public String getAnazhl() {
        return anazhl;
    }

    public void setAnazhl(String anazhl) {
        this.anazhl = anazhl;
    }

    public String getAbmessungen() {
        return abmessungen;
    }

    public void setAbmessungen(String abmessungen) {
        this.abmessungen = abmessungen;
    }

    public String getFlaeche() {
        return flaeche;
    }

    public void setFlaeche(String flaeche) {
        this.flaeche = flaeche;
    }

    public String getBearbeitungszeit() {
        return bearbeitungszeit;
    }

    public void setBearbeitungszeit(String bearbeitungszeit) {
        this.bearbeitungszeit = bearbeitungszeit;
    }

    public String getSchneidlaenge() {
        return schneidlaenge;
    }

    public void setSchneidlaenge(String schneidlaenge) {
        this.schneidlaenge = schneidlaenge;
    }

    public String getGewicht() {
        return gewicht;
    }

    public void setGewicht(String gewicht) {
        this.gewicht = gewicht;
    }

    public String getAnzahl_einstechpunkte() {
        return anzahl_einstechpunkte;
    }

    public void setAnzahl_einstechpunkte(String anzahl_einstechpunkte) {
        this.anzahl_einstechpunkte = anzahl_einstechpunkte;
    }

    public String getEinstechzeit() {
        return einstechzeit;
    }

    public void setEinstechzeit(String einstechzeit) {
        this.einstechzeit = einstechzeit;
    }

    public String getGeofilename() {
        return geofilename;
    }

    public void setGeofilename(String geofilename) {
        this.geofilename = geofilename;
    }
}
