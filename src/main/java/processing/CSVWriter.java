package processing;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import model.Schneidplan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class CSVWriter {

    public void write() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Parser parser = new Parser();
        Schneidplan schneidplan = parser.parseSchneidplan("Reference/einrichteplan_1.htm");
        Writer writer = new FileWriter(new File("beans.csv"));
        StatefulBeanToCsvBuilder beanToCsv = new StatefulBeanToCsvBuilder(writer);
        StatefulBeanToCsv build = beanToCsv.build();
        build.write(schneidplan.getEinzelteile());
        writer.close();

    }
}
