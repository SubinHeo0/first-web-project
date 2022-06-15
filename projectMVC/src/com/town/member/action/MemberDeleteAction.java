package com.town.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.board.db.BoardDAO;
import com.town.comment.db.CommentDAO;
import com.town.member.db.MemberDAO;

public class MemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberDeleteAction_execute() 호출 ");
		
		String id = request.getParameter("id");
		
		MemberDAO dao = new MemberDAO();
		dao.deleteMember(id);
		
		// 해당 아이디가 작성한 글/댓글 모두 삭제
		// boardDTO/commentDTO 에는 아이디가 없고 닉네임만 존재
		HttpSession session = request.getSession();
		String nickname = (String) session.getAttribute("nickname");
		
		BoardDAO bdao = new BoardDAO();
		bdao.deleteBoard(nickname);
		
		CommentDAO cdao = new CommentDAO();
		cdao.deleteComment(nickname);
		
		// 세션 초기화
		session.invalidate();
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./member/delete.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
