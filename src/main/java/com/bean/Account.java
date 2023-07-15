package com.bean;

import java.sql.Date;

public class Account {

	int accountId;
	
	int customerId;
	
	String accountType;
	
	int balance;
	
	Date creationDate;
	
	Date lastTransactionDate;

	public Account() {
	}

	public Account(int accountId, int customerId, String accountType, int balance, Date creationDate,
			Date lastTransactionDate) {
		this.accountId = accountId;
		this.customerId = customerId;
		this.accountType = accountType;
		this.balance = balance;
		this.creationDate = creationDate;
		this.lastTransactionDate = lastTransactionDate;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastTransactionDate() {
		return lastTransactionDate;
	}

	public void setLastTransactionDate(Date lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", customerId=" + customerId + ", accountType=" + accountType
				+ ", balance=" + balance + ", creationDate=" + creationDate + ", lastTransactionDate="
				+ lastTransactionDate + "]";
	}
	
}
