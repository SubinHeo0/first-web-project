package com.town.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class MemberInfoProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberInfoProAction_execute() 호출 ");
		
		// 전달받은 정보 저장(아이디, 바뀐비번)
		// 바뀐비번이 있다면 비밀번호는 그걸로 저장
		// => DTO에 저장
		String pw = (request.getParameter("upw") == "") ? request.getParameter("pw") : request.getParameter("upw");
		
		MemberDTO dto = new MemberDTO();
		
		dto.setId(request.getParameter("id"));
		dto.setPw(pw);
		dto.setName(request.getParameter("name"));
		dto.setNickname(request.getParameter("nickname"));
		dto.setBirth(request.getParameter("birth"));
		dto.setGender(request.getParameter("gender"));
		dto.setTel(request.getParameter("tel"));
//		dto.setEmail(request.getParameter("email"));
		dto.setEmail(request.getParameter("email").length()>0?request.getParameter("email"):null);
//		dto.setAddr(request.getParameter("addr"));
		dto.setAddr_num(request.getParameter("addr_num").length()>0?request.getParameter("addr_num"):null);
		dto.setAddr_load(request.getParameter("addr_load").length()>0?request.getParameter("addr_load"):null);
		dto.setAddr(request.getParameter("addr").length()>0?request.getParameter("addr"):null);
		dto.setAddr_detail(request.getParameter("addr_detail").length()>0?request.getParameter("addr_detail"):null);
		dto.setAddr_dong(request.getParameter("addr_dong").length()>0?request.getParameter("addr_dong"):null);
		
		// DAO 객체 생성, updateMember
		MemberDAO dao = new MemberDAO();
		dao.updateMember(dto);
		
		// 세션 닉네임 업데이트
		HttpSession session = request.getSession();
		session.setAttribute("nickname", dto.getNickname());
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./Main.go");
		forward.setRedirect(true);
		
		return forward;
	}

}
