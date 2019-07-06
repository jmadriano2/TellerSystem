package com.finastra.jade.tellersystem.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomStringUtils {

	public static String fullName(String customer_first_name, String customer_middle_name, String customer_last_name) {
		if (customer_middle_name.isEmpty())
			return customer_first_name + " " + customer_last_name.substring(0, 1)
					+ customer_last_name.substring(1).toLowerCase();
		else
			return customer_first_name + " " + customer_middle_name.substring(0, 1).toUpperCase() + ". "
					+ customer_last_name.substring(0, 1) + customer_last_name.substring(1).toLowerCase();
	}

	public static String currencyFormat(double amount) {
		if (amount == 0)
			return "0.00";
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		return formatter.format(amount);
	}

	public static String padSixZeroes(int id) {
		return String.format("%06d", id);
	}

	public static String padTwoZeroes(int sequence) {
		return String.format("%02d", sequence);
	}

	public static String formatAccountType(String accountType) {
		if (accountType.equals("S"))
			return "Savings";
		else
			return "Current";
	}

	public static String formatBalanceStatus(String balanceStatus) {
		if (balanceStatus.equals("Debit"))
			return "Dr";
		else
			return "Cr";
	}

	public static String reverseBalanceStatus(String balanceStatus) {
		if (balanceStatus.equals("Credit")) {
			return "Debit";
		} else {
			return "Credit";
		}
	}

	public static String parseTransactionType(String transactionCode) {
		if (transactionCode.substring(2).contentEquals("D")) {
			return "Deposit";
		} else if (transactionCode.substring(2).contentEquals("W")) {
			return "Withrawal";
		} else if (transactionCode.substring(2).contentEquals("T")) {
			return "Transfer";
		} else {
			return "Undefined Transaction Type";
		}

	}

	public static String balanceWithStatus(double balance, String balanceStatus) {
		return currencyFormat(balance) + " " + formatBalanceStatus(balanceStatus);
	}
	
	public static String formatDate(Date date) {
		
		String pattern = "MM/dd/yyyy | h:mm a";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		return simpleDateFormat.format(date);
	}
}
