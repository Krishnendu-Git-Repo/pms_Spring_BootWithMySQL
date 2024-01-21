package com.project.projectManagementSystem.response;

public class GlobalResponse {

	private String statusCode;
    private String message;
    private Object response;
	
    public GlobalResponse() {
		super();
	}
	
    public GlobalResponse(String statusCode, String message, Object response) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.response = response;
	}
	
    public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	
	@Override
	public String toString() {
		return "GlobalResponse [statusCode=" + statusCode + ", message=" + message + ", response=" + response + "]";
	}
    
    
}
