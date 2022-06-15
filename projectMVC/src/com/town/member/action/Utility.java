package com.town.member.action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utility {
	// 문자열을 전달받아 암호화하여 반환하는 메소드
	// => 단방향 암호 알고리즘(MD5, SHA-1, SHA-256, SHA-512 등) 사용
	// => MessageDigest 클래스 이용
	public static String encoding(String str){
		String encodeString = "";
		
		try {
			// MessageDigest 객체 생성
			// => 암호화 알고리즘을 선택하여 객체 생성
			MessageDigest md =  MessageDigest.getInstance("SHA-256");
			
			// MessageDigest 객체에 암호화하고자 하는 원본문자열을 update()메소드를 이용하여 전달
			// => String 객체가 아닌 byte 배열 형태로 전달 (문자열객체.getBytes())
			md.update(str.getBytes());
			
			// 원본문자열을 digest() 메소드로 암호화하여 byte배열로 반환받아 저장
			byte[] encodeData = md.digest();
			
			// 암호화된 byte 배열을 String 객체로 변환하여 저장
			for(int i=0; i<encodeData.length; i++){
				// byte 데이터를 16진수 문자열로 변환하여 반환 문자열에 추가(한번 더 암호화)
				encodeString += Integer.toHexString(encodeData[i]&0xFF);
			}
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return encodeString;
	}
	
}
