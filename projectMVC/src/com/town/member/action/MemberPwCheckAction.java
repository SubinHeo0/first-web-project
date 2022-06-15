package com.town.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.town.member.db.MemberDAO;

public class MemberPwCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : MemberPwCheckAction_execute() 호출 ");
		
		// 전달정보 pw
		String id = request.getParameter("id");
//		String pw = request.getParameter("pw");
		String pw = Utility.encoding(request.getParameter("pw"));
		
		// MemberDAO 객체 생성
		MemberDAO dao = new MemberDAO();
		// 1: 비밀번호 일치, 0:비밀번호 틀림
		int result = dao.loginCheck(id, pw);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(result == 0){
			out.print("<span style=\"color:red\">");
			out.print("비밀번호를 다시 확인해주세요");
			out.print("<input type=\"hidden\" value=\"0\" id=\"chkPw\" name=\"chkPw\">");
			out.print("</span>");
			out.close();
		} else {
			out.print("<span>");
			out.print("<input type=\"hidden\" value=\"1\" id=\"chkPw\" name=\"chkPw\">");
			out.print("</span>");
			out.close();
		}
		
		
		
		return null;
	}

}
