package com.town.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.town.member.db.MemberDAO;

public class MemberTelCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberTelCheckActoin_execute() 호출 ");
		
		// 전달정보 tel
		String tel = request.getParameter("tel");
		
		// MemberDAO 객체 생성
		MemberDAO dao = new MemberDAO();
		
		// 전화번호 중복체크 (1:사용가능 0:전화번호 중복)
		int result = dao.telCheck(tel);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(result == 0){
			// 전화번호 중복
			out.print("<div style=\"color:red\">");
			out.print("이미 존재하는 전화번호입니다");
			out.print("<input type=\"hidden\" value=\"0\" id=\"chkTel\" name=\"chkTel\">");
			out.print("</div>");
			out.close();
		} else {
			out.print("<div>");
			out.print("<input type=\"hidden\" value=\"1\" id=\"chkTel\" name=\"chkTel\">");
			out.print("</div>");
			out.close();
		}
		
		return null;
	}

}
