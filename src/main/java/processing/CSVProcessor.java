package processing;


import model.Schneidplan;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

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
