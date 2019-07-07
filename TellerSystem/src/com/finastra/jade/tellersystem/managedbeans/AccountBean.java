package com.finastra.jade.tellersystem.managedbeans;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.finastra.jade.tellersystem.dao.AccountDao;
import com.finastra.jade.tellersystem.dao.LedgerDao;
import com.finastra.jade.tellersystem.object.Account;
import com.finastra.jade.tellersystem.object.Balance;
import com.finastra.jade.tellersystem.util.CustomStringUtils;
import com.finastra.jade.tellersystem.util.CustomMessageUtils;

@SessionScoped
@ManagedBean
public class AccountBean {
	private List<Account> accountList;
	private String accountId;
	private String type;
	private double overdraft;
	private String currency;
	private int sequence;
	private double initialDeposit;
	private Balance balance;
	private Date dateCreated;
	private int customerId;
	private String customerName;

	public AccountBean() {

	}

	@PostConstruct
	public void init() {
		accountList = AccountDao.getAllAccounts();
	}

	public List<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(double overdraft) {
		this.overdraft = overdraft;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public double getInitialDeposit() {
		return initialDeposit;
	}

	public void setInitialDeposit(double initialDeposit) {
		this.initialDeposit = initialDeposit;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String paddedCustomerId() {
		return CustomStringUtils.padSixZeroes(customerId);
	}

	public String paddedAccountSequence() {
		return CustomStringUtils.padTwoZeroes(sequence);
	}

	public String typeName(String accountType) {
		return CustomStringUtils.formatAccountType(accountType);
	}

	public String typeName() {
		return CustomStringUtils.formatAccountType(type);
	}

	public String toCurrencyFormat(double amount) {
		return CustomStringUtils.currencyFormat(amount);
	}

	public void resetAccountBean() {
		type = "S";
		overdraft = 0;
		currency = "PHP";
		sequence = 0;
		customerId = 0;
		initialDeposit = 0;
		balance = new Balance(0, 0);
		System.out.println("Reset Account!");
		accountList = AccountDao.getAllAccounts();
	}

	public boolean typeIsCurrent() {
		System.out.println("Account: " + type);

		if (type.equals("S")) {
			overdraft = 0;
		}
		return type.equals("C");
	}

	public String getBalanceAmount() {
		return CustomStringUtils.currencyFormat(balance.getBalance()) + " "
				+ CustomStringUtils.formatBalanceStatus(balance.getBalanceStatus());
	}

	public String createAccountForm() {
		if(customerId==0) {
			CustomMessageUtils.showWarning("Please choose a customer");
			return "#";
		}
		
		type = "S";

		return "create_account";
	}

	public String createAccount() throws ParseException {
		String paddedCustomerId = paddedCustomerId();
		String paddedAccountSequence = paddedAccountSequence();
		accountId = currency + paddedCustomerId + type + paddedAccountSequence;
		System.out.println("Account ID: " + accountId + "\nCustomer ID: " + customerId);

		if (!AccountDao.exists(accountId)) {
			if (AccountDao.insertAccount(accountId, type, currency, sequence, overdraft, customerId, initialDeposit)) {
				System.out.println("returns create_account_success");
				accountList = AccountDao.getAllAccounts();

				balance = LedgerDao.getBalance(accountId);

				return "create_account_success";
			}
		}
		CustomMessageUtils.showError("The account with ID '" + accountId + "' already exists");
		return "create_account_error";
	}

	public String viewAccountDetails() {

		Account account = AccountDao.getAccount(accountId);
		account.toString();
		setAccountBean(account);
		balance = LedgerDao.getBalance(accountId);

		return "account_details";
	}
	
	public void setAccount(String viewed_account, String fullName) {
		accountId = viewed_account;
		System.out.println("You have set account to " + accountId);
	}
	
	public void setCustomer(int customerNumber, String fullName) {
		customerId = customerNumber;
		customerName = fullName;
		System.out.println("You have selected customer " + customerName + " with ID " + customerId);
	}

	private void setAccountBean(Account account) {
		type = account.getAccount_type();
		overdraft = account.getAccount_overdraft();
		currency = account.getAccount_currency();
		sequence = account.getAccount_sequence();
		dateCreated = account.getDate_created();
		customerId = account.getCustomer_id();
		customerName = account.getCustomer_name();
	}

}
