package com.finastra.jade.tellersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.finastra.jade.tellersystem.object.Balance;
import com.finastra.jade.tellersystem.object.Transaction;
import com.finastra.jade.tellersystem.object.TransactionAccount;
import com.finastra.jade.tellersystem.util.CustomStringUtils;
import com.finastra.jade.tellersystem.util.DataConnect;

public class TransactionDao {

	public static List<TransactionAccount> getAllTransactionAccounts() {
		Connection con = null;
		PreparedStatement ps = null;
		List<TransactionAccount> transactionAccounts = new ArrayList<>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT customer_first_name, customer_middle_name, customer_last_name, "
					+ "account_id, account_overdraft FROM customer c INNER JOIN account a "
					+ "ON c.customer_id = a.customer_id");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("customer_first_name");
				String middleName = rs.getString("customer_middle_name");
				if (rs.wasNull()) {
					middleName = "";
				}
				String lastName = rs.getString("customer_last_name");

				String fullName = CustomStringUtils.fullName(firstName, middleName, lastName);

				String accountId = rs.getString("account_id");
				double overdraft = rs.getInt("account_overdraft");

				Balance balance = LedgerDao.getBalance(accountId);
				double balanceAmount = balance.getBalance();
				String balanceStatus = balance.getBalanceStatus();

				transactionAccounts
						.add(new TransactionAccount(accountId, fullName, balanceAmount, balanceStatus, overdraft));
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return transactionAccounts;
	}

	public static List<TransactionAccount> getRecipientAccounts(String accountNumber) {
		Connection con = null;
		PreparedStatement ps = null;
		List<TransactionAccount> recipientAccounts = new ArrayList<>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT customer_first_name, customer_middle_name, customer_last_name, "
					+ "account_id, account_overdraft FROM customer c INNER JOIN account a "
					+ "ON c.customer_id = a.customer_id WHERE account_currency=? AND account_id NOT IN (?)");

			ps.setString(1, accountNumber.substring(0, 3));
			ps.setString(2, accountNumber);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("customer_first_name");
				String middleName = rs.getString("customer_middle_name");
				if (rs.wasNull()) {
					middleName = "";
				}
				String lastName = rs.getString("customer_last_name");

				String fullName = CustomStringUtils.fullName(firstName, middleName, lastName);

				String accountId = rs.getString("account_id");
				double overdraft = rs.getInt("account_overdraft");

				Balance balance = LedgerDao.getBalance(accountId);
				double balanceAmount = balance.getBalance();
				String balanceStatus = balance.getBalanceStatus();

				recipientAccounts
						.add(new TransactionAccount(accountId, fullName, balanceAmount, balanceStatus, overdraft));
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return recipientAccounts;
	}

	public static List<Transaction> getAllTransactions() {
		Connection con = null;
		PreparedStatement ps = null;
		List<Transaction> transactions = new ArrayList<>();

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT entry_id, entry_type, entry_amount, entry_date, recipient_id, account_id "
					+ "FROM ledger_entry WHERE entry_type IN ('CrD', 'DrW', 'DrT')");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int traceNumber = rs.getInt("entry_id");
				String type = rs.getString("entry_type");
				double amount = rs.getDouble("entry_amount");
				Date date = rs.getTimestamp("entry_date");
				String recipientId = rs.getString("recipient_id");
				if (rs.wasNull()) {
					recipientId = "---";
				}
				String accountId = rs.getString("account_id");

				transactions
						.add(new Transaction(traceNumber, type, amount, date, recipientId, accountId));
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return transactions;
	}

	public static Transaction getTransaction(int traceNumber) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT * FROM ledger_entry WHERE entry_id=?");

			ps.setInt(1, traceNumber);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int entryId = rs.getInt("entry_id");
				String type = rs.getString("entry_type");
				double amount = rs.getDouble("entry_amount");
				double transactionBalance = rs.getDouble("entry_balance");
				String transactionBalanceStatus = rs.getString("entry_balance_status");

				double resultingBalance = transactionBalance;
				String resultingBalanceStatus = transactionBalanceStatus;

				if (type.substring(0, 2).equals("Cr")) { // if transaction credits account, do:
					if (transactionBalanceStatus.equals("Credit")) { // if balance on transaction is in credit, do:
						resultingBalance += amount;
					} else { // if in debit, then do:
						resultingBalance -= amount;
						if (resultingBalance < 0) {
							resultingBalance = Math.abs(resultingBalance);
							resultingBalanceStatus = CustomStringUtils.reverseBalanceStatus(resultingBalanceStatus);
						}
					}
				} else { // if transaction debits account, do:
					if (transactionBalanceStatus.equals("Debit")) { // if balance on transaction is in debit, do:
						resultingBalance += amount;
					} else { // if in credit, then do:
						resultingBalance -= amount;
						if (resultingBalance < 0) {
							resultingBalance = Math.abs(resultingBalance);
							resultingBalanceStatus = CustomStringUtils.reverseBalanceStatus(resultingBalanceStatus);
						}
					}
				}

				Date entryDate = rs.getTimestamp("entry_date");
				String accountId = rs.getString("account_id");

				return new Transaction(entryId, type, amount, transactionBalance, transactionBalanceStatus,
						resultingBalance, resultingBalanceStatus, entryDate, accountId);
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		System.out.println("For some reason, getTransaction returned Null. :p");
		return null;
	}
}
