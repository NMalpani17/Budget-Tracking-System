package edu.neu.csye6200.controller;

import edu.neu.csye6200.service.ExpenseCSVService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/expenses/csv")
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseCSVController {

    @Autowired
    private ExpenseCSVService expenseCSVService;

    @PostMapping
    public ResponseEntity<String> uploadExpenseCSV(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Convert Excel to CSV
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);

            // Assuming there is only one sheet in the Excel file
            Sheet sheet = workbook.getSheetAt(0);

            List<String> csvDataList = new ArrayList<>();
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();

                StringBuilder csvRow = new StringBuilder();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    csvRow.append(cell.toString()).append(",");
                }
                csvDataList.add(csvRow.substring(0, csvRow.length() - 1));
            }

            workbook.close();

            for (String csvRow : csvDataList) {
                System.out.println(csvRow);
            }
            expenseCSVService.addCSVExpenseEqual(csvDataList);


            return new ResponseEntity<>("File uploaded and converted successfully.", HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("Failed to process the uploaded file.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
