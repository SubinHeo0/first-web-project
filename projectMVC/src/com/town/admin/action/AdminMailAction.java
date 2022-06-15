package com.town.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class AdminMailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : AdminMailAction_execute 호출 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String sId = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(sId==null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 전달받은 정보 저장(회원아이디) + pageNum은 바로 request에 저장해서 넘기기
//		String receiver = request.getParameter("email");
		MemberDAO dao = new MemberDAO();
		MemberDTO mDTO = dao.getMember(request.getParameter("id"));
		String receiver = mDTO.getEmail();
		
		// 관리자 이메일 저장
		MemberDTO dto = dao.getMember(sId);
		String sender = dto.getEmail();
		
		// 정보들 request영역에 저장
		request.setAttribute("sender", sender);
		request.setAttribute("receiver", receiver);
		request.setAttribute("pageNum", request.getParameter("pageNum"));

		
		// 페이지 이동 ("./admin/mailForm.jsp")
		forward.setPath("./admin/mailForm.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
