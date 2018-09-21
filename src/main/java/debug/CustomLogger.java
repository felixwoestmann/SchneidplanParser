package debug;

import java.util.ArrayList;

public class CustomLogger {
    private static CustomLogger instance;
    private ArrayList<LogObserver> observers;

    private CustomLogger() {
        observers = new ArrayList<>();
    }

    /**
     * Lazy getter for singleton logger.
     *
     * @return returns instance
     */
    public static CustomLogger getInstance() {
        if (instance == null) {
            instance = new CustomLogger();
        }
        return instance;
    }

    public void addObserver(LogObserver logObserver) {
        observers.add(logObserver);
    }


    /**
     * Logs only to the log view for the robot.
     *
     * @param message message to be logged.
     */
    public void log(String message) {
        for (LogObserver observer : observers) {
            observer.logForUI(message);
            System.out.println(message);
        }
    }


}
