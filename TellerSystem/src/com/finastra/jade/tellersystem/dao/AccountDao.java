package com.finastra.jade.tellersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.finastra.jade.tellersystem.object.Account;
import com.finastra.jade.tellersystem.object.Balance;
import com.finastra.jade.tellersystem.util.CustomStringUtils;
import com.finastra.jade.tellersystem.util.DataConnect;

public class AccountDao {

	public static boolean insertAccount(String accountId, String type, String currency, int sequence, double overdraft,
			int customerId, double initialDeposit, Timestamp timestamp) {
		int i = 0;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DataConnect.getConnection();
			stmt = conn.prepareStatement("INSERT INTO account values (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, accountId);
			stmt.setString(2, type);
			stmt.setString(3, currency);
			stmt.setInt(4, sequence);
			stmt.setDouble(5, overdraft);
			stmt.setTimestamp(6, timestamp);
			stmt.setInt(7, customerId);
			System.out.println(customerId);
			i = stmt.executeUpdate();

			System.out.println("Account '" + accountId + "' Added Successfully\ni: " + i);

			LedgerDao.initialDeposit(initialDeposit, accountId);

			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DataConnect.close(conn);
		}
		return i > 0;
	}

	public static boolean exists(String accountId) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean exists;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select account_id " + "from account " + "Where account_id = ?");

			ps.setString(1, accountId);

			ResultSet rs = ps.executeQuery();

			exists = rs.next();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			exists = true;
		} finally {
			DataConnect.close(con);
		}

		return exists;
	}

	public static List<Account> getAllAccounts() {
		Connection con = null;
		PreparedStatement stmt = null;
		List<Account> accounts = new ArrayList<>();

		try {

			con = DataConnect.getConnection();
			stmt = con.prepareStatement("SELECT a.account_id, a.account_type, a.account_currency, "
					+ "a.account_sequence, a.account_overdraft, a.account_date_created, a.customer_id, "
					+ "c.customer_first_name, c.customer_middle_name, c.customer_last_name " + "FROM account a "
					+ "INNER JOIN customer c " + "ON a.customer_id = c.customer_id");

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String accountId = rs.getString("account_id");
					String type = rs.getString("account_type");
					String currency = rs.getString("account_currency");
					int sequence = rs.getInt("account_sequence");
					double overdraft = rs.getDouble("account_overdraft");
					String firstName = rs.getString("customer_first_name");
					String middleName = rs.getString("customer_middle_name");
					if (rs.wasNull()) {
						middleName = "";
					}
					String lastName = rs.getString("customer_last_name");

					Balance balance = LedgerDao.getBalance(accountId);
					double balanceAmount = balance.getBalance();
					String balanceStatus = balance.getBalanceStatus();

					Date dateCreated = rs.getTimestamp("account_date_created");
					int customerId = rs.getInt("customer_id");

					String fullName = CustomStringUtils.fullName(firstName, middleName, lastName);

					accounts.add(new Account(accountId, type, currency, sequence, overdraft, balanceAmount,
							balanceStatus, dateCreated, customerId, fullName));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DataConnect.close(con);
		}

		return accounts;
	}

	public static Account getAccount(String viewed_account) {
		Connection con = null;
		PreparedStatement stmt = null;
		System.out.println(viewed_account);

		try {

			con = DataConnect.getConnection();
			stmt = con.prepareStatement("SELECT a.account_id, a.account_type, a.account_currency, "
					+ "a.account_sequence, a.account_overdraft, a.account_date_created, a.customer_id, "
					+ "c.customer_first_name, c.customer_middle_name, c.customer_last_name FROM account a "
					+ "INNER JOIN customer c ON a.customer_id = c.customer_id WHERE a.account_id=?");

			stmt.setString(1, viewed_account);

			try (ResultSet rs = stmt.executeQuery()) {
				rs.next();
				String accountId = rs.getString("account_id");
				String type = rs.getString("account_type");
				String currency = rs.getString("account_currency");
				int sequence = rs.getInt("account_sequence");
				double overdraft = rs.getDouble("account_overdraft");
				String firstName = rs.getString("customer_first_name");
				String middleName = rs.getString("customer_middle_name");
				if (rs.wasNull()) {
					middleName = "";
				}
				String lastName = rs.getString("customer_last_name");

				Balance balance = LedgerDao.getBalance(accountId);
				double balanceAmount = balance.getBalance();
				String balanceStatus = balance.getBalanceStatus();

				Date dateCreated = rs.getTimestamp("account_date_created");
				int customerId = rs.getInt("customer_id");

				String fullName = CustomStringUtils.fullName(firstName, middleName, lastName);

				return new Account(accountId, type, currency, sequence, overdraft, balanceAmount,
						balanceStatus, dateCreated, customerId, fullName);

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DataConnect.close(con);
		}
	}

	public static double accountIsCurrent() {
		// TODO Auto-generated method stub
		return 0;
	}

}
