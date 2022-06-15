package com.town.member.db;

import java.sql.Timestamp;

public class MemberDTO {
	private String id;
	private String pw;
	private String name;
	private String nickname;
	private String birth;
	private String gender;
	private String tel;
	private String email;
	private String addr_num;
	private String addr_load;
	private String addr;
	private String addr_detail;
	private String addr_dong;
	private Timestamp reg_date;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddr_num() {
		return addr_num;
	}
	public void setAddr_num(String addr_num) {
		this.addr_num = addr_num;
	}
	public String getAddr_load() {
		return addr_load;
	}
	public void setAddr_load(String addr_load) {
		this.addr_load = addr_load;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddr_detail() {
		return addr_detail;
	}
	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}
	public String getAddr_dong() {
		return addr_dong;
	}
	public void setAddr_dong(String addr_dong) {
		this.addr_dong = addr_dong;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [id=" + id + ", pw=" + pw + ", name=" + name + ", nickname=" + nickname + ", birth=" + birth
				+ ", gender=" + gender + ", tel=" + tel + ", email=" + email + ", addr_num=" + addr_num + ", addr_load="
				+ addr_load + ", addr=" + addr + ", addr_detail=" + addr_detail + ", addr_dong=" + addr_dong
				+ ", reg_date=" + reg_date + "]";
	}
	
}
