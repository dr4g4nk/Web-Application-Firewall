package org.unibl.etf.dto;

import java.io.Serializable;

public class DTOUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5454753602357552537L;
	private String username, password;
	private int role;

	

	public DTOUser(String username, String password, int role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getRole() {
		return role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "DTOUser [username=" + username + ", password=" + password + ", role="
				+ role + "]";
	}

}