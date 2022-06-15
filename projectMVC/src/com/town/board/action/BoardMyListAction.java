package com.town.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.board.db.BoardDAO;
import com.town.board.db.BoardDTO;

public class BoardMyListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : BoardMyListAction_execute() 호출 ");
		

		
		// 닉네임에 해당하는 게시글을 가져올 것
		HttpSession session = request.getSession();
		String nickname = (String)session.getAttribute("nickname");
		
		// DAO 객체 생성
		BoardDAO dao = new BoardDAO();
		// 검색어(nickname)가 포함된 글이 있는지 체크(글개수 체크)
		int result = dao.getUserBoardCount(nickname);
		
		System.out.println(" M : 검색된 글 개수 "+result);
		
		///////////////////////////////////////////////////////////////////////////////
		// 페이징 처리 1
		// 한 페이지에 보여줄 글의 개수
		int pageSize = 5;
		// 한 페이지 정보 계산하기
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null){
		pageNum = "1";	// pageNum정보가 없을 경우 항상 1페이지
		}
		
		// 페이지 시작행 계산 1, 11, 21, 31, 41, ...
		// -> 한페이지에 10개씩 보여준다 했으므로 1페이지에서는 1번행이 2페이지에서는 11번행이 시작행
		int currentPage = Integer.parseInt(pageNum);
//		System.out.println(" @@@@@M : " +currentPage);
		
		int startRow = (currentPage - 1)*pageSize + 1;
		// 페이지 끝행 계산 10,20,30,40,...
		int endRow = currentPage * pageSize;
		
		///////////////////////////////////////////////////////////////////////////////
		
		// 글이 있을 때, 글정보 전부를 가져오기
		List<BoardDTO> userList = null;
		if(result > 0){
			userList = dao.getUserBoardList(startRow, pageSize, nickname);
		}
//		System.out.println(" @@@@ M : myList : "+userList);

		
		// 페이징 2
		// 전체 필요한 페이지 수 계산
		int pageCount = result / pageSize + (result % pageSize == 0 ? 0 : 1);

		// 한 화면에 보여줄 페이지 블럭의 수
		int pageBlock = 3;

		// 페이지 블럭의 시작번호 1~10=>1 11~20=>11 21~30=>21
		int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;

		// 페이지 블럭의 끝번호 1~10=>10
		int endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}

		
		// request 영역에 글정보(list) 저장
		request.setAttribute("userBoardList", userList);
		
		// request 영역에 페이징처리 정보를 저장
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("result", result);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		// request 영역에 nickname 저장
		request.setAttribute("nickname", nickname);
		
		// 본인의 글이므로 my=1 저장
		request.setAttribute("my", 1);
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./board/userBoardList.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
