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

@SessionScoped
@ManagedBean
public class CustomerBean {
	private List<Customer> customerList;
	private int customerId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String address;
	private String occupation;
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

	public String createCustomer() throws ParseException {

		if (!CustomerDao.exists(customerId)) {
			if (CustomerDao.insertCustomer(customerId, firstName, middleName, lastName, address, occupation,
					description, dateJoined)) {
				customerList = CustomerDao.getAllCustomers();
				System.out.println("returns create_customer_success");
				return "create_customer_success";
			}
		} else if (customerId == 0) {
			
		}
		showDuplicateError(customerId);
		return "create_customer_error";
	}

	public void showDuplicateError(int customerId) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Customer with ID '" + paddedCustomerId() + "' already exists", "Error!"));
	}

	public String viewCustomerDetails(String padded_customer_number) {
		int customer_number = Integer.parseInt(padded_customer_number);

		setCustomerBean(CustomerDao.getCustomer(customer_number));

		return "customer_details";
	}

	private void setCustomerBean(Customer customer) {
		customerId = Integer.parseInt(customer.getCustomer_id());
		firstName = customer.getCustomer_first_name();
		middleName = customer.getCustomer_middle_name();
		lastName = customer.getCustomer_last_name();
		address = customer.getCustomer_address();
		occupation = customer.getCustomer_occupation();
		description = customer.getCustomer_description();
		dateJoined = customer.getCustomer_date_joined();
	}

	public String amendCustomer() throws ParseException {
		CustomerDao.amendCustomer(firstName, middleName, lastName, address, occupation, description, customerId);
		return "amend_customer_success";
	}
}
