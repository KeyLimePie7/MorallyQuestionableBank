package com.csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bean.Account;
import com.bean.Transaction;
import com.util.DBUtil;

public class CSVCreator {
	public boolean exportListToCSV(ArrayList<Transaction> li) {
		boolean result = false ;
		try {
            // specify path
			String base_dir="C:\\Users\\2500857\\Desktop\\Eclipse\\Workspace\\bank-casestudy-master-221028\\";
            String csvFilePath = base_dir+"transactions.csv";
            
            // write header line containing column names
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
//            fileWriter.write("Customer_ID,SSN,Name,Address,Age");
            fileWriter.write("Transaction ID,Description,Date,Amount");
            
            // extract rows & add into csv
            for (int i = 0 ; i < li.size(); i++) {
//                int custId = rs.getInt("ws_cust_id");
            	int txId = li.get(i).getTransactionId();
//                int ssn = rs.getInt("ws_ssn");
            	String des = li.get(i).getTransactionType();
//                String name = rs.getString("name");
            	String date = li.get(i).getTransactionDate().toString() ;
//                String addr = rs.getString("ws_adrs");
            	int amt = li.get(i).getTransactionAmount() ;
//                int age = rs.getInt("age");
                String line = String.format("%d,%s,%s,%d", txId,des,date,amt);
                fileWriter.newLine();
                fileWriter.write(line);
            }
            
            fileWriter.close();
            result = true ;
        } catch (IOException ex) {
            result = false;
            System.out.println(ex.getMessage());
        }
		return result ;
	}
	
	//deprecated - ignore this
	public boolean exportToCsv() {
        boolean result = true;
//        try {
//            Connection cn = DBUtil.createConnection();
//            PreparedStatement ps = cn.prepareStatement("SELECT * FROM customer");
//            ResultSet rs = ps.executeQuery();
//            
//            // specify path
//            String base = "C:\\Users\\2500585\\Desktop\\Eclipse Java - Spring\\bank-casestudy-master-221027-EODD\\src\\main\\webapp\\";
//            String csvFilePath = base+"Test-export.csv";
//            
//            // write header line containing column names
//            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
//            fileWriter.write("Customer_ID,SSN,Name,Address,Age");
//            
//            // extract rows & add into csv
//            while (rs.next()) {
//                int custId = rs.getInt("ws_cust_id");
//                int ssn = rs.getInt("ws_ssn");
//                String name = rs.getString("name");
//                String addr = rs.getString("ws_adrs");
//                int age = rs.getInt("age");
//                String line = String.format("%d,%d,%s,%s,%d", custId,ssn,name,addr,age);
//                fileWriter.newLine();
//                fileWriter.write(line);
//            }
//            
//            fileWriter.close();
//            DBUtil.closeAllConnection(rs, ps, cn);
//        } catch (SQLException ex) {
//            result = false;
//            System.out.println(ex.getMessage());
//        } catch (IOException ex) {
//            result = false;
//            System.out.println(ex.getMessage());
//        }
        return result;
    }
}
