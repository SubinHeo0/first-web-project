package com.town.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.board.db.BoardDAO;
import com.town.comment.db.CommentDAO;
import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class AdminMemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : AdminMemberDeleteAction_execute 호출 ");
		
		// 세션 제어
		HttpSession session = request.getSession();
		String sId = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(sId == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 전달정보 저장(num, pageNum)
		String id = request.getParameter("id");
		String pageNum = request.getParameter("pageNum");
		
		// DAO 객체 -> 삭제메서드
		MemberDAO dao = new MemberDAO();
		dao.deleteMember(id);
		
		// 글 / 댓글 삭제
		String nickname = (String) session.getAttribute("nickname");
		
		BoardDAO bdao = new BoardDAO();
		bdao.deleteBoard(nickname);
		
		CommentDAO cdao = new CommentDAO();
		cdao.deleteComment(nickname);
		
		// 페이지 이동
		forward.setPath("./AdminMemberList.ad?pageNum="+pageNum);
		forward.setRedirect(true);
		
		return forward;
	}

}
