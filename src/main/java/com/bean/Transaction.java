package com.bean;

import java.sql.Date;

public class Transaction {

	int transactionId;
	
	int accountId;
	
	int transactionAmount;
	
	String transactionType;
	
	Date transactionDate;

	public Transaction(int transactionId, int accountId, int transactionAmount, String transactionType,
			Date transactionDate) {
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.transactionAmount = transactionAmount;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
	}

	public Transaction() {
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(int transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", accountId=" + accountId + ", transactionAmount="
				+ transactionAmount + ", transactionType=" + transactionType + ", transactionDate=" + transactionDate
				+ "]";
	}
	
	
}
