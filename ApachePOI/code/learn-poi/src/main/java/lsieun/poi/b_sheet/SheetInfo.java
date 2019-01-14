package lsieun.poi.b_sheet;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class SheetInfo {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        String filename = "target/test.xlsx";
        Workbook workbook = WorkbookFactory.create(new File(filename));
        //Get the number of sheets in the xlsx file
        int numberOfSheets = workbook.getNumberOfSheets();
        System.out.println("Workbook has " + numberOfSheets + " Sheets.");

        for(int i=0; i < numberOfSheets; i++){
            //Get the nth sheet from the workbook
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            int lastRowNum = sheet.getLastRowNum();
            System.out.println("sheet name: " + sheetName);
            System.out.println("lastRowNum: " + lastRowNum);
            System.out.println("===");
        }

    }
}
