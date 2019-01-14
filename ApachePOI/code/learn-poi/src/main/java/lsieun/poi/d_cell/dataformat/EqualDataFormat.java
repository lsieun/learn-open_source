package lsieun.poi.d_cell.dataformat;

import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EqualDataFormat {
    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook();
        DataFormat dataFormat1 = workbook.createDataFormat();
        DataFormat dataFormat2 = workbook.createDataFormat();

        System.out.println(dataFormat1 == dataFormat2); // true
    }
}
