package com.town.comment.db;

import java.sql.Date;

public class CommentDTO {
	private int num;
	private int b_num;
	private String nickname;
	private String addr;
	private String content;
	private int re_ref;
	private int re_lev;
	private int re_seq;
	private Date date;
	private String ip;
	
	// alt+shift+s + r
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getB_num() {
		return b_num;
	}
	public void setB_num(int b_num) {
		this.b_num = b_num;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRe_ref() {
		return re_ref;
	}
	public void setRe_ref(int re_ref) {
		this.re_ref = re_ref;
	}
	public int getRe_lev() {
		return re_lev;
	}
	public void setRe_lev(int re_lev) {
		this.re_lev = re_lev;
	}
	public int getRe_seq() {
		return re_seq;
	}
	public void setRe_seq(int re_seq) {
		this.re_seq = re_seq;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	// alt+shift+s + s
	@Override
	public String toString() {
		return "CommentDTO [num=" + num + ", b_num=" + b_num + ", nickname=" + nickname + ", addr=" + addr
				+ ", content=" + content + ", re_ref=" + re_ref + ", re_lev=" + re_lev + ", re_seq=" + re_seq
				+ ", date=" + date + ", ip=" + ip + "]";
	}
	
	
}
