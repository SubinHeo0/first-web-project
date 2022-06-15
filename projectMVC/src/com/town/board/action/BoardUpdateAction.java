package com.town.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.board.db.BoardDAO;
import com.town.board.db.BoardDTO;

public class BoardUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : BoardUpdateAction_execute() 호출 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 전달정보 저장(num, pageNum)
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
//		System.out.println(" M : num : "+num);
//		System.out.println(" M : pageNum : "+pageNum);
		
		// DAO 객체 생성
		BoardDAO dao = new BoardDAO();
		
		// 글 정보 가져오기(getBoard())
		BoardDTO dto = dao.getBoard(num);
		
		// 전달된 글 정보 request 영역에 저장
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
//		System.out.println(" @@@@@@수정할때 가져가는 데이터 M : dto: "+dto);
		
		// 페이지 이동 (./board/updateForm.jsp)
		forward.setPath("./board/updateForm.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
