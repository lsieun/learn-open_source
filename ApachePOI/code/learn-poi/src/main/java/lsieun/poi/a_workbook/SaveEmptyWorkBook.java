package lsieun.poi.a_workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaveEmptyWorkBook {
    public static void main(String[] args) {
        String filename = "target/test.xlsx";

        //Create Workbook instance for xlsx/xls file input stream
        Workbook workbook = null;
        if(filename.toLowerCase().endsWith("xlsx")){
            workbook = new XSSFWorkbook();
        }else if(filename.toLowerCase().endsWith("xls")){
            workbook = new HSSFWorkbook();
        }

        try(OutputStream fileOut = new FileOutputStream(filename)) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
