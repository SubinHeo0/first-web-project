package com.town.comment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.board.db.BoardDAO;
import com.town.comment.db.CommentDAO;

public class CommentDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : CommentDeleteAction_execute() 호출 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 전달정보 저장(num, b_num)
		int num = Integer.parseInt(request.getParameter("num"));
		int b_num = Integer.parseInt(request.getParameter("b_num"));
		
		// commentDAO -> 댓글삭제동작 수행
		CommentDAO dao = new CommentDAO();
		dao.deleteOneComment(num);
		
		// 해당 댓글의 게시글에서의 c_count 1줄이기
		BoardDAO bdao = new BoardDAO();
		bdao.decreaseCommentCount(b_num);
		
		return null;
	}

}
