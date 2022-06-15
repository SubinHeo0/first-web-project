package com.town.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class MemberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberInfoAction_execute() 호출 ");
		
		// 세션에 저장된 아이디를 가지고 디비에서 아이디에 해당하는 정보가져와서 출력
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
//		System.out.println(" M : sessionID : "+id);
		
		// 세션에 저장된 아이디가 없으면 로그인페이지로 이동
		ActionForward forward = new ActionForward();
		
		if(id == null){
			System.out.println(" M : sessionID가 null입니다");
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			
			return forward;
		}
		
		// 아이디에 해당하는 정보가져오기 => DTO에 저장
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(id);
		
		// request 영역에 저장
		request.setAttribute("dto", dto);
		
		// 페이지 이동 (./member/update.jsp)
		forward.setPath("./member/update.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
