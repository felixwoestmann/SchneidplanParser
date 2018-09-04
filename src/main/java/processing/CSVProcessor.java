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

    public void write() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Parser parser = new Parser();
        Schneidplan schneidplan = parser.parseSchneidplan("Reference/einrichteplan_1.htm");

        try (
                Writer writer = new FileWriter("beans.csv");
        ) {
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();



            beanToCsv.write(schneidplan.getEinzelteile());

        }
    }
}
