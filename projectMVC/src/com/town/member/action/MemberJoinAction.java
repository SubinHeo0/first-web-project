package com.town.member.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class MemberJoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberJoinAction_execuute() 호출 ");
		
		// 전달된 정보 저장(MemberDTO)
		MemberDTO dto = new MemberDTO();
		
		dto.setId(request.getParameter("id"));
//		dto.setPw(request.getParameter("pw"));
		// 비밀번호 암호화
		dto.setPw(Utility.encoding(request.getParameter("pw")));
		
		dto.setName(request.getParameter("name"));
		dto.setNickname(request.getParameter("nickname"));
		dto.setBirth(request.getParameter("birth"));
		dto.setGender(request.getParameter("gender"));
		dto.setTel(request.getParameter("tel"));
//		dto.setEmail(request.getParameter("email"));
		dto.setEmail(request.getParameter("email").length()>0?request.getParameter("email"):null);
		dto.setAddr_num(request.getParameter("addr_num").length()>0?request.getParameter("addr_num"):null);
		dto.setAddr_load(request.getParameter("addr_load").length()>0?request.getParameter("addr_load"):null);
		dto.setAddr(request.getParameter("addr").length()>0?request.getParameter("addr"):null);
		dto.setAddr_detail(request.getParameter("addr_detail").length()>0?request.getParameter("addr_detail"):null);
		dto.setAddr_dong(request.getParameter("addr_dong").length()>0?request.getParameter("addr_dong"):null);
//		dto.setAddr_num(request.getParameter("addr_num"));
//		dto.setAddr_load(request.getParameter("addr_load"));
//		dto.setAddr(request.getParameter("addr"));
//		dto.setAddr_detail(request.getParameter("addr_detail"));
//		dto.setAddr_dong(request.getParameter("addr_dong"));
		dto.setReg_date(new Timestamp(System.currentTimeMillis()));
		
//		System.out.println(" M : "+dto);
		
		// MemberDAO 객체 생성
		MemberDAO dao = new MemberDAO();
		// insertMember() 회원가입 동작 실행
		dao.insertMember(dto);
		
		// 페이지 이동 -> 이동정보를 저장해서 컨트롤러에게 전달
		// 실제로 페이지 이동처리가 일어나는 건 컨트롤러에서
		ActionForward forward = new ActionForward();
		forward.setPath("./MemberLogin.me");
		forward.setRedirect(true);
		
		return forward;
	}

}
