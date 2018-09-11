package processing;


import model.Einzelteil;
import model.Schneidplan;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Consumer;

public class CSVProcessor {
    private final String SEPERATOR = ",";

    public String writeToString(Schneidplan schneidplan) {
        StringWriter writer = new StringWriter();
        write(schneidplan, writer);
        return writer.toString();
    }

    public void writeToFile(Schneidplan schneidplan, String path) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
        } catch (IOException e) {
            System.out.println("Etwas lief schief!");
            e.printStackTrace();
        }
        write(schneidplan, writer);
    }


    private void write(Schneidplan schneidplan, Writer writer) {
        StringBuilder sb = new StringBuilder();
        //addheadings
        appendLine(sb, "Beschreibung:", "Wert:");
        schneidplan.toCSV().forEach(strings -> appendLine(sb, strings[0], strings[1]));
        try {
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void appendLine(StringBuilder sb, String leftcolumn, String rightcolumn) {
        sb.append(leftcolumn).append(SEPERATOR).append(rightcolumn).append("\n");
    }
}
