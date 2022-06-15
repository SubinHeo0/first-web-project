package com.town.main.action;

public class ActionForward {
	// 페이지 이동할 때 사용하는 객체
	
	private String path;
	private boolean isRedirect;
	// redirect 방식 : true   주소변환 O , 화면변환 O
	// forward 방식 : false   주소변환 X, 화면변환 O
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	@Override
	public String toString() {
		return "ActionForward [path=" + path + ", isRedirect=" + isRedirect + "]";
	}
	
	
}
