package com.finastra.jade.tellersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.finastra.jade.tellersystem.object.Balance;
import com.finastra.jade.tellersystem.util.DataConnect;

public class LedgerDao {

	public static boolean initialDeposit(double entryAmount, String accountId) {
		int i = 0;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DataConnect.getConnection();
			stmt = conn.prepareStatement(
					"INSERT INTO ledger_entry(entry_type, entry_amount, entry_balance, entry_balance_status, account_id) VALUES ('CrD',?,?,'Credit',?)");
			stmt.setDouble(1, entryAmount);
			stmt.setDouble(2, entryAmount);
			stmt.setString(3, accountId);
			i = stmt.executeUpdate();

			System.out.println("Deposited " + entryAmount + " to account '" + accountId + "' Successfully\ni: " + i);

			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DataConnect.close(conn);
		}
		return i > 0;
	}

	public static int creditAccount(String entryType, double entryAmount, String accountId) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DataConnect.getConnection();
			stmt = conn.prepareStatement(
					"INSERT INTO ledger_entry(entry_type, entry_amount, entry_balance, entry_balance_status, account_id) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, entryType);
			stmt.setDouble(2, entryAmount);
			Balance balance = getBalance(accountId);
			stmt.setDouble(3, balance.getBalance());
			stmt.setString(4, balance.getBalanceStatus());

			stmt.setString(5, accountId);

			int i = stmt.executeUpdate();

			System.out.println("Deposited " + entryAmount + " to account '" + accountId + "' Successfully\ni: " + i);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
				return rs.getInt(1);

			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DataConnect.close(conn);
		}
		return -1;
	}

	public static int debitAccount(String entryType, double entryAmount, String accountId) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DataConnect.getConnection();
			stmt = conn.prepareStatement(
					"INSERT INTO ledger_entry(entry_type, entry_amount, entry_balance, entry_balance_status, account_id) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, entryType);
			stmt.setDouble(2, entryAmount);
			Balance balance = getBalance(accountId);
			stmt.setDouble(3, balance.getBalance());
			stmt.setString(4, balance.getBalanceStatus());

			stmt.setString(5, accountId);

			int i = stmt.executeUpdate();

			System.out.println("Withrew " + entryAmount + " from account '" + accountId + "' Successfully\ni: " + i);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
				return rs.getInt(1);

			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DataConnect.close(conn);
		}
		return -1;
	}

	public static int creditAccount(String entryType, double entryAmount, String accountId, Date date) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DataConnect.getConnection();
			stmt = conn.prepareStatement(
					"INSERT INTO ledger_entry(entry_type, entry_amount, entry_balance, entry_balance_status, entry_date, account_id) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, entryType);
			stmt.setDouble(2, entryAmount);
			Balance balance = getBalance(accountId);
			stmt.setDouble(3, balance.getBalance());
			stmt.setString(4, balance.getBalanceStatus());

			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			stmt.setDate(5, sqlDate);

			stmt.setString(6, accountId);

			int i = stmt.executeUpdate();

			System.out.println("Deposited " + entryAmount + " to account '" + accountId + "' Successfully\ni: " + i);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
				return rs.getInt(1);

			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DataConnect.close(conn);
		}
		return -1;
	}

	public static int debitAccount(String entryType, double entryAmount, String accountId, Date date) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DataConnect.getConnection();
			stmt = conn.prepareStatement(
					"INSERT INTO ledger_entry(entry_type, entry_amount, entry_balance, entry_balance_status, entry_date, account_id) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, entryType);
			stmt.setDouble(2, entryAmount);
			Balance balance = getBalance(accountId);
			stmt.setDouble(3, balance.getBalance());
			stmt.setString(4, balance.getBalanceStatus());

			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			stmt.setDate(5, sqlDate);

			stmt.setString(6, accountId);

			int i = stmt.executeUpdate();

			System.out.println("Withrew " + entryAmount + " from account '" + accountId + "' Successfully\ni: " + i);

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
				return rs.getInt(1);

			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DataConnect.close(conn);
		}
		return -1;
	}

	public static Balance getBalance(String accountId) {
		Connection con = null;
		PreparedStatement ps = null;
		Balance balance = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT "
					+ "(SELECT SUM(entry_amount) FROM ledger_entry WHERE account_id=? AND entry_type LIKE 'Cr%') AS TOTAL_CREDIT, "
					+ "(SELECT SUM(entry_amount) FROM ledger_entry WHERE account_id=? AND entry_type LIKE 'Dr%') AS TOTAL_DEBIT "
					+ "FROM ledger_entry WHERE account_id=? GROUP BY TOTAL_CREDIT");
			ps.setString(1, accountId);
			ps.setString(2, accountId);
			ps.setString(3, accountId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				double totalDebit = rs.getDouble("TOTAL_DEBIT");
				double totalCredit = rs.getDouble("TOTAL_CREDIT");

				return new Balance(totalDebit, totalCredit);
			}
			return new Balance(0, 0);

		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
		} finally {
			DataConnect.close(con);
		}
		return balance;
	}

}
