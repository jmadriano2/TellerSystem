package com.finastra.jade.tellersystem.managedbeans;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.finastra.jade.tellersystem.dao.AccountDao;
import com.finastra.jade.tellersystem.dao.LedgerDao;
import com.finastra.jade.tellersystem.dao.TransactionDao;
import com.finastra.jade.tellersystem.object.Account;
import com.finastra.jade.tellersystem.object.Transaction;
import com.finastra.jade.tellersystem.object.TransactionAccount;
import com.finastra.jade.tellersystem.util.CustomMessageUtils;
import com.finastra.jade.tellersystem.util.CustomStringUtils;

@SessionScoped
@ManagedBean
public class TransactionBean {
	private List<TransactionAccount> transactionAccounts;
	private List<TransactionAccount> recipientAccounts;
	private List<Transaction> transactions;

	private String type;
	private String accountNumber;
	private double balanceOnTransaction;
	private String balanceStatus;
	private double resultingBalance;
	private String resultingBalanceStatus;
	private String recipientNumber;
	private double recipientBalanceOnTransaction;
	private String recipientBalanceStatus;
	private double recipientResultingBalance;
	private String recipientResultingBalanceStatus;
	private double amount;
	private double overdraft;
	private Date date;
	private int traceNumber;
	private int recipientTraceNumber;

	public TransactionBean() {
	}

	@PostConstruct
	public void init() {
		transactionAccounts = TransactionDao.getAllTransactionAccounts();
		transactions = TransactionDao.getAllTransactions();
	}

	public void resetTransactionBean() {
		amount = 0;
		transactionAccounts = TransactionDao.getAllTransactionAccounts();
		transactions = TransactionDao.getAllTransactions();
	}

	public void resetRecipientAccounts() {
		amount = 0;
		recipientAccounts = TransactionDao.getRecipientAccounts(accountNumber);
	}

	public List<TransactionAccount> recipientAccounts() {
		recipientAccounts = TransactionDao.getRecipientAccounts(accountNumber);
		return recipientAccounts;
	}

