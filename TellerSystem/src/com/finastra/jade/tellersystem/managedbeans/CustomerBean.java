package com.finastra.jade.tellersystem.managedbeans;

import java.util.Date;
import java.util.List;
import java.text.ParseException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.finastra.jade.tellersystem.dao.CustomerDao;
import com.finastra.jade.tellersystem.object.Customer;
import com.finastra.jade.tellersystem.util.CustomMessageUtils;
import com.finastra.jade.tellersystem.util.CustomStringUtils;
import com.finastra.jade.tellersystem.util.CustomValidatorUtils;

@SessionScoped
@ManagedBean
public class CustomerBean {
	private List<Customer> customerList;
	private int customerId;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date birthday;
	private String sex;
	private String civilStatus;
	private String occupation;
	private String address;
	private String description;
	private Date dateJoined;
	private int tellerId;

	public CustomerBean() {
	}

	@PostConstruct
	public void init() {
		customerList = CustomerDao.getAllCustomers();
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getSex() {
		return sex;
	}

	public String getCivilStatus() {
		return civilStatus;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setCivilStatus(String civilStatus) {
		this.civilStatus = civilStatus;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public int getTellerId() {
		return tellerId;
	}

	public void setTellerId(int tellerId) {
		this.tellerId = tellerId;
	}

	public String customer() {
		return "customer";
	}

	public String paddedCustomerId() {
		String paddedId = String.format("%06d", customerId);
		return paddedId;
	}

	public String getFormattedDate() {
		return CustomStringUtils.formatDate(dateJoined);
	}

	public String getFormattedDate(Date date) {
		return CustomStringUtils.formatDate(date);
	}
	
	public String getParsedSex() {
		return CustomStringUtils.parseSex(sex);
	}
	
	public String getFormattedBirthday() {
		return CustomStringUtils.formatDate(birthday);
	}

	public String tellerName() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		return (String) session.getAttribute("username");
	}

	public void resetCustomerBean() {
		customerId = 0;
		firstName = "";
		middleName = "";
		lastName = "";
		address = "";
		occupation = "";
		description = "";
		dateJoined = new Date(System.currentTimeMillis());
		customerList = CustomerDao.getAllCustomers();
	}

	private void setCustomerBean(Customer customer) {
		firstName = customer.getCustomer_first_name();
		middleName = customer.getCustomer_middle_name();
		lastName = customer.getCustomer_last_name();
		birthday = customer.getCustomer_birthday();
		sex = customer.getCustomer_sex();
		civilStatus = customer.getCustomer_civil_status();
		address = customer.getCustomer_address();
		occupation = customer.getCustomer_occupation();
		description = customer.getCustomer_description();
		dateJoined = customer.getCustomer_date_joined();
	}

	public String createCustomer() throws ParseException {
		if (CustomValidatorUtils.negativeId(customerId)) {
			return "#";
		}

		if (!CustomerDao.exists(customerId)) {
			if (customerId == 0) {
				customerId = CustomerDao.insertCustomer(customerId, firstName, middleName, lastName, address,
						occupation, description, dateJoined, birthday, sex, civilStatus);
			} else {
				CustomerDao.insertCustomer(customerId, firstName, middleName, lastName, address,
						occupation, description, dateJoined, birthday, sex, civilStatus);
			}
			customerList = CustomerDao.getAllCustomers();
			setCustomerBean(CustomerDao.getCustomer(customerId));
			System.out.println("returns create_customer_success");
			return "create_customer_success";
		}
		CustomMessageUtils.showError("Customer with ID '" + paddedCustomerId() + "' already exists");
		return "create_customer_error";
	}

	public String viewCustomerDetails() {
		setCustomerBean(CustomerDao.getCustomer(customerId));

		return "customer_details";
	}

	public void setCustomer(String padded_customer_number) {
		customerId = Integer.parseInt(padded_customer_number);
		System.out.println("You have set customer to " + customerId);
	}

	public String amendCustomer() throws ParseException {
		CustomerDao.amendCustomer(firstName, middleName, lastName, address, occupation, description, customerId);
		customerList = CustomerDao.getAllCustomers();
		return "amend_customer_success";
	}
}
