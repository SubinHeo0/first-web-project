package com.town.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberFrontController extends HttpServlet{
	
	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" MemberFrontController - doProcess() 호출");
		System.out.println(" GET/POST방식 모두 처리!! \n\n"); 
		
		////////////////////////1. 가상 주소 계산/////////////////////////////////////
		System.out.println(" C : 1. 가상 주소 계산 시작");
		
		// 가상주소 가져오기
		String requestURI = request.getRequestURI();
		System.out.println(" C : requestURI- "+requestURI);
		// 프로젝트명 가져오기
		String ctxPath = request.getContextPath();
		System.out.println(" C : ctxPath - "+ctxPath);
		// 가상주소 계산 (가상주소 - 프로젝트명)
		String command = requestURI.substring(ctxPath.length());
		System.out.println(" C : command - "+command);
		
		
		System.out.println(" C : 1. 가상 주소 계산 끝\n");
		////////////////////////1. 가상 주소 계산/////////////////////////////////////
		
		////////////////////////2. 가상 주소 매핑/////////////////////////////////////
		System.out.println(" C : 2. 가상 주소 매핑 시작 ");
		Action action = null;
		ActionForward forward = null;
		
		if(command.equals("/MemberJoin.me")){
			System.out.println(" C : /MemberJoin.me 호출 ");
			System.out.println(" C : DB사용X 정보입력페이지(view)");
			
			// 페이지 이동 객체 생성
			forward = new ActionForward();
			forward.setPath("./member/join.jsp");
			forward.setRedirect(false);
		}
		else if(command.equals("/MemberIdCheckAction.me")){
			System.out.println(" C : /MemberIdCheckAction.me 호출");
			System.out.println(" C : DB사용O, 해당페이지에 출력");
			
			// MemberIdCheckAction 객체 생성
			action = new MemberIdCheckAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberNicknameCheckAction.me")){
			System.out.println(" C : /MemberNicknameCheckAction.me 호출 ");
			System.out.println(" C : DB사용O, 해당페이지에 출력 ");
			
			// MemberNicknameCheckAction 객체 생성
			action = new MemberNicknameCheckAction();
			
			try {
				action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberTelCheckAction.me")){
			System.out.println(" C : /MemberTelCheckAction.me 호출 ");
			System.out.println(" C : DB사용O, 해당페이지에 출력");
			
			// MemberTelCheckAction 객체 생성
			action = new MemberTelCheckAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberEmailCheckAction.me")){
			System.out.println(" C : /MemberEmailCheckAction.me 호출 ");
			System.out.println(" C : DB사용O, 해당페이지에 출력");
			
			// MemberEmailCheckAction 객체 생성
			action = new MemberEmailCheckAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/MemberJoinAction.me")){
			System.out.println(" C : /MemberJoinAction.me 호출");
			System.out.println(" C : DB사용O, 페이지 이동");
			
			// JoinAction 객체 생성
			action = new MemberJoinAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberLogin.me")){
			System.out.println(" C : /MemberLogin.me 호출");
			System.out.println(" C : DB사용X, 정보입력페이지(view)");
			
			// 페이지 이동 객체 생성
			forward = new ActionForward();
			forward.setPath("./member/login.jsp");
			forward.setRedirect(false);
		}
		else if(command.equals("/MemberLoginAction.me")){
			System.out.println(" C : /MemberLoginAction.me 호출 ");
			System.out.println(" C : DB사용O, 페이지 이동");
			
			// MemberLoginAction 객체 생성
			action = new MemberLoginAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberLogout.me")){
			System.out.println(" C : /MemberLogout.me 호출 ");
			System.out.println(" C : DB사용X, view페이지이동");
			
			forward = new ActionForward();
			forward.setPath("./member/logout.jsp");
			forward.setRedirect(false);
		}
		else if(command.equals("/MemberInfo.me")){
			System.out.println(" C : /MemberInfo.me 호출 ");
			System.out.println(" C : DB사용O, view페이지 출력");
			
			action = new MemberInfoAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberInfoProAction.me")){
			System.out.println(" C : /MemberInfoProAction.me 호출 ");
			System.out.println(" C : DB사용O, 페이지이동");
			
			action = new MemberInfoProAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberPwCheckAction.me")){
			System.out.println(" C : /MemberPwCheckAction.me 호출 ");
			System.out.println(" C : DB사용O, 해당페이지에 출력");
			
			action = new MemberPwCheckAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberDelete.me")){
			System.out.println(" C : /MemberDelete.me 호출 ");
			System.out.println(" C : DB사용O, 페이지이동");
			
			action = new MemberDeleteAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		System.out.println(" C : 2. 가상 주소 매핑 끝\n ");
		////////////////////////2. 가상 주소 매핑/////////////////////////////////////
		
		////////////////////////3. 페이지 이동/////////////////////////////////////
		System.out.println(" C : 3. 페이지 이동 시작");
		if(forward != null){
			
			if(forward.isRedirect()){
				System.out.println(" C : redirect방식, "+forward.getPath()+"로 이동");
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dis = 
						request.getRequestDispatcher(forward.getPath());
				
				System.out.println(" C : forward방식, "+forward.getPath()+"로 이동");
				dis.forward(request, response);
			}
			
		}
		System.out.println(" C : 3. 페이지 이동 끝\n ");
		////////////////////////3. 페이지 이동/////////////////////////////////////
		
		
		
		
	}//doProcess
	
	

	@Override
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" BoardFrontController - doGET() 호출");
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" BoardFrontController - doPOST() 호출");
		doProcess(request, response);
	}
	
	
}
