package com.finastra.jade.tellersystem.object;

public class TransactionAccount {
	private String accountId;
	private String owner;
	private double balance;
	private String balanceStatus;
	private double overdraft;

	public TransactionAccount(String accountId, String owner, double balance, 
			String balanceStatus, double overdraft) {
		this.accountId = accountId;
		this.owner = owner;
		this.balance = balance;
		this.balanceStatus = balanceStatus;
		this.overdraft = overdraft;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(String balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	public double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(double overdraft) {
		this.overdraft = overdraft;
	}

}
