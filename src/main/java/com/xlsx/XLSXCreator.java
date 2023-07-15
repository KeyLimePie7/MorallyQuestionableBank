package com.xlsx;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bean.Transaction;

public class XLSXCreator {
	public boolean exportListToXLSX(ArrayList<Transaction> list) {
		//patt's path
		//    C:\\Users\\2500585\\Desktop\\Eclipse Java - Spring\\bank-casestudy-master-221027-EODD\\src\\main\\webapp\\
		String base = "C:\\Users\\2500857\\Desktop\\Eclipse\\Workspace\\bank-casestudy-master-221028\\" ;
		boolean result = false;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("transactions");
			
			// create headers first
			
			Row row2 = sheet.createRow(0);
			Cell cell2 = row2.createCell(0);
		    cell2.setCellValue("Transaction ID");
		 
		    cell2 = row2.createCell(1);
		    cell2.setCellValue("Transaction Type");
		 
		    cell2 = row2.createCell(2);
		    cell2.setCellValue("Transaction Date");
		    
		    cell2 = row2.createCell(3);
		    cell2.setCellValue("Transaction Amount");
			
			// populate rows
			int rowCount = 0;

			for (Transaction t : list) {
				Row row = sheet.createRow(++rowCount);
				
			    Cell cell = row.createCell(0);
			    cell.setCellValue(t.getTransactionId());
			 
			    cell = row.createCell(1);
			    cell.setCellValue(t.getTransactionType());
			 
			    cell = row.createCell(2);
			    cell.setCellValue(t.getTransactionDate());
			    
			    cell = row.createCell(3);
			    cell.setCellValue(t.getTransactionAmount());
			}
			// export file
			
			
//			Object[][] bookData = { { "Head First Java", "Kathy Serria", 79 },
//					{ "Effective Java", "Joshua Bloch", 136 }, { "Clean Code", "Robert martin", 42 },
//					{ "Thinking in Java", "Bruce Eckel", 35 }, };
//
//
//			for (Object[] aBook : bookData) {
//				Row row = sheet.createRow(++rowCount);
//
//				int columnCount = -1; // set to = 0 for 1 padding
//
//				for (Object field : aBook) {
//					Cell cell = row.createCell(++columnCount);
//					if (field instanceof String) {
//						cell.setCellValue((String) field);
//					} else if (field instanceof Integer) {
//						cell.setCellValue((Integer) field);
//					}
//				}
//
//			}
//
			FileOutputStream outputStream = new FileOutputStream(base+"Transactions.xlsx");
			workbook.write(outputStream);

		} catch (Exception e) {

		}
		return result;
	}
}
