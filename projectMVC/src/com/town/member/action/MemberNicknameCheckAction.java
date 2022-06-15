package com.town.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.town.member.db.MemberDAO;

public class MemberNicknameCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : MemberNicknameCheckAction_execute() 호출 ");
		
		// 전달정보 nickname
		String nickname = request.getParameter("nickname");
		
		// MemberDAO 객체 생성
		MemberDAO dao = new MemberDAO();
		
		// 닉네임 중복체크 (1:사용가능 0:중복)
		int result = dao.nicknameCheck(nickname);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(nickname.length() > 0){
			if(result == 0){
				// 닉네임 중복
				out.print("<div style=\"color:red\">");
				out.print("이미 존재하는 닉네임입니다");
				out.print("<input type=\"hidden\" value=\"0\" id=\"chkNickname\" name=\"chkNickname\">");
				out.print("</div>");
				out.close();
			} else {
				out.print("<div style=\"color:green\">");
				out.print("사용가능한 닉네임입니다");
				out.print("<input type=\"hidden\" value=\"1\" id=\"chkNickname\" name=\"chkNickname\">");
				out.print("</div>");
				out.close();
			}
		} else {
			out.print("<div style=\"color:red\">");
			out.print("한글자이상 입력해주세요");
			out.print("<input type=\"hidden\" value=\"-1\" id=\"chkNickname\" name=\"chkNickname\">");
			out.print("</div>");
			out.close();
		}
		
		return null;
	}

}
