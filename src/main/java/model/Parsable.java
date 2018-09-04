package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * The Parsable is an abstract class. Every inherited class is able to store methods as values to a trigger phrase in the map.
 * The Map will be initialized and trigger phrases will be provided.
 * Depending if the trigger phrase is present in the map, the method will be invoked!
 */
public abstract class Parsable {

    //hashmap to triggerSetMethodMap attributes to tabl elemetns
    HashMap<String, Method> triggerSetMethodMap;

    /**
     * Inits the map with trigger phrases an decides wich method to call, according to those
     * @throws NoSuchMethodException
     */
    abstract void initMap() throws NoSuchMethodException;
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
}
