package com.finastra.jade.tellersystem.filter;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest reqt, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("doFilter function");
		try {

			HttpServletRequest request = (HttpServletRequest) reqt;
			HttpServletResponse response = (HttpServletResponse) res;
			HttpSession session = request.getSession(false);

			String loginURL = request.getContextPath() + "/index.xhtml";
//			System.out.println("loginURL: " + loginURL);

			boolean loggedIn = (session != null) && (session.getAttribute("username") != null);
			boolean loginRequest = request.getRequestURI().equals(loginURL);
			boolean resourceRequest = request.getRequestURI()
					.startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");

			if (loggedIn || loginRequest || resourceRequest) {
//				System.out.println("I am logged in. 45");
				if (!resourceRequest) { // Prevent browser from caching restricted resources. See also
										// https://stackoverflow.com/q/4194207/157882
//					System.out.println("I am logged in. 48");
					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
					response.setDateHeader("Expires", 0); // Proxies.
				}
					
				chain.doFilter(request, response);
			} else {
				System.out.println("I am not logged in. 55");
				response.sendRedirect(loginURL);
			}
		} catch (ServletException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void destroy() {

	}
}