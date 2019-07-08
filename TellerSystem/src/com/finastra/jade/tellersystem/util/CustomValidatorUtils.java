package com.finastra.jade.tellersystem.util;

import com.finastra.jade.tellersystem.dao.AccountDao;
import com.finastra.jade.tellersystem.object.Account;

public class CustomValidatorUtils {
	public static boolean negativeSequence(int sequence) {
		if (sequence < 0) {
			CustomMessageUtils.showError("The sequence number must not be a negative value");
			System.out.println("transaction amount is negative. -_-");
			return true;
		}
		return false;
	}

	public static boolean negativeId(int id) {
		if (id < 0) {
			CustomMessageUtils.showError("The customer number must not be a negative value");
			System.out.println("transaction amount is negative. -_-");
			return true;
		}
		return false;
	}

	public static boolean zeroAmount(double amount) {
		if (amount == 0) {
			CustomMessageUtils.showError("The transaction amount must not be 0");
			System.out.println("transaction amount is 0. -_-");
			return true;
		}
		return false;
	}

	public static boolean negativeAmount(double amount) {
		if (amount < 0) {
			CustomMessageUtils.showError("The transaction amount must not be a negative value");
			System.out.println("transaction amount is negative. -_-");
			return true;
		}
		return false;
	}

	public static boolean validOverdraft(String accountNumber, double amount, double overdraft) {
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
			System.out.println(amount + " >? " + balance);
			if (amount > balance) {
				resultingBalanceStatus = CustomStringUtils.reverseBalanceStatus(resultingBalanceStatus);
				resultingBalance = Math.abs(resultingBalance);
			}
			System.out.println(balance + " - " + amount + " = " + resultingBalance + " the account is in "
					+ resultingBalanceStatus);

		}
		System.out.println(resultingBalance + " >? " + overdraft);
		if (resultingBalanceStatus.equals("Debit") && resultingBalance > overdraft) {
			CustomMessageUtils.showError("Transaction amount must not exceed overdraft limit.");
			return false;
		}
		return true;
	}
}
