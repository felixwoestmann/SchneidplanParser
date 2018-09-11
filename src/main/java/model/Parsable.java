package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Parsable is an abstract class. Every inherited class is able to store methods as values to a trigger phrase in the map.
 * The Map will be initialized and trigger phrases will be provided.
 * Depending if the trigger phrase is present in the map, the method will be invoked!
 */
public abstract class Parsable {

    //hashmap to triggerSetMethodMap attributes to tabl elemetns
    HashMap<String, Field> triggerSetFieldMap;

    /**
     * Inits the map with trigger phrases an decides wich method to call, according to those
     *
     * @throws NoSuchMethodException
     */
    abstract void initMap() throws NoSuchFieldException, NoSuchMethodException;

    /**
     * Gets a trigger phrase and the content and fills the correct field, using the map.
     *
     * @param trigger
     * @param content
     */
    public void setAttribute(String trigger, String content) {
        Field actualfield = triggerSetFieldMap.get(trigger);
        if (actualfield != null) {
            try {
                actualfield.set(this, content);
            } catch (IllegalAccessException e) {
                System.out.println("Irgendwas lief hier sehr schief!");
                e.printStackTrace();
            }
        }
    }

    List<String[]> getCSVFromMap() {
        ArrayList<String[]> list = new ArrayList<>();
        triggerSetFieldMap.keySet().forEach(s -> {
            String[] row = new String[2];
            row[0] = s;
            try {
                row[1] = String.valueOf(triggerSetFieldMap.get(s).get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            list.add(row);
        });
        return list;
    }

    abstract List<String[]> toCSV();
}
