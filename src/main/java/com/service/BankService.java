package com.service;

import java.util.ArrayList;
import java.util.List;

import com.bean.Account;
import com.bean.Customer;
import com.bean.Transaction;
import com.csv.CSVCreator;
import com.dao.BankDao;
import com.pdf.PDFWriter;
import com.xlsx.XLSXCreator;

public class BankService {
	
	// Patt 27-10
	public int getCustomerAccs(int custId) {
		BankDao dao = new BankDao();
		return dao.getCustomerAccs(custId);
	}

	
	//Byron 221027
	public boolean exportListToCSV(ArrayList<Transaction> li) {
		CSVCreator cC = new CSVCreator() ;
		return cC.exportListToCSV(li);
	}
	
	// Patt 28-10
	public boolean exportListToXLSX(ArrayList<Transaction> list) {
		XLSXCreator x = new XLSXCreator();
		return x.exportListToXLSX(list);
	}
	
	// Byron 221021
	//Are login details correct? returns boolean true = correct
	public boolean loginFn(String id, String pw1) {
		BankDao bD = new BankDao() ;
		return bD.loginFn(id, pw1);
	}
	
	// Wei Hong 221021
	public List<Customer> getCustomerList() {
		BankDao dao = new BankDao();
		return dao.getCustomerList();
	}
	
	// Wei Hong 221021
	public Customer searchCustomer(int ws_cust_id) {
		BankDao dao = new BankDao();
		return dao.searchCustomer(ws_cust_id);
	}
	
	// Bryan 221025
	public Customer searchCustomerBySsn(int ssn) {
		BankDao dao = new BankDao();
		return dao.searchCustomerBySsn(ssn);
	}
	
	// Oleta 221025
	public Account searchAccountByAccountId(int acctId) {
		BankDao dao = new BankDao();
		return dao.searchAccountByAccountId(acctId);
	}
	
	// Oleta 221025
	public List<Account> searchAccountByCustomerId(int custId) {
		BankDao dao = new BankDao();
		return dao.searchAccountByCustomerId(custId);
	}
	
	// Byron 221025
	//exec update cust : namee, addd, ageee are the new values leave strings as blank and ageee as 0 if no change
	public boolean updateCustomer(int custId,String namee, String addd, String ageee) {
		BankDao bD = new BankDao() ;
		return bD.updateCustomer(custId, namee, addd, ageee);
	}
	
	// Byron 221025
	//Teller account withdraw: takes the amount to withdraw in amt, 
	//returns 0 if retrieval error, returns 1 if insufficient funds, returns 2 if success 
	public int accountWithdraw(int accId, int amt) {
		BankDao bD = new BankDao() ;
		return bD.accountWithdraw(accId, amt);
	}
	
	// Byron 221025
	//Transfer Money
	//source or target is either "Current" or "Savings" string
	//returns 0 if unknown error
	//returns 1 if insufficient accounts (you need 2 accounts) 
	//returns 2 if insufficient funds
	//returns 3 if successful transfer
	public int transferMoney(int custId, String source, int amount) {
		BankDao bD = new BankDao() ;
		return bD.transferMoney(custId, source, amount);
	}
	
	// Patt 221025
	public boolean createCustomer(Customer customer) {
		BankDao dao = new BankDao();
		return dao.createCustomer(customer);
	}

	// Patt 221025
	public boolean deleteCustomer(int ws_cust_id) {
		BankDao dao = new BankDao();
		return dao.deleteCustomer(ws_cust_id);
	}
	
	// Wan Lin 221025
	public Customer findCustomer(int ws_cust_id) {
		BankDao dao= new BankDao();
		return dao.findCustomer(ws_cust_id);
	}
	
	// Wan Lin 221025 Wan Lin 221025
	public Account tellerSearchByAccId(int accountId) {
		BankDao dao= new BankDao();
		return dao.tellerSearchByAccId(accountId);
	}
	
	// Wei Hong 221025
	public List<Account> getAccountByCustomerId(int ws_cust_id) {
		BankDao dao = new BankDao();
		return dao.getAccountByCustomerId(ws_cust_id);
	}
	// Wei Hong 221025
	public boolean deleteAccount(int accountId) {
		BankDao dao = new BankDao();
		return dao.deleteAccount(accountId);	
	}
	// Wei Hong 221025
	public boolean createAccount(Account ac) {
		BankDao dao = new BankDao();
		return dao.createAccount(ac);	
	}
	
	// Oleta 221026
	public boolean createTransaction(Transaction transaction) {
		BankDao dao = new BankDao();
		return dao.createTransaction(transaction);
	}
	
	//Byron 221027
	public void printList(List<Transaction> li) {
		PDFWriter.writer(li);
	}
	
	// Bryan 221026
	public void printAllTransaction(int acctId) {
		List<Transaction> transactions = getAllTx(acctId);
		PDFWriter.writer(transactions);
	}
	
	// Bryan 221026
	public void printTransactionCSVNumber(int acctId, int count) {
		List<Transaction> transactions = getNTx(acctId, count);
		PDFWriter.writer(transactions);
	}
	
	// Bryan 221026
	public void printTransactionCSVDate(int acctId, String startDate, String endDate) {
		List<Transaction> transactions = getTxWithinDate(acctId, startDate, endDate);
		PDFWriter.writer(transactions);
	}
	
	public ArrayList<Transaction> getAllTx(int accountId) {
		BankDao dao = new BankDao() ;
		return dao.getAllTx(accountId);
	}
	
	// Byron 221026
	//retrieve transactions based on date
	public ArrayList<Transaction> getTxWithinDate(int accountId, String startDate, String endDate) {
		BankDao bD = new BankDao() ;
		return bD.getTxWithinDate(accountId, startDate, endDate);
	}

	// Byron 221026
	//retrieve last N transactions
	public ArrayList<Transaction> getNTx(int accountId, int num) {
		BankDao bD = new BankDao() ;
		return bD.getNTx(accountId, num);
	}
	
	// Patt 221026
	public List<Boolean> getDeletionListBySSN(int ssn) {
		BankDao dao = new BankDao();
		return dao.getDeletionListBySSN(ssn);
	}
	// Patt 221026
	public List<Boolean> getDeletionListByCID(int custId) {
		BankDao dao = new BankDao();
		return dao.getDeletionListByCID(custId);
	}

	// Patt 221026
	public List<Boolean> getDeletionList() {
		BankDao dao = new BankDao();
		return dao.getDeletionList();
	}
	
	// Wei Hong 221026
	public List<Account> getAccountList() {
		BankDao dao = new BankDao();
		return dao.getAccountList();
	}
	
	// Wan Lin 221026
	public List<Account> getAccountDetails(int customerId)
	{
		BankDao dao= new BankDao();
		return dao.getAccountDetails(customerId);
	}
}
