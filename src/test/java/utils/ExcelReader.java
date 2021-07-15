package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    public static String getDataAsString(Workbook wb) {
        String result = null;
        DataFormatter formatter = new DataFormatter();
        Sheet sheet1 = wb.getSheetAt(0);
        for (Row row : sheet1) {
            for (Cell cell : row) {
                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                System.out.print(cellRef.formatAsString());
                System.out.print(" - ");
                // get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
                String text = formatter.formatCellValue(cell);
                System.out.println(text);
                result = text;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream("data.xls");
                HSSFWorkbook book = new HSSFWorkbook(inputStream)) {
            String value = book
                    .getSheet("data1")
                    .getRow(0)
                    .getCell(0)
                    .getStringCellValue();
            System.out.println(value);
            String dataAsString = getDataAsString(book);
            System.out.println(dataAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
