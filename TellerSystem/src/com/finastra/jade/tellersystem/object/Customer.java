package com.finastra.jade.tellersystem.object;

import java.util.Date;

public class Customer {
	private String customer_id;
	private String customer_first_name;
	private String customer_middle_name;
	private String customer_last_name;
	private String customer_full_name;
	private Date customer_birthday;
	private String customer_sex;
	private String customer_civil_status;
	private String customer_address;
	private String customer_occupation;
	private String customer_description;
	private Date customer_date_joined;
	private Date customer_date_updated;
	private int teller_id;
	
	public Customer(String customer_id, String customer_full_name, String customer_occupation,
			Date customer_date_joined, int teller_id) {
		super();
		this.customer_id = customer_id;
		this.customer_full_name = customer_full_name;
		this.customer_occupation = customer_occupation;
		this.customer_date_joined = customer_date_joined;
		this.teller_id = teller_id;
	}

	public Customer(String customer_id, String customer_first_name, String customer_middle_name,
			String customer_last_name, Date customer_birthday, String customer_sex, String customer_civil_status,
			String customer_address, String customer_occupation, String customer_description, Date customer_date_joined) {
		super();
		this.customer_id = customer_id;
		this.customer_first_name = customer_first_name;
		this.customer_middle_name = customer_middle_name;
		this.customer_last_name = customer_last_name;
		this.customer_birthday = customer_birthday;
		this.customer_sex = customer_sex;
		this.customer_civil_status = customer_civil_status;
		this.customer_address = customer_address;
		this.customer_occupation = customer_occupation;
		this.customer_description = customer_description;
		this.customer_date_joined = customer_date_joined;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_first_name() {
		return customer_first_name;
	}

	public void setCustomer_first_name(String customer_first_name) {
		this.customer_first_name = customer_first_name;
	}

	public String getCustomer_middle_name() {
		return customer_middle_name;
	}

	public void setCustomer_middle_name(String customer_middle_name) {
		this.customer_middle_name = customer_middle_name;
	}

	public String getCustomer_last_name() {
		return customer_last_name;
	}

	public void setCustomer_last_name(String customer_last_name) {
		this.customer_last_name = customer_last_name;
	}

	public String getCustomer_full_name() {
		return customer_full_name;
	}

	public void setCustomer_full_name(String customer_full_name) {
		this.customer_full_name = customer_full_name;
	}

	public Date getCustomer_birthday() {
		return customer_birthday;
	}

	public String getCustomer_sex() {
		return customer_sex;
	}

	public String getCustomer_civil_status() {
		return customer_civil_status;
	}

	public void setCustomer_birthday(Date customer_birthday) {
		this.customer_birthday = customer_birthday;
	}

	public void setCustomer_sex(String customer_sex) {
		this.customer_sex = customer_sex;
	}

	public void setCustomer_civil_status(String customer_civil_status) {
		this.customer_civil_status = customer_civil_status;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getCustomer_occupation() {
		return customer_occupation;
	}

	public void setCustomer_occupation(String customer_occupation) {
		this.customer_occupation = customer_occupation;
	}

	public String getCustomer_description() {
		return customer_description;
	}

	public void setCustomer_description(String customer_description) {
		this.customer_description = customer_description;
	}

	public Date getCustomer_date_joined() {
		return customer_date_joined;
	}

	public void setCustomer_date_joined(Date customer_date_joined) {
		this.customer_date_joined = customer_date_joined;
	}

	public Date getCustomer_date_updated() {
		return customer_date_updated;
	}

	public void setCustomer_date_updated(Date customer_date_updated) {
		this.customer_date_updated = customer_date_updated;
	}

	public int getTeller_id() {
		return teller_id;
	}

	public void setTeller_id(int teller_id) {
		this.teller_id = teller_id;
	}

	public String fullName() {
		if(customer_middle_name.isEmpty())
			return customer_first_name.substring(0, 1) + customer_first_name.substring(1).toLowerCase() + " " + customer_last_name.substring(0, 1) + customer_last_name.substring(1).toLowerCase();
		else
			return customer_first_name.substring(0, 1) + customer_first_name.substring(1).toLowerCase() + " " + customer_middle_name.substring(0, 1).toUpperCase() + ". " + customer_last_name.substring(0, 1) + customer_last_name.substring(1).toLowerCase();
	}
}
