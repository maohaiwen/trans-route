package com.trans.route.util.common;

public class CommonResp {

	private int code = 200;
	
	private String message;
	
	private Object data;
	
	public CommonResp() {
		
	}
	
	public CommonResp(Object t) {
		this.data = t;
	}
	
	public CommonResp(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public CommonResp(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public CommonResp(int code) {
		this.code = code;
	}
	
	public CommonResp(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
