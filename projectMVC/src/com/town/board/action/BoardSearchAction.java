package com.town.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.town.board.db.BoardDAO;
import com.town.board.db.BoardDTO;

public class BoardSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : BoardSearchAction_execute() 호출 ");
		
		// 전달된 정보 저장(search)
		String search = request.getParameter("search");
		System.out.println(" M : 검색어 : "+search);
		
		// DAO 객체 생성
		BoardDAO dao = new BoardDAO();
		// 검색어가 포함된 글이 있는지 체크(글개수 체크)
		int searchCnt = dao.getBoardCount(search);
		
		System.out.println(" M : 검색된 글 개수 "+searchCnt);
		
		///////////////////////////////////////////////////////////////////////////////
		// 페이징 처리 1
		// 한 페이지에 보여줄 글의 개수
		int pageSize = 5;
		// 한 페이지 정보 계산하기
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null){
			pageNum = "1";	// pageNum정보가 없을 경우 항상 1페이지
		}
		
		int currentPage = Integer.parseInt(pageNum);
		
		int startRow = (currentPage - 1)*pageSize + 1;
		int endRow = currentPage * pageSize;
		
		///////////////////////////////////////////////////////////////////////////////
		
		List<BoardDTO> searchList = null;
		if(searchCnt > 0){
			// 검색어가 포함된 제목/내용/주소가 있을 때
			// => 해당 내용만 디비에서 저장해서 가녀오기
			searchList = dao.getBoardList(startRow, pageSize, search);
		} else {
			// 검색어가 포함된 제목이 없을 때
			// => 일반글 전체 가져오기 / null 처리
			searchList = dao.getBoardList(startRow, pageSize);
		}
		
		// 페이징 2
		// 전체 필요한 페이지 수 계산
		int pageCount = searchCnt / pageSize + (searchCnt % pageSize == 0 ? 0 : 1);

		// 한 화면에 보여줄 페이지 블럭의 수
		int pageBlock = 3;

		// 페이지 블럭의 시작번호
		int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;

		// 페이지 블럭의 끝번호
		int endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		// DB에서 전달받은 데이터를 request 영역에 저장
		request.setAttribute("searchList", searchList);
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("result", searchCnt);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("search", search);
		
		
		// 페이지 이동(화면에 뿌리기)
		ActionForward forward = new ActionForward();
		forward.setPath("./board/boardSearchList.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