	public List<TransactionAccount> getTransactionAccounts() {
		return transactionAccounts;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void setTransactionAccounts(List<TransactionAccount> transactionAccounts) {
		this.transactionAccounts = transactionAccounts;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getRecipientNumber() {
		return recipientNumber;
	}

	public void setRecipientNumber(String recipientNumber) {
		this.recipientNumber = recipientNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getAccountNumberLabel() {
		System.out.println("The type is " + type);
		if (type.substring(2).equals("T")) {
			return "Sender Account Number";
		}
		return "Account Number";
	}

	public String toCurrencyFormat(double amount) {
		return CustomStringUtils.currencyFormat(amount);
	}

	private void setTransactionBean(Transaction transaction) {
		type = transaction.getType();
		accountNumber = transaction.getAccountId();
		balanceOnTransaction = transaction.getTransactionBalance();
		balanceStatus = transaction.getTransactionBalanceStatus();
		resultingBalance = transaction.getResultingBalance();
		resultingBalanceStatus = transaction.getResultingBalanceStatus();
		amount = transaction.getAmount();
		date = transaction.getTransactionDate();
		traceNumber = transaction.getTraceNumber();
	}

	private void setRecipient(Transaction transaction) {
		recipientNumber = transaction.getAccountId();
		recipientBalanceOnTransaction = transaction.getTransactionBalance();
		recipientBalanceStatus = transaction.getTransactionBalanceStatus();
		recipientResultingBalance = transaction.getResultingBalance();
		recipientResultingBalanceStatus = transaction.getResultingBalanceStatus();
	}

	public String getTransactionType() {
		return CustomStringUtils.parseTransactionType(type);
	}

	public String getTransactionType(String type) {
		return CustomStringUtils.parseTransactionType(type);
	}

	public String getTransactionBalance() {
		return CustomStringUtils.balanceWithStatus(balanceOnTransaction, balanceStatus);
	}

	public String getResultBalance() {
		return CustomStringUtils.balanceWithStatus(resultingBalance, resultingBalanceStatus);
	}

	public String getRecipientTransactionBalance() {
		return CustomStringUtils.balanceWithStatus(recipientBalanceOnTransaction, recipientBalanceStatus);
	}

	public String getRecipientResultBalance() {
		return CustomStringUtils.balanceWithStatus(recipientResultingBalance, recipientResultingBalanceStatus);
	}

	public String getTransactionAmount() {
		return CustomStringUtils.currencyFormat(amount);
	}

	public String getTransactionAmount(double amount) {
		return CustomStringUtils.currencyFormat(amount);
	}

	public String getTransactionDate() {
		return CustomStringUtils.formatDate(date);
	}

	public String getTransactionDate(Date date) {
		return CustomStringUtils.formatDate(date);
	}

	public String getTraceNumber() {
		return CustomStringUtils.padSixZeroes(traceNumber);
	}

	public String getTraceNumber(int traceNumber) {
		return CustomStringUtils.padSixZeroes(traceNumber);
	}

	public boolean isTransfer() {
		if (type.substring(2).equals("T")) {
			return true;
		}
		return false;
	}

	public void selectTransaction(int selectedTraceNumber, int selectedRecipientTraceNumber) {
		traceNumber = selectedTraceNumber;
		recipientTraceNumber = selectedRecipientTraceNumber;

		System.out.println("You have set the traceNumber to " + traceNumber + " and recipientTraceNumber to "
				+ recipientTraceNumber);
	}

	public void setTransaction(String account_id, double transactionOverdraft) {
		accountNumber = account_id;
		overdraft = transactionOverdraft;
		System.out.println(
				"You have set account_id to " + account_id + " and transactionOverdraft to " + transactionOverdraft);
	}

	public void setTransferRecipient(String account_id) {
		recipientNumber = account_id;
		System.out.println("You have set " + recipientNumber + " as recipient to a transfer!");
	}

	public String deposit() {

		if (zeroAmount()) {
			return "#";
		}

		// inserts new transaction and returns generated id
		int traceNumber = LedgerDao.creditAccount("CrD", amount, accountNumber);

		setTransactionBean(TransactionDao.getTransaction(traceNumber));

		return "deposit_success";
	}

	public String withdraw() {

		if (!validOverdraft() || zeroAmount()) {
			return "#";
		}

		int traceNumber = LedgerDao.debitAccount("DrW", amount, accountNumber);

		setTransactionBean(TransactionDao.getTransaction(traceNumber));

		return "withdrawal_success";
	}

	public String transfer() throws SQLException {

		if (!validOverdraft() || zeroAmount()) {
			return "#";
		}

		// inserts new transaction and returns generated ids
		String[] traceNumbers = LedgerDao.transferFunds(accountNumber, recipientNumber, amount).split(",");

		System.out.println("Debit Trace Number: " + traceNumbers[0] + "\nCredit Trace Number: " + traceNumbers[1]);

		setTransactionBean(TransactionDao.getTransaction(Integer.parseInt(traceNumbers[0])));
		setRecipient(TransactionDao.getTransaction(Integer.parseInt(traceNumbers[1])));

		System.out.println("You have successfully transferred " + amount + " " + accountNumber.substring(0, 4)
				+ "\nfrom " + accountNumber + " to " + recipientNumber);

		return "transfer_success";
	}

	public String viewTransactionDetails() {
		setTransactionBean(TransactionDao.getTransaction(traceNumber));
		if(recipientTraceNumber >= 0) {			
			setRecipient(TransactionDao.getTransaction(recipientTraceNumber));
		}

		return "view_transaction_details";
	}

	private boolean zeroAmount() {
		if (amount == 0) {
			CustomMessageUtils.showError("The transaction amount must not be 0");
			System.out.println("withdrawal amount is 0. -_-");
			return true;
		}
		return false;
	}

	private boolean validOverdraft() {
		Account account = AccountDao.getAccount(accountNumber);
		double balance = account.getBalance();
		if (account.getAccount_type().equals("S") && amount > balance) {
			CustomMessageUtils.showError("A savings account cannot be overdrawn.");
			return false;
		}
		double resultingBalance = 0;
		String resultingBalanceStatus = account.getBalanceStatus();

		if (account.getBalanceStatus().equals("Debit")) {
			resultingBalance = balance + amount;
		} else if (account.getBalanceStatus().equals("Credit")) {

			resultingBalance = balance - amount;
			if (balance > amount) {
				CustomStringUtils.reverseBalanceStatus(resultingBalanceStatus);
				resultingBalance = Math.abs(resultingBalance);
			}
			System.out.println(balance + " - " + amount + " = " + resultingBalance);

		}

		if (resultingBalanceStatus.equals("Debit") && resultingBalance > overdraft) {
			CustomMessageUtils.showError("Transaction amount must not exceed overdraft limit.");
			return false;
		}
		return true;
	}
}
