package utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
    public static void main(String[] args) {
        try (HSSFWorkbook book = new HSSFWorkbook();
             FileOutputStream output = new FileOutputStream("data.xls");) {
            HSSFSheet data1 = book.createSheet("Data1");
            HSSFRow row = data1.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("Hello world");
            book.createSheet("Data2");
            book.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
