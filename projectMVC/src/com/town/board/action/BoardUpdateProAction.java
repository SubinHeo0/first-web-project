package com.town.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.town.board.db.BoardDAO;
import com.town.board.db.BoardDTO;

public class BoardUpdateProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : BoardUpdateProAction_execute() 호출 ");
		
		// 전달된 정보 체크(수정할 데이터 num, subject, content, pageNum)
		// => DTO 객체에 저장
		BoardDTO dto = new BoardDTO();
		dto.setNum(Integer.parseInt(request.getParameter("num")));
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		
		String pageNum = request.getParameter("pageNum");
		
		BoardDAO dao = new BoardDAO();
		dao.updateBoard(dto);
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo?pageNum="+pageNum);
		forward.setRedirect(true);
		
		return forward;
	}

}
