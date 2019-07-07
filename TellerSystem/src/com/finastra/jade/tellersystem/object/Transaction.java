package com.finastra.jade.tellersystem.object;

import java.util.Date;

public class Transaction {
	private int traceNumber;
	private int recipientTraceNumber;
	private String type;
	private double amount;
	private double transactionBalance;
	private String transactionBalanceStatus;
	private double resultingBalance;
	private String resultingBalanceStatus;
	private Date transactionDate;
	private String recipientId;
	private String accountId;

	public Transaction(int traceNumber, String type, double amount, double transactionBalance,
			String transactionBalanceStatus, double resultingBalance, String resultingBalanceStatus,
			Date transactionDate, String accountId) {
		this.traceNumber = traceNumber;
		this.type = type;
		this.amount = amount;
		this.transactionBalance = transactionBalance;
		this.transactionBalanceStatus = transactionBalanceStatus;
		this.resultingBalance = resultingBalance;
		this.resultingBalanceStatus = resultingBalanceStatus;
		this.transactionDate = transactionDate;
		this.accountId = accountId;
	}

	public Transaction(int traceNumber, int recipientTraceNumber, String type, double amount, Date transactionDate, String recipientId,
			String accountId) {
		this.traceNumber = traceNumber;
		this.recipientTraceNumber = recipientTraceNumber;
		this.type = type;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.recipientId = recipientId;
		this.accountId = accountId;
	}

	public int getTraceNumber() {
		return traceNumber;
	}

	public void setTraceNumber(int traceNumber) {
		this.traceNumber = traceNumber;
	}

	public int getRecipientTraceNumber() {
		return recipientTraceNumber;
	}

	public void setRecipientTraceNumber(int recipientTraceNumber) {
		this.recipientTraceNumber = recipientTraceNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public double getTransactionBalance() {
		return transactionBalance;
	}

	public void setTransactionBalance(double transactionBalance) {
		this.transactionBalance = transactionBalance;
	}

	public double getResultingBalance() {
		return resultingBalance;
	}

	public void setResultingBalance(double resultingBalance) {
		this.resultingBalance = resultingBalance;
	}

	public String getResultingBalanceStatus() {
		return resultingBalanceStatus;
	}

	public void setResultingBalanceStatus(String resultingBalanceStatus) {
		this.resultingBalanceStatus = resultingBalanceStatus;
	}

	public String getTransactionBalanceStatus() {
		return transactionBalanceStatus;
	}

	public void setTransactionBalanceStatus(String transactionBalanceStatus) {
		this.transactionBalanceStatus = transactionBalanceStatus;
	}

	public double getCurrentBalance() {
		return resultingBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.resultingBalance = currentBalance;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "Transaction [traceNumber=" + traceNumber + ", type=" + type + ", amount=" + amount
				+ ", transactionDate=" + transactionDate + ", recipientId=" + recipientId + ", accountId=" + accountId
				+ "]";
	}

}
