import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.Test;
import processing.CSVWriter;

import java.io.IOException;

public class TestCSVWriter {

    @Test
    public void testCSVWriter() {
        CSVWriter writer=new CSVWriter();
        try {
            writer.write();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }
}
