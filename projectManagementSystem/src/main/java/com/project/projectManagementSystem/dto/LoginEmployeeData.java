package com.project.projectManagementSystem.dto;

import org.springframework.security.core.userdetails.UserDetails;

import com.project.projectManagementSystem.model.Employee;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class LoginEmployeeData implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private String token;
	private Long id;
	private String email;
	private String password;
	private String message;
	private List<GrantedAuthority> role;
	private String authority;
	private int status;
	
	public LoginEmployeeData() {
		super();
	}
	
	public LoginEmployeeData(Employee employee) {
		this.email = employee.getEmail();
		this.password = employee.getPassword();
		this.role = Stream.of(employee.getDepartment()).map(SimpleGrantedAuthority::new).collect(Collectors.toList())  ;		
	}
	
	public LoginEmployeeData(String token, Long id, String email, String password, String message,
			List<GrantedAuthority> role, String authority, int status) {
		super();
		this.token = token;
		this.id = id;
		this.email = email;
		this.password = password;
		this.message = message;
		this.role = role;
		this.authority = authority;
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<GrantedAuthority> getRole() {
		return role;
	}

	public void setRole(List<GrantedAuthority> role) {
		this.role = role;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "LoginEmployeeData [token=" + token + ", id=" + id + ", email=" + email + ", password=" + password
				+ ", message=" + message + ", role=" + role + ", authority=" + authority + ", status=" + status + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
