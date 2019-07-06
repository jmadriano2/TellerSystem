package com.finastra.jade.tellersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.finastra.jade.tellersystem.util.DataConnect;

public class LoginDao {

	public static boolean validate(String username, String password) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("SELECT teller_id, teller_username, teller_password FROM teller "
					+ "WHERE teller_username = ? and teller_password = ?");
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				// result found, means valid inputs
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
		return false;
	}

	public static int getTellerId(String username, String password) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement(
					"Select teller_id " + "from teller " + "Where teller_username = ? and teller_password = ?");
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				// result found, means valid inputs
				return rs.getInt("teller_id");
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return 0;
		} finally {
			DataConnect.close(con);
		}
		return 0;
	}

}