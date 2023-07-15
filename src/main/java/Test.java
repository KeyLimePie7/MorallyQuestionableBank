import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.bean.Transaction;
import com.service.BankService;
import com.util.DBUtil;

public class Test {
	public static void main(String[] args) throws ParseException {
		BankService service = new BankService();
//		service.printAllTransaction(200000000);
//		service.printTransactionCSVNumber(200000000,5);
//		System.out.println("${PROJECT_LOC}");
		//Tester for CSVCreator
//		ArrayList<Transaction> tempLi = new ArrayList<>() ;
//		Transaction tempTx = new Transaction() ;
//		tempTx.setAccountId(1);
//		tempTx.setTransactionAmount(2);
//		 long millis=System.currentTimeMillis();  
//		tempTx.setTransactionDate(new java.sql.Date(millis));
//		tempTx.setTransactionId(1);
//		tempTx.setAccountId(1);
//		tempTx.setTransactionType("Test");
//		tempLi.add(tempTx);
////		service.exportListToCSV(tempLi);
////		service.exportListToXLSX(tempLi);
//		service.printList(tempLi);
		
//		String startDate = "2022-10-27" ;
//		String startDate = "2022-10-30" ;
//		int accountId = 200000000 ;
//		java.util.Date startDateJU=new SimpleDateFormat("yyyy-mm-dd").parse(startDate); 
//	    java.sql.Date startDateTest = new java.sql.Date(startDateJU.getTime());
//	    
//	    ArrayList<Transaction> li = new ArrayList<>() ;
//		try {
//			Connection con = DBUtil.createConnection() ;
//			PreparedStatement sta = con.prepareStatement("SELECT * FROM transactions WHERE ws_acct_id="+accountId+" AND ws_trxn_date<?");
////			sta.setDate(1,startDateTest);
//			sta.setString(1, startDate);
//			//sta.setDate(2, endDate);
//			ResultSet rs = sta.executeQuery();
//			System.out.println("outside loop");
////			Statement sta = con.createStatement() ;
////			ResultSet rs = sta.executeQuery("SELECT * FROM transactions WHERE ws_acct_id="+accountId+" AND ws_trxn_date>=(CAST("+startDate+" AS DATE )) AND ws_trxn_date<=(CAST("+endDate+" AS DATE )) ORDER BY ws_trxn_date DESC") ;
//			while (rs.next()) {
//				System.out.println("i found something");
//				Transaction tx = new Transaction() ;
//				tx.setTransactionId(rs.getInt("ws_trxn_id"));
//				System.out.print("tx id: "+rs.getInt("ws_trxn_id")) ;
//				tx.setAccountId(rs.getInt("ws_acct_id"));
//				System.out.print("acc id: "+rs.getInt("ws_acct_id")) ;
//				tx.setTransactionAmount(rs.getInt("ws_trxn_amount"));
//				System.out.print("tx amt: "+rs.getInt("ws_trxn_amount")) ;
//				tx.setTransactionType(rs.getString("ws_trxn_type"));
//				System.out.print("tx type: "+rs.getString("ws_trxn_type")) ;
//				tx.setTransactionDate(rs.getDate("ws_trxn_date"));
//				System.out.print("tx date: "+rs.getDate("ws_trxn_date")) ;
//				li.add(tx);
//			}
//			rs.close();
//			sta.close();
//			con.close();
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
	}
}
