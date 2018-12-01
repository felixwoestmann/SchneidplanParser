package processing.xlxs;

import debug.CustomLogger;
import model.Schneidplan;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class XLXSProcessor extends AbstractXLXSProcessor {
    private final String[] COLUMNS = {"Beschreibung:", "Wert:"};
    private final String SHEETNAME = "Schneidplan";

    @Override
    public void processAndWrite(Schneidplan schneidplan, String path) {
        //large part of code are from https://www.callicoder.com/java-write-excel-file-apache-poi/
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();
        // Create a Sheet
        Sheet sheet = workbook.createSheet(SHEETNAME);

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for (int i = 0; i < COLUMNS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(COLUMNS[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rownumber = 1;
        for (String[] rowstring : schneidplan.toCSV()) {
            Row row = sheet.createRow(rownumber++);
            row.createCell(0).setCellValue(rowstring[0]);
            row.createCell(1).setCellValue(rowstring[1]);

        }
        // Resize all columns to fit the content size
        for (int i = 0; i < COLUMNS.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = null;
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
