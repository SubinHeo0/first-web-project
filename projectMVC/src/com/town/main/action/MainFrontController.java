package com.town.main.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainFrontController extends HttpServlet {
	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" MainFrontController - doProcess() 호출");
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
		
		if(command.equals("/Main.go")){
			System.out.println(" C : /Main.go 호출 ");
			// DB연결X, view페이지
			
			forward = new ActionForward();
			forward.setPath("./main/main.jsp");
			forward.setRedirect(false);
		}
//		else if(command.equals("/Map.go")){
//			System.out.println(" C : /Map.go 호출");
//			// DB사용X, view 페이지
//			
//			forward = new ActionForward();
//			forward.setPath("./main/map_ex.jsp");
//			forward.setRedirect(false);
//		}
		
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
