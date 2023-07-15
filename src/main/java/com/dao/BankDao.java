package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bean.Account;
import com.bean.Customer;
import com.bean.Transaction;
import com.util.DBUtil;

public class BankDao {

	// Patt 27-10
	public int getCustomerAccs(int custId) {
		int result = 0;
		// returns 0/1/2/3; if customer has 0/savings ONLY/current ONLY/both accs
		try {
			Connection cn = DBUtil.createConnection();
			PreparedStatement ps = cn.prepareStatement("SELECT * FROM account WHERE ws_cust_id=? ");
			ps.setInt(1,custId); 
			ResultSet rs = ps.executeQuery();
			
			// if it enters this while loop, at least one account exists
			while (rs.next()) {
				String s = rs.getString("ws_acct_type");
				if (s.equals("Savings") && !rs.next()) { // if input is savings & no other accs avail
					result = 1;
					break;
				}
				if (s.equals("Current") && !rs.next()) {
					result = 2;
					break;
				}
				result = 3; // reach here if customer has both accounts
			}
			DBUtil.closeAllConnection(rs, ps, cn);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}
	
	// Byron 221021
	//Are login details correct? returns boolean true = correct
	public boolean loginFn(String id, String pw1) {
		boolean boo = false ;
		try {
			Connection con = DBUtil.createConnection() ;
			Statement sta = con.createStatement() ;
			ResultSet rs = sta.executeQuery("SELECT * FROM loginTable WHERE uid='"+id+"'") ;
			String pw = null ;
			if (rs.next()) {
				pw = rs.getString("pw") ;
			}
			if(pw != null) {
				if (pw.equals(pw1)) {
					boo = true ;
				}
			}
			rs.close();
			sta.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage()) ;
		}
		return boo ;
	}
	
	// Wei Hong 221021
	public List<Customer> getCustomerList() {
		List<Customer> custList=new ArrayList<Customer>();
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM customer");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Customer c=new Customer(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5));
				custList.add(c);
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return custList;
	}
	
	// Wei Hong 221021
	public Customer searchCustomer(int custId) {
		Customer cust=null;
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM customer WHERE ws_cust_id=?");
			ps.setInt(1, custId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				cust=new Customer(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5));
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return cust;
	}
	
