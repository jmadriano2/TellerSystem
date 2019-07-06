package com.finastra.jade.tellersystem.object;

import java.util.Date;

public class LedgerEntry {
	private int entryId;
	private String type;
	private double amount;
	private Date entryDate;
	private String accountId;

	public LedgerEntry(int entryId, String type, double amount, Date entryDate, String accountId) {
		super();
		this.entryId = entryId;
		this.type = type;
		this.amount = amount;
		this.entryDate = entryDate;
		this.accountId = accountId;
	}

	public int getEntryId() {
		return entryId;
	}

	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
