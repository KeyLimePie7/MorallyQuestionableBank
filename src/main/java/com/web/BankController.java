package com.web;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bean.Account;
import com.bean.Customer;
import com.bean.Transaction;
import com.service.BankService;
import com.util.DBUtil;

@Controller
public class BankController {
	
	//  PATT 27-10
    @RequestMapping("/exec-splash")
    public ModelAndView execSplash() {
        ModelAndView view = new ModelAndView("ExecSplash");
        return view;
    }
	
	// Byron 221021
	//for login landing page
	@RequestMapping("/loginFunc")
	public ModelAndView login(@RequestParam("uid1") String uid11, @RequestParam("pw1") String pw11) {
		ModelAndView view = null ;
		BankService bS = new BankService() ;
		boolean boo = bS.loginFn(uid11, pw11);
		if (boo == false) { //login failed
			view = new ModelAndView("index") ; //navigate to login failed page
			boolean boo2 = false ;
			view.addObject("boo2",boo2);
		}
		else if (boo = true ) { //login success
			if (uid11.equals("teller")) {
				view = new ModelAndView("TellerSearchByCusId"); //navigate to teller splash page
			}
			else if (uid11.equals("exec")) {
				view = new ModelAndView("ExecSplash"); //navigate to exec splash page
			}
		}
		return view ;
	}
	
	// Wei Hong 221021 Patt 221026
	@RequestMapping("/list")
	public ModelAndView listCustomer() {
		ModelAndView view = new ModelAndView("exec_list_cust");
		BankService service = new BankService();
		List<Customer> custList = service.getCustomerList();
		List<Boolean> boolList = service.getDeletionList();
		view.addObject("custList", custList);
		view.addObject("boolList", boolList);
		return view;
	}
	
	// Wei Hong 221021 Patt 221026
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchEmployee(@RequestParam("custId") String custId, @RequestParam("ssnId") String ssnId,
			ModelMap model) {
		ModelAndView view = new ModelAndView("exec_search_result");
		BankService service = new BankService();
		Customer cust = null;
		if (!custId.equals("")) {
			cust = service.searchCustomer(Integer.parseInt(custId));
			List<Boolean> boolList = service.getDeletionListByCID(Integer.parseInt(custId));
			view.addObject("boolList", boolList);
		} else if (!ssnId.equals("")) {
			cust = service.searchCustomerBySsn(Integer.parseInt(ssnId));
			List<Boolean> boolList = service.getDeletionListBySSN(Integer.parseInt(ssnId));
			view.addObject("boolList", boolList);
		}
		view.addObject("customer", cust);
		return view;
	}
	
	// Wei Hong 221021
	@RequestMapping("/searchLoad")
	public ModelAndView loadSearchPage() {
		ModelAndView view=new ModelAndView("exec_search_cust");
		return view;
	}
	
	// Bryan 221025
	@RequestMapping("/home")
	public ModelAndView navHome () {
		ModelAndView view=new ModelAndView("index");
		return view;
	}
	
	// Oleta 221025
	// PLEASE DONT USE THIS IS RWRONGWORNWRONGRWRONGWORNWRONGRWRONGWORNWRONGRWRONGWORNWRONGRWRONGWORNWRONG
	@RequestMapping("/teller-home")
	public ModelAndView tellerHome() {
		ModelAndView view = new ModelAndView("TellerSearchByCusId");
		return view;
	}
	
	// Oleta 221025
	@RequestMapping("/teller-search-main")
	public ModelAndView tellerSearchMain(@RequestParam("custId") String custId, @RequestParam("acctId") String acctId) {
		ModelAndView view = new ModelAndView();
		BankService service = new BankService();
		if(acctId != "") {
			Account account = service.searchAccountByAccountId(Integer.parseInt(acctId));
			view = new ModelAndView("teller-acct-page");
			view.addObject("account", account);
		} else if (custId != "") {
			Customer customer = service.searchCustomer(Integer.parseInt(custId));
			List<Account> accountList = service.searchAccountByCustomerId(Integer.parseInt(custId));
			view = new ModelAndView("teller-cust-page");
			view.addObject("customer", customer);
			view.addObject("accountList", accountList);
		} else {
			view = new ModelAndView("teller-home");
			view.addObject("emptyInput", true);
		}
		return view;
	}
	
