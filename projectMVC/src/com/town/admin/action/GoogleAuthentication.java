package com.town.admin.action;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GoogleAuthentication extends Authenticator {
	PasswordAuthentication passAuth;
	
	public GoogleAuthentication(){
		// 첫번째 인자 : 구글 아이디
		// 두번째 인자 : 구글 비번
		passAuth = new PasswordAuthentication("anmedi3623", "ycvkumkalmfajken");
	}
	
	public PasswordAuthentication getPasswordAuthentication(){
		return passAuth;
	}
	
}
