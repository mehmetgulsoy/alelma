package com.alelma;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class LoginBean {

	private String username;
	private String password;
	private String password2;

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
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			request.login(this.username, this.password);

			return "/private/UrunKayit.xhtml?faces-redirect=true";
		} catch (ServletException e) {

			// request.isUserInRole(username)
			if (!e.getMessage().equals("This request has already been authenticated")) {

				context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("E-Posta ve/veya şifre hatalı."));

			}
		}
		return "";
	}

	public void logout() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		try {
			request.logout();
		} catch (ServletException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Logout failed."));
		}
	}
}
