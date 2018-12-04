package processing.xlxs;

import debug.CustomLogger;
import org.apache.poi.ss.usermodel.Workbook;
import processing.Processor;

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class AbstractXLXSProcessor implements Processor {

    @Override
    public String getFileExtension() {
        return "*.xlsx";
    }

    @Override
    public String getFileExtensionName() {
        return "Excel/(.xlsx)";
    }

    void saveAndCloseWorkbook(String path, Workbook workbook) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();
        } catch (IOException e) {
            CustomLogger.getInstance().log(e);

        }
    }
}
