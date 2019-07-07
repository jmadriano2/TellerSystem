package com.finastra.jade.tellersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.finastra.jade.tellersystem.object.Customer;
import com.finastra.jade.tellersystem.util.DataConnect;
import com.finastra.jade.tellersystem.util.CustomStringUtils;

public class CustomerDao {

	public static int insertCustomer(int id, String firstName, String middleName, String lastName, String address,
			String occupation, String description, Date dateJoined, Date birthday, String sex, String civilStatus) {
		int customerNumber = 0;
		Connection conn = null;
		PreparedStatement stmt = null;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		int teller_id = (int) session.getAttribute("userId");
		System.out.println(teller_id);

		try {
			conn = DataConnect.getConnection();
			stmt = conn.prepareStatement(
					"INSERT INTO customer(customer_id, customer_first_name, "
							+ "customer_middle_name, customer_last_name, customer_address, customer_occupation, "
							+ "customer_description, customer_date_joined, teller_id, customer_birthday, "
							+ "customer_sex, customer_civil_status) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			if (id == 0)
				stmt.setNull(1, Types.INTEGER);
			else
				stmt.setInt(1, id);
			stmt.setString(2, firstName);
			stmt.setString(3, middleName);
			stmt.setString(4, lastName);
			stmt.setString(5, address);
			stmt.setString(6, occupation);
			stmt.setString(7, description);
			stmt.setObject(8, dateJoined.toInstant().atZone(ZoneId.of("GMT+8")).toLocalDateTime());
			stmt.setInt(9, teller_id);
			stmt.setDate(10, new java.sql.Date(birthday.getTime()));
			stmt.setString(11, sex);
			stmt.setString(12, civilStatus);
			customerNumber = stmt.executeUpdate();

			ResultSet customer = stmt.getGeneratedKeys();
			if (customer.next())
				customerNumber = customer.getInt(1);
			System.out.println("The Credit Trace Number inside fundsTransfer is " + customerNumber);

			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DataConnect.close(conn);
		}
		return customerNumber;
	}

	public static boolean exists(int customerId) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean exists;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select customer_id " + "from customer " + "Where customer_id = ?");

			ps.setInt(1, customerId);

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

	public static List<Customer> getAllCustomers() {
		Connection con = null;
		PreparedStatement stmt = null;
		List<Customer> customers = new ArrayList<>();

		try {

			con = DataConnect.getConnection();
			stmt = con.prepareStatement("SELECT * FROM  customer");

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int customer_id = rs.getInt("customer_id");
					String padded_customer_id = String.format("%06d", customer_id);
					String customer_first_name = rs.getString("customer_first_name");
					String customer_middle_name = rs.getString("customer_middle_name");
					if (rs.wasNull()) {
						customer_middle_name = "";
					}
					String customer_last_name = rs.getString("customer_last_name");
					String customer_occupation = rs.getString("customer_occupation");
					Date customer_date_joined = rs.getTimestamp("customer_date_joined");
					int teller_id = rs.getInt("teller_id");

					String customer_full_name = CustomStringUtils.fullName(customer_first_name, customer_middle_name,
							customer_last_name);

					customers.add(new Customer(padded_customer_id, customer_full_name, customer_occupation,
							customer_date_joined, teller_id));
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

		return customers;
	}

	public static Customer getCustomer(int viewed_customer) {
		Connection con = null;
		PreparedStatement stmt = null;
		Customer customer;

		try {

			con = DataConnect.getConnection();
			stmt = con.prepareStatement("SELECT customer_id, customer_first_name, customer_middle_name, "
					+ "customer_last_name, customer_birthday, customer_sex, customer_civil_status, customer_address, "
					+ "customer_occupation, customer_description, customer_date_joined FROM  customer WHERE customer_id = ?");

			stmt.setInt(1, viewed_customer);

			try (ResultSet rs = stmt.executeQuery()) {
				rs.next();
				int customer_id = rs.getInt("customer_id");
				String padded_customer_id = String.format("%06d", customer_id);
				String customer_first_name = rs.getString("customer_first_name");
				String customer_middle_name = rs.getString("customer_middle_name");
				if (rs.wasNull()) {
					customer_middle_name = "";
				}
				String customer_last_name = rs.getString("customer_last_name");
				Date customer_birthday = rs.getDate("customer_birthday");
				String customer_sex = rs.getString("customer_sex");
				String customer_civil_status = rs.getString("customer_civil_status");
				String customer_address = rs.getString("customer_address");
				String customer_occupation = rs.getString("customer_occupation");
				String customer_description = rs.getString("customer_description");
				if (rs.wasNull()) {
					customer_description = "";
				}
				Date customer_date_joined = rs.getTimestamp("customer_date_joined");

				customer = new Customer(padded_customer_id, customer_first_name, customer_middle_name,
						customer_last_name, customer_birthday, customer_sex, customer_civil_status, customer_address,
						customer_occupation, customer_description, customer_date_joined);

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

		return customer;
	}

	public static boolean amendCustomer(String firstName, String middleName, String lastName, String address,
			String occupation, String description, int id) {
		int i = 0;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DataConnect.getConnection();
			stmt = conn.prepareStatement("UPDATE customer SET customer_first_name=?, "
					+ "customer_middle_name=?, customer_last_name=?, customer_address=?, customer_occupation=?, "
					+ "customer_description=?, customer_date_updated=CURRENT_TIMESTAMP  WHERE customer_id=?");
			stmt.setString(1, firstName);
			stmt.setString(2, middleName);
			stmt.setString(3, lastName);
			stmt.setString(4, address);
			stmt.setString(5, occupation);
			stmt.setString(6, description);
			stmt.setInt(7, id);
			i = stmt.executeUpdate();

			System.out.println("Data Updated Successfully");

			stmt.close();

			return i > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DataConnect.close(conn);
		}
	}
}