	// Byron 221025 Patt 221026
	//exec update cust : namee, addd, ageee are the new values leave strings as blank and ageee as 0 if no change
	@RequestMapping("/updateCustomer") //the JSP has not called this function YET!!!#####TAKE NOTE
	public ModelAndView updateCustomer(@RequestParam("customerId") int custID) { // YOU MUST PASS IT THE CUSTOMER ID
		ModelAndView view = new ModelAndView("updateCustomerPre");
		BankService bS = new BankService();
		Customer cust = bS.searchCustomer(custID);
		view.addObject("customer", cust);
		return view;
	}
	
	// Byron 221025 Patt 221026
	//exec update cust : namee, addd, ageee are the new values leave strings as blank and ageee as 0 if no change
	@RequestMapping("/updateCustomer2")
	public ModelAndView updateCustomer2(@RequestParam("customId") int custID, @RequestParam("newName") String newName,
			@RequestParam("newAddress") String newAddress, @RequestParam("newAge") String newAge) {
		ModelAndView view = new ModelAndView("exec_search_result");
		BankService bS = new BankService();
		List<Boolean> boolList = bS.getDeletionListByCID(custID);
		view.addObject("boolList",boolList);
		bS.updateCustomer(custID, newName, newAddress, newAge);
		Customer cus = bS.searchCustomer(custID);
		view.addObject("customer", cus); // passing to wei hongs search result page
		return view;
	}
	
	// Byron 221025
	//Teller account withdraw: redirects to page asking for withdraw amount
	//Please call this method
	@RequestMapping("/accountWithdraw")
    public ModelAndView accountWithdraw(@RequestParam("AccId") int accId) { //MUST PASS THE ACCOUNTID WHEN U CALL ME
        ModelAndView view = new ModelAndView("teller_acct_withdraw1");
        BankService bS = new BankService() ;
        Account acct = bS.searchAccountByAccountId(accId);
        view.addObject("account1",acct) ;
        return view ;
    }
	
	// Byron 221025 INCOMPLETE - MUST ATTACH LINKS
    @RequestMapping("/accountWithdraw2")
    public ModelAndView accountWithdraw2(@RequestParam("accID") int accId, @RequestParam("val") int val) {
        ModelAndView view = null ;
        BankService bS = new BankService() ;
        int tester = bS.accountWithdraw(accId, val);
        if (tester == 0) { //retrieval error
            String msg = "An unexpected error has occurred, please try again";
            view = new ModelAndView("teller_acct_withdraw1");
            view.addObject("MSG",msg);
        } else if (tester == 1) { //insufficient funds
            String msg = "You have insufficient funds";
            view = new ModelAndView("teller_acct_withdraw1");
            view.addObject("MSG",msg);
        } else if (tester == 2) { //success ***** ATTENTION REQUIRED *****
            view = new ModelAndView("teller_acct_withdraw1"); //redirects them back to same account page 
            String msg = "Withdrawal Successful";
            view.addObject("MSG",msg);
        }
        Account acct = bS.searchAccountByAccountId(accId);
        view.addObject("account1",acct) ;
        return view ;
    }
	
	// Byron 221025 INCOMPLETE - MUST ATTACH LINKS
	@RequestMapping("/transferFunds")
	public ModelAndView transferFunds(@RequestParam("customerId") int custId) { //someone will call this, and pass the CustomerID
		ModelAndView view = new ModelAndView("teller_acct_deposit1") ;
		view.addObject("custId1",custId);
		return view ;
	}
	
	// Byron 221025
	@RequestMapping("/transferFunds2")
	public ModelAndView transferFunds2(@RequestParam("custId") int custId, @RequestParam("accType") String accType, @RequestParam("amount") int amt) {
		BankService bS = new BankService() ;
		int result = bS.transferMoney(custId, accType, amt) ;
		ModelAndView view = null;
		if (result == 0) { //unknown error
			view = new ModelAndView("teller_acct_deposit1") ;
			String msg = "An unknown error has occurred";
			view.addObject("custId1",custId);
			view.addObject("MSG",msg);
		} else if (result == 1) { //not enough accounts (need two)
			view = new ModelAndView("teller_acct_deposit1") ;
			view.addObject("custId1",custId);
			String msg = "You need two accounts to perform a transfer";
			view.addObject("MSG",msg);
		} else if (result == 2) { //insufficient funds
			view = new ModelAndView("teller_acct_deposit1") ;
			view.addObject("custId1",custId);
			String msg = "You do not have sufficient funds for the transfer";
			view.addObject("MSG",msg);
		} else if (result == 3) { //successful transfer 
			view = new ModelAndView("teller_acct_deposit1");
			view.addObject("custId1",custId);
			String msg = "Transfer successful";
			view.addObject("MSG",msg);
		}
		return view ;
	}
	