	// Bryan 221025
	public Customer searchCustomerBySsn(int ssn) {
		Customer cust=null;
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM customer WHERE ws_ssn=?");
			ps.setInt(1, ssn);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				cust=new Customer(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5));
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return cust;
	}
	
	// Oleta 221025
	public Account searchAccountByAccountId(int acctId) {
		Account account = null;
		try {
			Connection conn = DBUtil.createConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM account WHERE ws_acct_id = ?");
			ps.setInt(1, acctId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				account = new Account();
				account.setAccountId(rs.getInt(1));
				account.setCustomerId(rs.getInt(2));
				account.setAccountType(rs.getString(3));
				account.setBalance(rs.getInt(4));
				account.setCreationDate(rs.getDate(5));
				account.setLastTransactionDate(rs.getDate(6));
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return account;
	}
	
	// Oleta 221025
	public List<Account> searchAccountByCustomerId(int custId) {
		List<Account> accounts = new ArrayList<Account>();
		try {
			Connection conn = DBUtil.createConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM account WHERE ws_cust_id = ?");
			ps.setInt(1, custId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt(1));
				account.setCustomerId(rs.getInt(2));
				account.setAccountType(rs.getString(3));
				account.setBalance(rs.getInt(4));
				account.setCreationDate(rs.getDate(5));
				account.setLastTransactionDate(rs.getDate(6));
				accounts.add(account);
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return accounts;
	}
	
	// Byron 221025, Patt 221026
	//exec update cust : namee, addd, ageee are the new values leave strings as blank and ageee as 0 if no change
	public boolean updateCustomer(int custId, String name, String addr, String age) {
		// declarations
		boolean result = false;
		int returnAge;
		try {
			Connection cn = DBUtil.createConnection();
			// retrieve existing customer information from DB
			PreparedStatement ps = cn.prepareStatement("SELECT * FROM customer WHERE ws_cust_id=?");
			ps.setInt(1, custId);
			ResultSet rs = ps.executeQuery();
			// store existing customer information into variables db_
			if (rs.next()) {
				// retrieve values from DB
				String dbName = rs.getString("name");
				String dbAddr = rs.getString("ws_adrs");
				int dbAge = rs.getInt("age");

				// check method input for null entries & update values accordingly
				// empty value implies user wants name to remain unchanged
				if (name == null || name.equals("")) {
					name = dbName; // name DNE, give old name back
				}
				if (addr == null || addr.equals("")) {
					addr = dbAddr; // address DNE, give old address back
				}
				if (age == null || age.equals("")) {
					returnAge = dbAge; // age DNE, give old age back
				} else {
					returnAge = Integer.parseInt(age); // age EXISTS, parse string to integer value
				}
				// execute update statement with new values
				ps = cn.prepareStatement("UPDATE customer SET name=?, ws_adrs=?, age=? WHERE ws_cust_id=?");
				ps.setString(1, name);
				ps.setString(2, addr);
				ps.setInt(3, returnAge);
				ps.setInt(4, custId);
				int t = ps.executeUpdate();
				if (t > 0) {
					// success
					result = true;
				} else {
					// failure
				}
			}
			DBUtil.closeAllConnection(rs, ps, cn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	// Byron 221025
	//Teller account withdraw: takes the amount to withdraw in amt, 
	//returns 0 if retrieval error, returns 1 if insufficient funds, returns 2 if success 
	public int accountWithdraw(int accId, int amt) {
		int res = 0 ;
		try {
			//driver + connection
			Connection con = DBUtil.createConnection() ;
			//create statement
			Statement sta = con.createStatement();
			//execute statement
			ResultSet rs = sta.executeQuery("SELECT ws_acct_balance FROM account WHERE ws_acct_id="+accId);
			//result set?
			int balance = -50 ;
			if (rs.next()) {
				balance = rs.getInt("ws_acct_balance");
			}
			if (balance == -50) {
				//account retrieval failed
				res = 0 ;
			} else if (balance < amt) {
				//insufficient funds
				res = 1 ;
			} else {
				//sufficient funds hence code execution
				res = 2 ;
				int remBal = balance - amt ;
				sta.execute("UPDATE account SET ws_acct_balance="+remBal+" WHERE ws_acct_id="+accId);
				//update last transaction date in account table:
				LocalDateTime localDateTime = LocalDateTime.now();
		        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
		        long date = zdt.toInstant().toEpochMilli();
				java.sql.Date da = new Date(date) ;
				PreparedStatement ps1 = con.prepareStatement("UPDATE account SET ws_acct_lasttrdate=? WHERE ws_acct_id="+accId);
				ps1.setDate(1, da);
				ps1.execute();
				ps1.close();
				
			}
			if (balance >= amt) { //if successful, create a new log entry in transactions table
				PreparedStatement sta2 = con.prepareStatement("INSERT INTO transactions (ws_acct_id,ws_trxn_amount,ws_trxn_type,ws_trxn_date) VALUES(?,?,?,?)");
				sta2.setInt(1,accId);
				sta2.setInt(2,amt);
				sta2.setString(3, "withdrawal");
				LocalDateTime localDateTime = LocalDateTime.now();
		        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
		        long date = zdt.toInstant().toEpochMilli();
				Date da = new Date(date) ; //SQL version of date, not java util
				sta2.setDate(4, da);
				sta2.execute();
				sta2.close();
			}
			//close
			rs.close();
			sta.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return res ;
	}
	
	// Byron 221025
	//Transfer Money
	//source or target is either "Current" or "Savings" string
	//returns 0 if unknown error
	//returns 1 if insufficient accounts (you need 2 accounts) 
	//returns 2 if insufficient funds
	//returns 3 if successful transfer
	public int transferMoney(int custId, String source, int amount) {
		int result = 0 ;
		String target = null ;
		if ("Current".equals(source)) {
			target = "Savings";
		} else if ("Savings".equals(source)) {
			target="Current" ;
		}
		try {
			Connection con = DBUtil.createConnection() ;
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery("SELECT * FROM account WHERE ws_cust_id="+custId);
			int count = 0 ;
			result = 1 ;
			while (rs.next()) {
				count++ ;
			}
			if (count == 2) { //sufficient number of accounts (2)
				rs=sta.executeQuery("SELECT * FROM account WHERE ws_cust_id="+custId+" AND ws_acct_type='"+source+"'");
				int sourceBalance = -1 ;
				int sourceAccId = -1 ;
				if (rs.next()) {
					sourceBalance = rs.getInt("ws_acct_balance");
					sourceAccId = rs.getInt("ws_acct_id");
				}
				result = 2 ;
				if (sourceBalance >= amount) { //sufficient balance. execute transfer
					//deduct money from source
					int sourceRemaining = sourceBalance - amount ;
					sta.execute("UPDATE account SET ws_acct_balance="+sourceRemaining+" WHERE ws_cust_id="+custId+" AND ws_acct_type='"+source+"'");
					//add money to target
					int targetOriginalBalance = -1 ;
					rs=sta.executeQuery("SELECT * FROM account WHERE ws_cust_id="+custId+" AND ws_acct_type='"+target+"'");
					int targetAccId = -1 ;
					if (rs.next()) {
						targetOriginalBalance = rs.getInt("ws_acct_balance");
						targetAccId = rs.getInt("ws_acct_id");
					}
					int targetRemaining = targetOriginalBalance + amount ;
					sta.execute("UPDATE account SET ws_acct_balance="+targetRemaining+" WHERE ws_cust_id="+custId+" AND ws_acct_type='"+target+"'");
					result = 3 ;
					
					//update last transaction date in SOURCE account table:
					LocalDateTime localDateTime = LocalDateTime.now();
			        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
			        long date = zdt.toInstant().toEpochMilli();
					java.sql.Date da = new Date(date) ;
					PreparedStatement ps1 = con.prepareStatement("UPDATE account SET ws_acct_lasttrdate=? WHERE ws_acct_id="+sourceAccId);
					ps1.setDate(1, da);
					ps1.execute();
					ps1.close();
					
					//update last transaction date in TARGET account table:
					LocalDateTime localDateTime3 = LocalDateTime.now();
			        ZonedDateTime zdt3 = ZonedDateTime.of(localDateTime3, ZoneId.systemDefault());
			        long date3 = zdt3.toInstant().toEpochMilli();
					java.sql.Date da3 = new Date(date3) ;
					PreparedStatement ps3 = con.prepareStatement("UPDATE account SET ws_acct_lasttrdate=? WHERE ws_acct_id="+targetAccId);
					ps3.setDate(1, da3);
					ps3.execute();
					ps3.close();
					
					//updating transaction logs
					//update transaction table for TARGET account
					PreparedStatement sta2 = con.prepareStatement("INSERT INTO transactions (ws_acct_id,ws_trxn_amount,ws_trxn_type,ws_trxn_date) VALUES(?,?,?,?)");
					sta2.setInt(1,targetAccId);
					sta2.setInt(2,amount);
					sta2.setString(3, "inbound transfer");
					LocalDateTime localDateTime2 = LocalDateTime.now();
			        ZonedDateTime zdt2 = ZonedDateTime.of(localDateTime2, ZoneId.systemDefault());
			        long date2 = zdt2.toInstant().toEpochMilli();
					Date da2 = new Date(date2) ; //SQL version of date, not java util
					sta2.setDate(4, da2);
					sta2.execute();
					//update transaction table for SOURCE account
					sta2.setInt(1,sourceAccId);
					sta2.setInt(2,amount);
					sta2.setString(3, "outbound transfer");
					sta2.execute();
					
					sta2.close();
				}
			}
			rs.close();
			sta.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage()) ;
		}
		return result ;
	}
	
	// Patt 221025
	public boolean createCustomer(Customer customer) {
		boolean result = false;
		boolean ssnExists = false;
		try {
			Connection cn = DBUtil.createConnection();
			
			// check for ssn exist
			PreparedStatement ps = cn.prepareStatement("SELECT * FROM customer WHERE ws_ssn=? ");
			ps.setInt(1, customer.getSsn());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ssnExists = true;
				break;
			}
			// only attempt insertion if the SSN does not already exist
			if (!ssnExists) {
				ps = cn.prepareStatement("INSERT INTO customer(ws_ssn, name, ws_adrs, age) VALUES(?,?,?,?)");
				ps.setInt(1, customer.getSsn());
				ps.setString(2, customer.getName());
				ps.setString(3, customer.getAddress());
				ps.setInt(4, customer.getAge());
				int t = ps.executeUpdate();
				if (t > 0) {
					// successful insertion
					result = true;
				}
			}
			DBUtil.closeAllConnection(rs, ps, cn);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}

	// Patt 221025
	public boolean deleteCustomer(int ws_cust_id) {
		boolean result = false;
		try {
			Connection cn = DBUtil.createConnection();
			PreparedStatement ps = cn.prepareStatement("DELETE FROM customer WHERE ws_cust_id=?");
			ps.setInt(1, ws_cust_id);
			int t = ps.executeUpdate();
			if (t > 0) {
				// successful deletion
				result = true;
			}
			DBUtil.closeAllConnection(null, ps, cn);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return result;
	}
	
	// Wan Lin 251022
	public Customer findCustomer(int customerId) {
		Customer cust=null;
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM customer WHERE ws_cust_id=?");
			ps.setInt(1, customerId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				cust=new Customer(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5));
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return cust;
	}
		
	// Wan Lin 221025 Wan Lin 221026
	public Account tellerSearchByAccId(int accountId)	{
		Account acc=null;
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM account WHERE ws_acct_id=?");
			ps.setInt(1, accountId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				acc=new Account(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getDate(6));
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return acc;
	}
	
	// Wei Hong 221025
	public List<Account> getAccountByList() {
		List<Account> accList=new ArrayList<Account>();
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM account");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Account a=new Account(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getDate(6));
				accList.add(a);
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return accList;
	}
	
	// Wei Hong 221025
	public List<Account> getAccountByCustomerId(int customerId) {
		List<Account> accList=new ArrayList<Account>();
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM account WHERE ws_cust_id=?");
			ps.setInt(1,customerId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Account a=new Account(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getDate(6));
				accList.add(a);
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return accList;
	}
	// Wei Hong 221025
	public boolean deleteAccount(int accountId) {
		boolean result=false;
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("DELETE FROM account WHERE ws_acct_id=?");
			ps.setInt(1, accountId);
			int t=ps.executeUpdate();
			if(t>0) {
				result=true;
			}
			DBUtil.closeAllConnection(null, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	// Wei Hong 221025
	public boolean createAccount(Account ac) {
		boolean result= false;
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("INSERT INTO account (ws_cust_id,ws_acct_type,ws_acct_balance,ws_acct_crdate,ws_acct_lasttrdate) VALUES (?,?,?,?,?)");
			ps.setInt(1, ac.getCustomerId());
			ps.setString(2, ac.getAccountType());
			ps.setInt(3, ac.getBalance());
			ps.setDate(4, ac.getCreationDate());
			ps.setDate(5, ac.getLastTransactionDate());
			int t=ps.executeUpdate();
			if(t>0) {
				result=true;
			}
			DBUtil.closeAllConnection(null, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	// Oleta 221026
	public boolean createTransaction(Transaction transaction) {
		boolean result = false;
		try {
			Connection conn = DBUtil.createConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO transactions (ws_acct_id, ws_trxn_amount, ws_trxn_type, ws_trxn_date) VALUES (?, ?, ?, ?)");
			ps.setInt(1, transaction.getAccountId());
			ps.setInt(2, transaction.getTransactionAmount());
			ps.setString(3, transaction.getTransactionType());
			ps.setDate(4, transaction.getTransactionDate());
			
			int resultInt = ps.executeUpdate();
			if(resultInt > 0) {
				ps = conn.prepareStatement("SELECT * FROM account WHERE ws_acct_id = ?");
				ps.setInt(1, transaction.getAccountId());
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					int balance = rs.getInt("ws_acct_balance");
					balance += transaction.getTransactionAmount();
					ps = conn.prepareStatement("UPDATE account SET ws_acct_balance = ? WHERE ws_acct_id = ?");
					ps.setInt(1, balance);
					ps.setInt(2, transaction.getAccountId());
					resultInt = ps.executeUpdate();
					if(resultInt > 0) {
						result = true;
					}
				}
				 if (result == true ) {
					//update last transaction date in account table only if transaction insertion was successful
					LocalDateTime localDateTime = LocalDateTime.now();
			        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
			        long date = zdt.toInstant().toEpochMilli();
					java.sql.Date da = new Date(date) ;
					PreparedStatement ps1 = conn.prepareStatement("UPDATE account SET ws_acct_lasttrdate=? WHERE ws_acct_id="+transaction.getAccountId());
					ps1.setDate(1, da);
					ps1.execute();
					ps1.close();
				 }
				rs.close();
			}
			DBUtil.closeAllConnection(null, ps, conn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	// Bryan 221026
	public ArrayList<Transaction> getAllTx(int accountId) {
		ArrayList<Transaction> li = new ArrayList<>() ;
		try {
			Connection con = DBUtil.createConnection() ;
			Statement sta = con.createStatement() ;
			ResultSet rs = null ;
			rs = sta.executeQuery("SELECT * FROM transactions WHERE ws_acct_id="+accountId+" ORDER BY ws_trxn_date DESC");
			while (rs.next()) {
				Transaction tx = new Transaction() ;
				tx.setTransactionId(rs.getInt("ws_trxn_id"));
				tx.setAccountId(rs.getInt("ws_acct_id"));
				tx.setTransactionAmount(rs.getInt("ws_trxn_amount"));
				tx.setTransactionType(rs.getString("ws_trxn_type"));
				tx.setTransactionDate(rs.getDate("ws_trxn_date"));
				li.add(tx);
			}
			rs.close();
			sta.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return li ;
	}
	
	// Byron 221026
	//retrieve last N transactions
	public ArrayList<Transaction> getNTx(int accountId, int num) {
		ArrayList<Transaction> li = new ArrayList<>() ;
		try {
			Connection con = DBUtil.createConnection() ;
			Statement sta = con.createStatement() ;
			ResultSet rs = null ;
			if (num == 1) {
				rs = sta.executeQuery("SELECT * FROM transactions WHERE ws_acct_id="+accountId+" ORDER BY ws_trxn_date DESC FETCH FIRST ROW ONLY"); //should be derby compatible
			} else { //else if num > 1
				rs = sta.executeQuery("SELECT * FROM transactions WHERE ws_acct_id="+accountId+" ORDER BY ws_trxn_date DESC FETCH NEXT "+num+" ROWS ONLY"); //should be derby compatible
			}
			while (rs.next()) {
				Transaction tx = new Transaction() ;
				tx.setTransactionId(rs.getInt("ws_trxn_id"));
				tx.setAccountId(rs.getInt("ws_acct_id"));
				tx.setTransactionAmount(rs.getInt("ws_trxn_amount"));
				tx.setTransactionType(rs.getString("ws_trxn_type"));
				tx.setTransactionDate(rs.getDate("ws_trxn_date"));
				li.add(tx);
			}
			rs.close();
			sta.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return li ;
	}
	
	// Byron 221026
	//retrieve transactions based on date
	public ArrayList<Transaction> getTxWithinDate(int accountId, String startDate, String endDate) {
		ArrayList<Transaction> li = new ArrayList<>() ;
		try {
			Connection con = DBUtil.createConnection() ;
			PreparedStatement sta = con.prepareStatement("SELECT * FROM transactions WHERE ws_acct_id="+accountId+" AND ws_trxn_date>=? AND ws_trxn_date<=? ORDER BY ws_trxn_date DESC");
			sta.setString(1,startDate);
			sta.setString(2, endDate);
			ResultSet rs = sta.executeQuery();
//			Statement sta = con.createStatement() ;
//			ResultSet rs = sta.executeQuery("SELECT * FROM transactions WHERE ws_acct_id="+accountId+" AND ws_trxn_date>=(CAST("+startDate+" AS DATE )) AND ws_trxn_date<=(CAST("+endDate+" AS DATE )) ORDER BY ws_trxn_date DESC") ;
			while (rs.next()) {
				Transaction tx = new Transaction() ;
				tx.setTransactionId(rs.getInt("ws_trxn_id"));
				tx.setAccountId(rs.getInt("ws_acct_id"));
				tx.setTransactionAmount(rs.getInt("ws_trxn_amount"));
				tx.setTransactionType(rs.getString("ws_trxn_type"));
				tx.setTransactionDate(rs.getDate("ws_trxn_date"));
				li.add(tx);
			}
			rs.close();
			sta.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return li ;
	}
	
	// Patt 221026
	public List<Boolean> getDeletionListBySSN(int ssn) {
		List<Boolean> resultList = new ArrayList<Boolean>();
		try {
			Connection conn = DBUtil.createConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT ws_cust_id FROM customer WHERE ws_ssn=?");
			ps.setInt(1, ssn);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (deleteEligibility(rs.getInt(1))) {
					resultList.add(true);
				} else {
					resultList.add(false);
				}
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return resultList;
	}

	// Patt 221026
	public List<Boolean> getDeletionListByCID(int custId) {
		List<Boolean> resultList = new ArrayList<Boolean>();
		try {
			Connection conn = DBUtil.createConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT ws_cust_id FROM customer WHERE ws_cust_id=?");
			ps.setInt(1, custId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (deleteEligibility(rs.getInt(1))) {
					resultList.add(true);
				} else {
					resultList.add(false);
				}
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return resultList;
	}

	// Patt 221026
	public List<Boolean> getDeletionList() {
		List<Boolean> resultList = new ArrayList<Boolean>();
		try {
			Connection conn = DBUtil.createConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT ws_cust_id FROM customer");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (deleteEligibility(rs.getInt(1))) {
					resultList.add(true);
				} else {
					resultList.add(false);
				}
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return resultList;
	}

	// Patt 221026
	public static boolean deleteEligibility(int custId) {
		// method is created to be used in getDeletionList()
		// method checks a cust_id, if it can be deleted, returns boolean
		boolean result = true;
		try {
			Connection conn = DBUtil.createConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM account WHERE ws_cust_id=?");
			ps.setInt(1, custId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = false;
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	// Wei Hong 221026
	public List<Account> getAccountList() {
		List<Account> accList=new ArrayList<Account>();
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM account");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Account a=new Account(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getDate(6));
				accList.add(a);
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return accList;
	}
	
	// Wan Lin 221026
	public List<Account> getAccountDetails(int customerId) {
		List<Account> accList=new ArrayList<Account>();
		try {
			Connection conn=DBUtil.createConnection();
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM account WHERE ws_cust_id=?");
			ps.setInt(1, customerId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				Account a=new Account(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getDate(6));
				accList.add(a);
			}
			DBUtil.closeAllConnection(rs, ps, conn);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return accList;
	}
}
