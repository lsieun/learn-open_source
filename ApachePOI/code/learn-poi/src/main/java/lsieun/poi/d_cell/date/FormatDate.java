package lsieun.poi.d_cell.date;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FormatDate {
    public static void main(String[] args) throws IOException {
        Date now = new Date();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hello");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);

        cell.setCellValue(now);

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        cell.setCellStyle(dateCellStyle);

        String filename = "target/FormatDate.xlsx";
        OutputStream out = new FileOutputStream(filename);
        workbook.write(out);
        out.close();
    }
}
