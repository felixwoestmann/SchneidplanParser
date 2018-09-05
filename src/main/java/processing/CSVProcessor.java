package processing;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import model.Einzelteil;
import model.Schneidplan;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVProcessor {

    public String writeToString(Schneidplan schneidplan) {
        StringWriter writer=new StringWriter();
        write(schneidplan,writer);
        return writer.toString();
    }

    public void writeToFile(Schneidplan schneidplan, String path) {
        FileWriter writer= null;
        try {
            writer = new FileWriter(path);
        } catch (IOException e) {
            System.out.println("Etwas lief schief!");
            e.printStackTrace();
        }
        write(schneidplan,writer);
    }

    private void write(Schneidplan schneidplan, Writer writer) {


        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();


        try {
            beanToCsv.write(schneidplan.getEinzelteile());
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.out.println("Etwas lief schief");
            e.printStackTrace();
        }


    }
}
