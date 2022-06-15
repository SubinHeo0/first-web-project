package com.town.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.town.member.db.MemberDAO;

public class MemberEmailCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : MemberEmailCheckAction_execute() 호출 ");
		
		// 전달정보 email
		String email = request.getParameter("email");
//		boolean emailCheck = email=="";
//		System.out.println(" M : email : "+emailCheck);
		
		// 결과가 1일 때만 submit을 막을 것
		// 결과가 0이든 값이 없든 그건 submit
		// 넘겨받은 이메일이 없을 땐 dao 메서드 수행안하게
		int result = 1;
		
		if(email != ""){
			// MemberDAO 객체 생성
			MemberDAO dao = new MemberDAO();
			
			// 이메일 중복체크 (1:사용가능 0:이메일중복)
			result = dao.emailCheck(email);
			
		}
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(result == 0){
			out.print("<div style=\"color:red\">");
			out.print("이미 존재하는 이메일입니다");
			out.print("<input type=\"hidden\" value=\"0\" id=\"chkEmail\" name=\"chkEmail\">");
			out.print("</div>");
			out.close();
		} else {
			out.print("<div>");
			out.print("<input type=\"hidden\" value=\"1\" id=\"chkEmail\" name=\"chkEmail\">");
			out.print("</div>");
			out.close();
		}
		
		return null;
	}

}
