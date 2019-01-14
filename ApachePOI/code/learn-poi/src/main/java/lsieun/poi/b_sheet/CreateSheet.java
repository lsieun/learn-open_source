package lsieun.poi.b_sheet;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateSheet {
    public static void main(String[] args) throws IOException {
        String filename = "target/country.xlsx";
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Countries");
        sheet = workbook.createSheet("States");
        sheet = workbook.createSheet("Colors");
        //lets write the excel data to file now
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.close();
    }
}
