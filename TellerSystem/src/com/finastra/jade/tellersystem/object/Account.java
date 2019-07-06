package com.finastra.jade.tellersystem.object;

import java.util.Date;

public class Account {
	private String account_id;
	private String account_type;
	private String account_currency;
	private int account_sequence;
	private double account_overdraft;
	private double balance;
	private String balanceStatus;
	private Date date_created;
	private int customer_id;
	private String customer_name;

	public Account(String account_id, String account_type, String account_currency, int account_sequence,
			double account_overdraft, double balance, String balanceStatus, Date date_created, int customer_id,
			String customer_name) {
		super();
		this.account_id = account_id;
		this.account_type = account_type;
		this.account_currency = account_currency;
		this.account_sequence = account_sequence;
		this.account_overdraft = account_overdraft;
		this.balance = balance;
		this.balanceStatus = balanceStatus;
		this.date_created = date_created;
		this.customer_id = customer_id;
		this.customer_name = customer_name;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getAccount_currency() {
		return account_currency;
	}

	public void setAccount_currency(String account_currency) {
		this.account_currency = account_currency;
	}

	public int getAccount_sequence() {
		return account_sequence;
	}

	public void setAccount_sequence(int account_sequence) {
		this.account_sequence = account_sequence;
	}

	public double getAccount_overdraft() {
		return account_overdraft;
	}

	public void setAccount_overdraft(double account_overdraft) {
		this.account_overdraft = account_overdraft;
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

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	@Override
	public String toString() {
		return "Account [account_id=" + account_id + ", account_type=" + account_type + ", account_currency="
				+ account_currency + ", account_sequence=" + account_sequence + ", account_overdraft="
				+ account_overdraft + ", balance=" + balance + ", date_created=" + date_created + ", customer_id="
				+ customer_id + ", customer_name=" + customer_name + "]";
	}

}
