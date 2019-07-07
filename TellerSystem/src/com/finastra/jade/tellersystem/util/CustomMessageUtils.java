package com.finastra.jade.tellersystem.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class CustomMessageUtils {

	public static void showError(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "Error!"));
	}

	public static void showWarning(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, "Warning!"));

	}
}
