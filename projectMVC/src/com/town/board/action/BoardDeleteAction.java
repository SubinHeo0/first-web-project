package com.town.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.town.board.db.BoardDAO;
import com.town.board.db.BoardDTO;
import com.town.comment.db.CommentDAO;

public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : BoardDeleteAction_execute() 호출 ");
		
		//전달정보 저장(num, pageNum) => DTO
		int num = Integer.parseInt(request.getParameter("num"));
//		BoardDTO dto = new BoardDTO();
//		dto.setNum(Integer.parseInt(request.getParameter("num")));
		String pageNum = request.getParameter("pageNum");
		
		// DAO객체 -> 삭제메서드
		BoardDAO dao = new BoardDAO();
		dao.deleteBoard(num);
		
		// 글삭제 -> 해당글의 댓글도 같이 삭제
		CommentDAO cdao = new CommentDAO();
		cdao.deleteComment(num);
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo?pageNum="+pageNum);
		forward.setRedirect(true);
		
		return forward;
	}

}