	// Patt 221025
	@RequestMapping("/create-customer-page")
	public ModelAndView createCustomerPage() {
		ModelAndView view = new ModelAndView("exec-create-customer-page");
		return view;
	}

	// Patt 221025 Patt 221026
	@RequestMapping(value = "/create-customer", method = RequestMethod.GET)
	public ModelAndView createCustomer(@RequestParam("ssn") int ssn, @RequestParam("name") String name,
			@RequestParam("age") int age, @RequestParam("address") String address, ModelMap model) {
		ModelAndView view = null;
		BankService service = new BankService();
		Customer customer = new Customer(0, ssn, name, address, age);
		System.out.println(customer);
		boolean creationResult = service.createCustomer(customer);
		if (creationResult) {
			// if successful creation - redirect to list page
			view = new ModelAndView("exec_list_cust"); // edit link later
			List<Customer> customerList = service.getCustomerList();
			List<Boolean> boolList = service.getDeletionList();
			view.addObject("custList", customerList);
			view.addObject("boolList", boolList);
			return view;
		} else {
			// failed creation because SSN clash
			view = new ModelAndView("exec-create-customer-page");
			String errorMsg = "SSN already exists!";
			view.addObject("errorMsg", errorMsg);
		}
		return view;
	}

	// Patt 221025 Patt 221026
	@RequestMapping(value = "/delete-customer", method = RequestMethod.GET)
	public ModelAndView deleteCustomer(@RequestParam("customerId") int ws_cust_id, ModelMap model) {
		ModelAndView view = null;
		BankService service = new BankService();
		boolean b = service.deleteCustomer(ws_cust_id);
		if (b) {
			// if success deletion - redirect to list page
			view = new ModelAndView("exec_list_cust"); // edit link later
			List<Customer> customerList = service.getCustomerList();
			List<Boolean> boolList = service.getDeletionList();
			view.addObject("boolList",boolList);
			view.addObject("custList", customerList);
			return view;
		} else {
			// failure
			view = new ModelAndView("failure");
		}
		return view;
	}

	
	// Wan Lin 221025
	@RequestMapping("/tellerSearchLoad")
	public ModelAndView loadSearchCustomerPage() {
		ModelAndView view = new ModelAndView("TellerSearchByCusId");
		return view;
	}

	// Wan Lin 221025
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView findCustomer(@RequestParam("customerId") String customerId, ModelMap model) {
		ModelAndView view = new ModelAndView("TellerSearchByCusIdResult");
		BankService service = new BankService();
		Customer cust = service.findCustomer(Integer.parseInt(customerId));
		view.addObject("customer", cust);
		return view;
	}
	
	// Wan Lin 221025 Qan Lin 221026
	@RequestMapping(value="/findAcc", method = RequestMethod.GET) //findAcc.html
	public ModelAndView tellerSearchByAccId(@RequestParam("accountId") String accountId, ModelMap model) {
		ModelAndView view = new ModelAndView("TellerSearchByAccResult");
		BankService service = new BankService();
		Account acc = service.tellerSearchByAccId(Integer.parseInt(accountId));
		view.addObject("Account", acc); //acc-object, Account ref name (linked to jsp)
		return view;
	}
	
