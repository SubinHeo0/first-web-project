package com.town.comment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.board.db.BoardDAO;
import com.town.comment.db.CommentDAO;
import com.town.comment.db.CommentDTO;
import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class CommentReWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : CommentReWriteAction_execute() 호출 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 전달정보 저장(num, content) -> CommentDTO
		int num = Integer.parseInt(request.getParameter("num"));
		
		CommentDTO dto = new CommentDTO();
		dto.setNum(num);
		dto.setContent(request.getParameter("content"));
		
		
		// 댓글번호에 해당하는 게시글번호, re_ref, re_lev, re_seq
		// + nickname, addr, ip 저장
		CommentDAO dao = new CommentDAO();
		// 댓글번호에 해당하는 댓글가져와야함(그댓글의 글번호, re_.....를 알아야하기 때문)
		dto.setB_num(dao.getComment(num).getB_num());
		dto.setRe_lev(dao.getComment(num).getRe_lev());
		dto.setRe_ref(dao.getComment(num).getRe_ref());
		dto.setRe_seq(dao.getComment(num).getRe_seq());
		
		dto.setNickname((String)session.getAttribute("nickname")); 
		
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
		
		dao.reInsertComment(dto);
		
		// 해당게시글의 댓글 c_count +1
		BoardDAO bdao = new BoardDAO();
		bdao.updateCommentCount(dao.getComment(num).getB_num());
		
		return null;
	}

}
