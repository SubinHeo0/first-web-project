package com.town.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.board.db.BoardDAO;
import com.town.board.db.BoardDTO;
import com.town.comment.db.CommentDAO;
import com.town.comment.db.CommentDTO;
import com.town.member.db.MemberDAO;

public class BoardContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" M : BoardContentAction_execute() 호출 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 전달된 데이터 저장(num, pageNum)
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		int my = Integer.parseInt(request.getParameter("my"));
		
		// BoardDAO 객체 생성
		BoardDAO dao = new BoardDAO();
		
		// 조회수 1증가
		dao.updateReadCount(num);
		System.out.println(" M : 조회수 1증가 완료 ");
		
		// 글 번호에 해당하는 글 전체의 정보를 가져오기
		BoardDTO dto =  dao.getBoard(num);
		System.out.println(" M : 글정보 1개 조회 완료");
		
		// 해당글의 댓글이 있는지 체크
		CommentDAO cdao = new CommentDAO();
		int commentCnt = cdao.getCommentCount(num);
		// 글 번호에 해당하는 댓글 전체의 정보(댓글목록-list)를 가져오기 (페이징처리x)
		List<CommentDTO> commentList = null;
		if(commentCnt > 0){
			commentList = cdao.getCommentList(num);
		}
		
		cdao.getCommentList(num);

		// request 영역에 글정보를 저장
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("my", my);
		request.setAttribute("commentList", commentList);
		
		// 페이지 이동(./board/content.jsp)
		forward.setPath("./board/content.jsp");
		forward.setRedirect(false);
		
		return forward;
		
	}

}
