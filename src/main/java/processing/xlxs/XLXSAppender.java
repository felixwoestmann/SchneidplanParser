package processing.xlxs;

import debug.CustomLogger;
import model.Einzelteil;
import model.Schneidplan;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Takes the incoming data and appends it to a XLSX File.
 * Specifics have to be determined here.
 */
public class XLXSAppender extends AbstractXLXSProcessor {
    private final String SHEET_TO_APPEND = "Datenbank";

    private XSSFSheet searchCorrectSheet(Workbook workbook) {
        for (Sheet sheet : workbook) {
            if (sheet.getSheetName().equals(SHEET_TO_APPEND)) {
                return (XSSFSheet) sheet;
            }
        }
        return null;
    }

    @Override
    public void processAndWrite(Schneidplan schneidplan, String path) throws Exception {

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new File(path));
        } catch (IOException e) {
            throw new Exception(String.format("Keine kombatible Datei in %s gefunden.", path));
        }

        //get the correct Excel Sheet, defined in a constant
        XSSFSheet sheet = searchCorrectSheet(workbook);
        if (sheet == null) {
            throw new Exception(String.format("Arbeitsblatt %s konnte nicht gefunden werden.", SHEET_TO_APPEND));
        }

        createRows(schneidplan, sheet);

        //close the workbook
        // Write the output to a file
        FileOutputStream fileOut = null;
        saveAndCloseWorkbook(path, workbook);


    }

    /**
     * Creates a row in the sheet for each Einzelteil
     *
     * @param schneidplan
     * @param sheet
     */
    private void createRows(Schneidplan schneidplan, Sheet sheet) {
        //get last row number of that sheet and create rows
        int lastRowNumber = sheet.getLastRowNum();

        //create a row for each Einzelteil
        for (Einzelteil einzelteil : schneidplan.getEinzelteile()) {
            Row actualRow = sheet.createRow(++lastRowNumber);
            Cell cell = actualRow.createCell(1);
            cell.setCellValue(schneidplan.getMaterial_id());
            cell = actualRow.createCell(2);
            cell.setCellValue(schneidplan.getGewicht());
        }
    }
}