	// Wei Hong 221025
	@RequestMapping("/execviewacc")
	public ModelAndView execViewAcc(@RequestParam("customerId") String customerId, ModelMap model) {
		ModelAndView view = new ModelAndView("exec_view_acc");
		BankService service = new BankService();
		List<Account> accList = service.getAccountByCustomerId(Integer.parseInt(customerId));
		view.addObject("accList",accList);
		int custId = Integer.parseInt(customerId);
		view.addObject("customerId",custId);
		return view;
	}
	// Wei Hong 221025
	@RequestMapping(value = "/exec_delete", method = RequestMethod.GET)
	public ModelAndView createAccount(@RequestParam("accountId") String accountId, ModelMap model) {
		ModelAndView view=new ModelAndView("exec_deleteResult");
		BankService service=new BankService();
		boolean b=service.deleteAccount(Integer.parseInt(accountId));
		if(b==true) {
			view.addObject("lmao","1");
		} else {
			view.addObject("lmao","0");
		}
		return view;
	}
	// Wei Hong 221025
	@RequestMapping(value = "/exec_create")
	public ModelAndView createAccount(@RequestParam("customerId") int customerId) {
		ModelAndView view=new ModelAndView("exec_create");
		// call service to determine which accounts a customer has
		BankService service = new BankService();
		int accs = service.getCustomerAccs(customerId); // returns 0/1/2/3; if customer has 0/savings/current/both accs
		view.addObject("numAccs",accs);
		view.addObject("customerId",customerId);
		return view;
	}
	
	// Oleta 221026
	@RequestMapping(value = "/deposit")
	public ModelAndView tellerDeposit(@RequestParam("acctId") String acctId) {
		ModelAndView view = new ModelAndView("teller_acct_deposit_OK");
		BankService service = new BankService();
		Account account = service.searchAccountByAccountId(Integer.parseInt(acctId));
		view.addObject("account", account);
		return view;
	}
	
	// Oleta 221026
	@RequestMapping(value = "/deposit-submit")
	public ModelAndView tellerDepositSubmit(@RequestParam("accountId") String accountId, @RequestParam("depositAmount") String depositAmount) {
		ModelAndView view = new ModelAndView();
		BankService service = new BankService();
		Transaction transaction = new Transaction();
		transaction.setAccountId(Integer.parseInt(accountId));
		transaction.setTransactionAmount(Integer.parseInt(depositAmount));
		transaction.setTransactionType("Deposit");
		LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        long date = zdt.toInstant().toEpochMilli();
		Date da = new Date(date) ; //SQL version of date, not java util
		transaction.setTransactionDate(da);
		boolean result = service.createTransaction(transaction);
		if(result) { //successful deposit
			Account account = service.searchAccountByAccountId(Integer.parseInt(accountId));
//			view = new ModelAndView("teller-acct-page");
			view = new ModelAndView("teller_acct_deposit_OK"); //my own change
			String msg = "Deposit was successful";
			view.addObject("MSG",msg);
			view.addObject("account", account);
		} else { //unsuccessful deposit
			view = new ModelAndView("teller_acct_deposit_OK");
			String msg = "Deposit was unsuccessful. An unknown error has occurred";
			view.addObject("MSG",msg);
			view.addObject("acctId", accountId);
		}
		return view;
	}
	
	// Byron 221026
	//going to search transaction page by last N transactions and date
	@RequestMapping("/searchTxTeller") //*** SOMEONE HAS TO CALL THIS, AND PASS AN ACCOUNT ID ***
	public ModelAndView searchTx(@RequestParam("accountId") int accId) {
		ModelAndView view = new ModelAndView("teller_act_statement1") ;
		view.addObject("accId",accId);
		return view ;
	}
	// Byron 221026
	//tx page continued
	@RequestMapping("/searchTxTeller2")
	public ModelAndView searchTx2(@RequestParam("accId") int accId, @RequestParam("methodType") String methodType) {
		ModelAndView view = null ;
		if ("lastN".equals(methodType)) {
			view = new ModelAndView("teller_act_statement_N1");
			view.addObject("accId",accId);
		}
		if ("date".equals(methodType)) { 
			view = new ModelAndView("teller_act_statement_date1");
			view.addObject("accId",accId);
		}
		return view ;
	}
	// Byron 221026
	//tx page continued
	@RequestMapping("/searchTxTellerNType")
	public ModelAndView searchTxNType(@RequestParam("accId") int accId, @RequestParam("num") String num) {
		ModelAndView view = new ModelAndView("teller_act_statement_N1") ;
		int num1 = Integer.parseInt(num);
		BankService bS = new BankService() ;
		ArrayList<Transaction> li = bS.getNTx(accId, num1);
		bS.printList(li); //generate pdf file
		bS.exportListToCSV(li); //generate csv file
		bS.exportListToXLSX(li); // generate xlsx file
		view.addObject("accId", accId);
		view.addObject("li",li);
		view.addObject("test",num1);
//		bS.printTransactionCSVNumber(accId,num1); //generate PDF File
		return view ;
	}
	// Byron 221026
	//tx page continued
	@RequestMapping("/searchTxTellerDate") //@DateTimeFormat(pattern="yyyy.mm.dd")
	public ModelAndView searchTxTellerDate(@RequestParam("accId") int accId,@RequestParam(value="startDate") String startDate1, @RequestParam(value="endDate") String endDate1) throws ParseException {
		ModelAndView view = new ModelAndView("teller_act_statement_date1") ;
		BankService bS = new BankService() ;
		System.out.println("StartDate: "+startDate1);
		System.out.println("EndDate: "+endDate1);
//	    java.util.Date startDateJU=new SimpleDateFormat("yyyy-mm-dd").parse(startDate1); 
//	    java.sql.Date startDate = new java.sql.Date(startDateJU.getTime());
//	    java.util.Date endDateJU=new SimpleDateFormat("yyyy-mm-dd").parse(endDate1); 
//	    java.sql.Date endDate = new java.sql.Date(endDateJU.getTime());
		
		ArrayList<Transaction> arr = bS.getTxWithinDate(accId, startDate1, endDate1);
		bS.printList(arr); //generate pdf file
		bS.exportListToCSV(arr); //generate csv file
		bS.exportListToXLSX(arr); // generate xlsx file
		view.addObject("arr",arr);
		int num1 = 1 ;
		view.addObject("tester",num1);
		view.addObject("accId",accId);
		return view ;
	}
	
