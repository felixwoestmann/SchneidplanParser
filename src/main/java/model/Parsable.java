package model;

import debug.CustomLogger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Parsable is an abstract class. Every inherited class is able to store class fields (via reflection) as values to a trigger phrase in the map.
 * The Map will be initialized and trigger phrases will be provided.
 * Depending if the trigger phrase is present in the map, the value will be saved to the field!
 */
public abstract class Parsable {


    HashMap<String, Field> triggerSetFieldMap;

    /**
     * Inits the map with trigger phrases an decides wich method to call, according to those
     *
     * @throws NoSuchFieldException
     */
    abstract void initMap() throws NoSuchFieldException;

    /**
     * Gets a trigger phrase and the content and fills the correct field, using the map.
     *
     * @param trigger The trigger phrase
     * @param content The content to be saved
     */
    public void setAttribute(String trigger, String content) {
        Field field = triggerSetFieldMap.get(trigger);
        if (field != null) {
            try {
                field.set(this, content);
            } catch (IllegalAccessException e) {
                CustomLogger.getInstance().log(e,"Irgendwas lief hier sehr schief!");
            }
        }
    }

    /**
     * Returns a List of String Arrays with the size two.
     * The String arrays are containing the trigger phrase and the value.
     * So to speak: The method serializes the clas as a CSV file.
     *
     * @return List of String Arrays
     */
    List<String[]> getCSVFromMap() {
        ArrayList<String[]> list = new ArrayList<>();
        triggerSetFieldMap.keySet().forEach(s -> {
            String[] row = new String[2];
            row[0] = s;
            try {
                row[1] = String.valueOf(triggerSetFieldMap.get(s).get(this));
            } catch (IllegalAccessException e) {
                CustomLogger.getInstance().log(e);
            }
            list.add(row);
        });
        return list;
    }

    abstract List<String[]> toCSV();
}
