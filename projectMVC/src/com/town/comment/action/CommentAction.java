package com.town.comment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.board.db.BoardDAO;
import com.town.comment.db.CommentDAO;
import com.town.comment.db.CommentDTO;
import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class CommentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" M : CommentAction_execute() 실행 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 전달정보 저장(b_num, comment) => dto
		// 세션닉네임도 추가로 저장
		CommentDTO dto = new CommentDTO();
		dto.setB_num(Integer.parseInt(request.getParameter("b_num")));
		dto.setContent( request.getParameter("comment"));
		dto.setNickname((String)session.getAttribute("nickname"));
		
		// 저장할 수 있는 정보 dto에 저장(addr,ip)
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = new MemberDTO();
		mdto = mdao.getMember(id);
		
		String addr = null;
		
		if(mdto.getAddr() != null){
			String[] addrArr = mdto.getAddr().split(" ");
			addr = addrArr[0] + " " + addrArr[1] + " " + addrArr[2];
		}
		
		dto.setAddr(addr);
		dto.setIp(request.getRemoteAddr());
		
		// CommentDAO 객체 생성
		CommentDAO dao = new CommentDAO();
		dao.insertComment(dto);
		
		BoardDAO bdao = new BoardDAO();
		bdao.updateCommentCount(Integer.parseInt(request.getParameter("b_num")));

		
		return null;
	}

}
