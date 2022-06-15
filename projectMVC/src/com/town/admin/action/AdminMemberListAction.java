package com.town.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.town.member.db.MemberDAO;
import com.town.member.db.MemberDTO;

public class AdminMemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : AdminMemberListAction_execute 호출 ");
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 전달정보 X
		
		// 회원 전체 정보 가져오기 & 페이징처리
		MemberDAO dao = new MemberDAO();
		// 회원수 확인 - getMemberCount();
		int result = dao.getMemberCount();
		System.out.println(" M : 회원수 "+result+"명");
		
		///////////////////////////////////////////////////////////////////////////////
		// 페이징 처리 1
		// 한 페이지에 보여줄 글의 개수
		int pageSize = 10;
		// 한 페이지 정보 계산하기
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
		pageNum = "1"; // pageNum정보가 없을 경우 항상 1페이지
		}
		
		// 페이지 시작행 계산 1, 11, 21, 31, 41, ...
		// -> 한페이지에 10개씩 보여준다 했으므로 1페이지에서는 1번행이 2페이지에서는 11번행이 시작행
		int currentPage = Integer.parseInt(pageNum);
		
		int startRow = (currentPage - 1) * pageSize + 1;
		// 페이지 끝행 계산 10,20,30,40,...
		int endRow = currentPage * pageSize;
		
		///////////////////////////////////////////////////////////////////////////////
		
		// 회원이 있을 때, 회원 정보 전부 가져오기
		List<MemberDTO> memberList = null;
		if(result > 0){
			memberList = dao.getMemberList(startRow, pageSize);
		}
		
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
		
		// request 영역에 정보 저장
		request.setAttribute("memberList", memberList);
		
		// request 영역에 페이징처리 정보를 저장
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("result", result);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		// 페이지 이동
		forward.setPath("./admin/memberList.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
