package com.town.comment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.comment.db.CommentDAO;
import com.town.comment.db.CommentDTO;

public class CommentUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : CommentUpdateAction_execute() 호출 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 전달정보 저장(num, content) -> CommentDTO
		CommentDTO dto = new CommentDTO();
		dto.setNum(Integer.parseInt(request.getParameter("num")));
		dto.setContent(request.getParameter("content"));
		
		// commentDAO -> 댓글수정동작 수행(댓글의 내용만 업데이트)
		CommentDAO dao = new CommentDAO();
		dao.updateComment(dto);
		
		
		return null;
	}

}
