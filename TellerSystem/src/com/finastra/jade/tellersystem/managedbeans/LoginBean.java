package com.finastra.jade.tellersystem.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.finastra.jade.tellersystem.dao.LoginDao;
import com.finastra.jade.tellersystem.util.SessionUtils;

@ManagedBean
@SessionScoped
public class LoginBean {
	private int userId;
	private String username;
	private String password;

	public LoginBean() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		boolean valid = LoginDao.validate(username, password);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", username);
			session.setAttribute("userId", LoginDao.getTellerId(username, password));
			System.out.println(session.getAttribute("userId"));
			return "login";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Incorrect Username and Password", "Please enter correct username and Password"));
			return "logout";
		}
	}

	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "logout";
	}
}
