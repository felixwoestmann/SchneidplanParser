package processing.xlxs;

import model.Einzelteil;
import model.Schneidplan;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
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

    private ArrayList<XSSFRow> createRows(Schneidplan schneidplan, XSSFSheet sheet) {
        ArrayList<XSSFRow> rows=new ArrayList<>();
        for (Einzelteil einzelteil : schneidplan.getEinzelteile()) {
            XSSFRow row= sheet.createRow()
        }
        return rows;
    }

    @Override
    public void processAndWrite(Schneidplan schneidplan, String path) throws Exception {

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new File("C:\\Users\\Felix\\git\\Reference\\schneidtable.xlsx"));
        } catch (IOException e) {
            throw new Exception(String.format("Keine kombatible Datei in %s gefunden.", path));
        }

        XSSFSheet sheet = searchCorrectSheet(workbook);
        if (sheet == null) {
            throw new Exception(String.format("Arbeitsblatt %s konnte nicht gefunden werden.", SHEET_TO_APPEND));
        }
        DataFormatter dataFormatter = new DataFormatter();
        Iterator<Cell> cellIterator=null;
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            cellIterator = row.cellIterator();
        }
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            cell.setCellValue("h");
            String cellValue = dataFormatter.formatCellValue(cell);
            System.out.print(cellValue + "\t");
        }

    }
}
