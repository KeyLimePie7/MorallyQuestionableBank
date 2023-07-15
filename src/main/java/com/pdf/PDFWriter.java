package com.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.bean.Transaction;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFWriter {
	public static void writer(List<Transaction> transactions) {
		Document document = new Document();
		try {
//			PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));
//			PdfWriter.getInstance(document, new FileOutputStream("src/main/webapp/iTextTable.pdf")); //attempted change to path 
			//THE ABOVE WORKS IF WE ARE RUNNING THE IT WITHOUT SERVER (RUN AS JAVA APPLICATION)
			// patts path:
			//  C:\\Users\\2500585\\Desktop\\Eclipse Java - Spring\\bank-casestudy-master-221027-EODD\\src\\main\\webapp\\
			String base_dir="C:\\Users\\2500857\\Desktop\\Eclipse\\Workspace\\bank-casestudy-master-221028\\";
			PdfWriter.getInstance(document, new FileOutputStream(base_dir+"transactions.pdf"));
			//***this works
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		document.open();

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		addTableHeader(table);
		for(Transaction transaction : transactions) {
			addRows(table, transaction);
		}
		

		try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
		System.out.println("done");
	}
	
	private static void addTableHeader(PdfPTable table) {
        Stream.of("Transaction ID", "Description", "Date \n(YYYY-MM-DD)", "Amount")
          .forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            header.setHorizontalAlignment(header.ALIGN_CENTER);
            header.setVerticalAlignment(header.ALIGN_MIDDLE);
            table.addCell(header);
        });
    }
	    
	    
	private static void addRows(PdfPTable table, Transaction transaction) {
		System.out.println("pdf printer activated and rows are being added");
        PdfPCell cell = new PdfPCell(new Phrase("" + transaction.getTransactionId()));
        cell.setHorizontalAlignment(cell.ALIGN_CENTER);
        cell.setVerticalAlignment(cell.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(transaction.getTransactionType()));
        cell.setHorizontalAlignment(cell.ALIGN_CENTER);
        cell.setVerticalAlignment(cell.ALIGN_MIDDLE);
        table.addCell(cell);
        Date date = transaction.getTransactionDate();
        String dateString = ("" + (date.getYear()+1900) + "-" + (date.getMonth()+1) + "-" + date.getDate());
        cell = new PdfPCell(new Phrase(dateString));
        cell.setHorizontalAlignment(cell.ALIGN_CENTER);
        cell.setVerticalAlignment(cell.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("" + transaction.getTransactionAmount()));
        cell.setHorizontalAlignment(cell.ALIGN_CENTER);
        cell.setVerticalAlignment(cell.ALIGN_MIDDLE);
        table.addCell(cell);
    }

}
