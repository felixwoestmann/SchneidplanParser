package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Ein model.Schneidplan definiert verschiedene Informationen bestehnd aus einem Kopf und mehreren Einzelteilen
 */

public class Schneidplan {
    //hashmap to triggerSetMethodMap attributes to tabl elemetns
    private HashMap<String, Method> triggerSetMethodMap;
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

    public Schneidplan() {
        triggerSetMethodMap =new HashMap<>();
        try {
            initMap();
        } catch (NoSuchMethodException e) {
            System.out.println("Diese Methode gibt es nicht.");
            e.printStackTrace();
        }
    }

    /**
     * Inits the map with trigger phrases an decides wich method to call, according to those
     * @throws NoSuchMethodException
     */
    private void initMap() throws NoSuchMethodException {
       triggerSetMethodMap.put("PROGRAMMNAME:",this.getClass().getMethod("setProgrammname", String.class));
       triggerSetMethodMap.put("MATERIAL-ID (TAFEL):",this.getClass().getMethod("setMaterial_id", String.class));
       triggerSetMethodMap.put("ZUSCHNITT:",this.getClass().getMethod("setZuschnitt", String.class));
       triggerSetMethodMap.put("MINDESTZUSCHNITT:",this.getClass().getMethod("setMindestzuschnitt", String.class));
       triggerSetMethodMap.put("GEWICHT:",this.getClass().getMethod("setGewicht", String.class));
       triggerSetMethodMap.put("MASCHINENZEIT:",this.getClass().getMethod("setMaschinenzeit", String.class));
       triggerSetMethodMap.put("GESAMTSCHNITTLAENGE:",this.getClass().getMethod("setGesamtschnittlaenge", String.class));
       triggerSetMethodMap.put("ANZAHL DER PROGRAMMDURCHLAEUFE:",this.getClass().getMethod("setAnzahl_programmdurchlaeufe", String.class));
       triggerSetMethodMap.put("VERSCHNITT:",this.getClass().getMethod("setVerschnitt", String.class));
    }

    /**
     * Gets a trigger phrase and the content and fills the correct field, using the map.
     * @param trigger
     * @param content
     */
    public void setAttribute(String trigger, String content){
        Method actualmethod=triggerSetMethodMap.get(trigger);
        if (actualmethod != null) {
            try {
                actualmethod.invoke(this,content);
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Irgendwas lief hier sehr schief!");
                e.printStackTrace();
            }
        }
    }

    public String getProgrammname() {
        return programmname;
    }

    public void setProgrammname(String programmname) {
        this.programmname = programmname;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getZuschnitt() {
        return zuschnitt;
    }

    public void setZuschnitt(String zuschnitt) {
        this.zuschnitt = zuschnitt;
    }

    public String getMindestzuschnitt() {
        return mindestzuschnitt;
    }

    public void setMindestzuschnitt(String mindestzuschnitt) {
        this.mindestzuschnitt = mindestzuschnitt;
    }

    public String getGewicht() {
        return gewicht;
    }

    public void setGewicht(String gewicht) {
        this.gewicht = gewicht;
    }

    public String getMaschinenzeit() {
        return maschinenzeit;
    }

    public void setMaschinenzeit(String maschinenzeit) {
        this.maschinenzeit = maschinenzeit;
    }

    public String getGesamtschnittlaenge() {
        return gesamtschnittlaenge;
    }

    public void setGesamtschnittlaenge(String gesamtschnittlaenge) {
        this.gesamtschnittlaenge = gesamtschnittlaenge;
    }

    public String getAnzahl_programmdurchlaeufe() {
        return anzahl_programmdurchlaeufe;
    }

    public void setAnzahl_programmdurchlaeufe(String anzahl_programmdurchlaeufe) {
        this.anzahl_programmdurchlaeufe = anzahl_programmdurchlaeufe;
    }

    public String getVerschnitt() {
        return verschnitt;
    }

    public void setVerschnitt(String verschnitt) {
        this.verschnitt = verschnitt;
    }

    public ArrayList<Einzelteil> getEinzelteile() {
        return einzelteile;
    }

    public void setEinzelteile(ArrayList<Einzelteil> einzelteile) {
        this.einzelteile = einzelteile;
    }
}
