package com.town.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.impl.protocol.BootstrapServerRequestDispatcher;
import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class MemberIdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberIdCheckAction_execute() 호출 ");
		
		// 전달정보 id
		String id = request.getParameter("id");
		
		// MemberDAO 객체 생성
		MemberDAO dao = new MemberDAO();
		
		// 아이디 중복체크 (1:사용가능 0:아이디중복)
		int result = dao.idCheck(id);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// 5자 ~ 20자
		if(id.length() > 4){
			if(result == 0){ // 아이디 중복
				out.print("<div style=\"color:red\">");
				out.print("아이디가 존재합니다");
				out.print("<input type=\"hidden\" value=\"0\" id=\"chkId\" name=\"chkId\">");
//				out.print("<script>");
//				out.print("alert(document.registerform.chkId.value);");
//				out.print("</script>");
				out.print("</div>");
				out.close();
			} else {
				out.print("<div style=\"color:green\">");
				out.print("사용가능한 아이디입니다");
				out.print("<input type=\"hidden\" value=\"1\" id=\"chkId\" name=\"chkId\">");
//				out.print("<script>");
//				out.print("alert(document.registerform.chkId.value);");
//				out.print("</script>");
				out.print("</div>");
				out.close();
			}
		} else {
			out.print("<div style=\"color:red\">");
			out.print("5~20자로 입력해주세요");
			out.print("<input type=\"hidden\" value=\"-1\" id=\"chkId\" name=\"chkId\">");
//			out.print("<script>");
//			out.print("alert(document.registerform.chkId.value);");
//			out.print("</script>");
			out.print("</div>");
			out.close();
		}
		
	
		return null;
	}

}
