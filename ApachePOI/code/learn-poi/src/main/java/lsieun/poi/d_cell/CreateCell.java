package lsieun.poi.d_cell;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lsieun.poi.entity.Employee;

public class CreateCell {
    public static void main(String[] args) throws IOException {

        List<Employee> list = new ArrayList<Employee>();
        Employee ea = new Employee("张三", "zhangsan@abc.com", new Date(), 100.005);
        Employee eb = new Employee("李四", "lisi@example.com", new Date(), 20.7897);
        list.add(ea);
        list.add(eb);



        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");
        for(int i=0; i<list.size(); i++) {
            Employee item = list.get(i);
            String name = item.getName();
            String email = item.getEmail();
            Date dateOfBirth = item.getDateOfBirth();
            double salary = item.getSalary();

            Row row = sheet.createRow(i);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(name);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(email);

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(dateOfBirth);

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(salary);
        }


        String filename = "target/employee.xlsx";
        OutputStream out = new FileOutputStream(filename);
        workbook.write(out);
        out.close();
        System.out.println(filename + " written successfully");

    }
}
