package com.town.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class MemberLoginAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberLoginAction_execute() 호출 ");
		
		// 한글처리(생략)
		// 전달정보
		String id = request.getParameter("id");
//		String pw = request.getParameter("pw");
		String pw = Utility.encoding(request.getParameter("pw"));
		
		// DAO객체생성
		MemberDAO dao = new MemberDAO();
		
		// 로그인메서드
		// 처리 결과 저장
		int result = dao.loginCheck(id,pw);
		System.out.println(" M : result : "+result);
		
		// 처리결과에 따른 페이지 이동(js)
		// => Action 페이지에서 js 이동시에는 컨트롤러를 통한 페이지 이동 X
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(result == 0){ // 비밀번호 오류
			out.print("<script>");
			out.print(" alert('비밀번호가 틀렸습니다'); ");
			out.print(" history.back(); ");
			out.print("</script>");
			
			out.close();
			
			return null;
		} else if(result == -1){
			out.print("<script>");
			out.print(" alert('회원이 아닙니다'); ");
			out.print(" location.href='./MemberJoin.me'; ");
			out.print("</script>");
			
			out.close();
			
			return null;
		}
		
		// result == 1 로그인 성공 (아이디값에 해당하는 닉네임정보도 세션에 저장)
//		System.out.println(" M : 로그인성공");
		HttpSession session = request.getSession();
		session.setAttribute("id", id);
//		System.out.println(" M : 세션아이디 : "+session.getAttribute("id"));
		MemberDTO dto = dao.getMember(id);
		session.setAttribute("nickname", dto.getNickname());
//		System.out.println(" M : 세션사용자이름 : "+session.getAttribute("name"));
		
		out.print("<script>");
		out.print(" location.href='./Main.go'; ");
		out.print("</script>");
		
		out.close();
		
		return null;
	}

}
