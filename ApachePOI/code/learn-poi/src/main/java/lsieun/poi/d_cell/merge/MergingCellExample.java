package lsieun.poi.d_cell.merge;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MergingCellExample {
    public static void main(String[] args) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hello World");
        Row row = sheet.createRow(1);
        Cell cell = row.createCell(1);
        cell.setCellValue("Two Cell");

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));

        OutputStream out = new FileOutputStream("target/merge.xlsx");
        workbook.write(out);
        out.close();
    }
}
