package com.finastra.jade.tellersystem.object;

public class Balance {
	private double totalDebit;
	private double totalCredit;
	private double balance;
	private String balanceStatus;

	public Balance(double totalDebit, double totalCredit) {
		super();
		this.totalDebit = totalDebit;
		this.totalCredit = totalCredit;
		
		if(totalCredit>=totalDebit) {
			this.balanceStatus = "Credit";
		} else {
			this.balanceStatus = "Debit";
		}
		this.balance = Math.abs(totalDebit - totalCredit); 
	}

	public double getTotalDebit() {
		return totalDebit;
	}

	public void setTotalDebit(double totalDebit) {
		this.totalDebit = totalDebit;
	}

	public double getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(double totalCredit) {
		this.totalCredit = totalCredit;
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

}
