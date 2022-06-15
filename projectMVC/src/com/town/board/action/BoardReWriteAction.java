package com.town.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.board.db.BoardDAO;
import com.town.board.db.BoardDTO;
import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class BoardReWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" M : BoardReWriteAction_execute()호출 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 전달된 정보 저장 (DTO)
		// num, re_ref, re_lev, re_seq, subject, content
		// + 세션의 nickname도 저장
		BoardDTO dto = new BoardDTO();
		dto.setNum(Integer.parseInt(request.getParameter("num")));
		dto.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
		dto.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
		dto.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		
		dto.setNickname((String)session.getAttribute("nickname"));
		
		dto.setIp(request.getRemoteAddr());
		
		// addr
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = new MemberDTO();
		mdto = mdao.getMember(id);
		
		String addr = null;
		
		if(mdto.getAddr() != null){
			String[] addrArr = mdto.getAddr().split(" ");
			addr = addrArr[0] + " " + addrArr[1] + " " + addrArr[2];
		}
		
		dto.setAddr(addr);
		
		// DAO 객체 생성 - reInsertBoard()
		BoardDAO dao = new BoardDAO();
		dao.reInsertBoard(dto);
		
		// 페이지 이동
		forward.setPath("./BoardList.bo");
		forward.setRedirect(true);
		
		return forward;
	}

}