	// Wei Hong 221026
    @RequestMapping(value = "/exec_create_submit", method = RequestMethod.GET)
    public ModelAndView createAccountSubmit(@RequestParam("customerId") int customerId, @RequestParam("accountType") String accountType, @RequestParam("balance") int balance,ModelMap model) {
        ModelAndView view = null;
        BankService service = new BankService();
        Account account = new Account();
        account.setCustomerId(customerId);
        account.setAccountType(accountType);
        //
        account.setBalance(balance);
        long s = System.currentTimeMillis();
        java.sql.Date var = new java.sql.Date(s);
        account.setCreationDate(var);
        boolean result = service.createAccount(account);
        if (result) {
//            view = new ModelAndView("exec_view_acc");
//            List<Account> accountList = service.getAccountList();
//            view.addObject("accList",accountList);
//            return view;
        	view = new ModelAndView("exec_view_acc");
    		List<Account> accList = service.getAccountByCustomerId(customerId);
    		view.addObject("accList",accList);
    		view.addObject("customerId",customerId);
    		return view;

        }
        else {
        	// i think we will never reach here with validations in place
            view = new ModelAndView("exec_create");
            String errorMsg = "Account Type already exists!";
            view.addObject("errorMsg", errorMsg);
        }
        return view;
    }
	
	// Wei Hong 221026
	@RequestMapping("/allacclist")
	public ModelAndView listAllAccount() {
		ModelAndView view = new ModelAndView("exec_list_all_acc");
		BankService service = new BankService();
		List<Account> accList = service.getAccountList();
		view.addObject("accList",accList);
		return view;
	}
	
	// Wei Hong 221025 Updated
	@RequestMapping(value = "exechome")
	public ModelAndView execHome() {
			ModelAndView view = new ModelAndView("exec_home");
		return view;
	}
	
	//Wan Lin 221026
	@RequestMapping(value="/listAcc", method = RequestMethod.GET) //listAcc.html
	public ModelAndView dropDown(@RequestParam("customerId") int customerId, ModelMap model) {
		ModelAndView view = new ModelAndView("teller_search_cust");
		BankService service = new BankService();
		List<Account> acc= service.getAccountDetails(customerId);
		view.addObject("acc", acc); 
		return view;
	}
	
	@RequestMapping("/downloadPdf")
	public ModelAndView downloadPdf() {
		ModelAndView view = new ModelAndView("downloadPdf");
		return view;
	}
	
	@RequestMapping("/downloadCsv")
	public ModelAndView downloadCsv() {
		ModelAndView view = new ModelAndView("downloadCsv");
		return view;
	}
	
	@RequestMapping("/downloadXlsx")
	public ModelAndView downloadXlsx() {
		ModelAndView view = new ModelAndView("downloadXlsx");
		return view;
	}
}
