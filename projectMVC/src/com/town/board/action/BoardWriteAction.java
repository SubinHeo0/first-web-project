package com.town.board.action;

import java.util.Spliterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.board.db.BoardDAO;
import com.town.board.db.BoardDTO;
import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class BoardWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : BoardWriteAction_execute()호출 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// -전달된 정보 저장 (DTO)
		BoardDTO dto = new BoardDTO();
		// subject, content, ip, nickname + addr
		// addr : 글 작성 시 addr은 저장, 글 내용 클릭할 때 나타나는 주소는 다시 member에 접속해서 addr 수정
		// 주소는 지번주소만 필요(~~동까지 알고싶기때문)
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		dto.setIp(request.getRemoteAddr());
		dto.setNickname(request.getParameter("nickname"));
		
		
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = new MemberDTO();
		mdto = mdao.getMember(id);
		
//		dto.setAddr(mdto.getAddr());
//		String str = mdto.getAddr();
		
		String addr = null;
		
		if(mdto.getAddr() != null){
			String[] addrArr = mdto.getAddr().split(" ");
			addr = addrArr[0] + " " + addrArr[1] + " " + addrArr[2];
		}
//		System.out.println(" M : addr : "+addr);
		
		dto.setAddr(addr);
		
//		System.out.println(" M : "+dto);
		
		// BoardDAO 객체 생성 - insertBoard()
		BoardDAO dao = new BoardDAO();
		dao.insertBoard(dto);
		
		// 페이지 이동
		forward.setPath("./BoardList.bo");
		forward.setRedirect(true);
		
		return forward;
		
	}

}
