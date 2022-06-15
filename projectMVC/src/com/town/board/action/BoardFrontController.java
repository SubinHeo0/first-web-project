package com.town.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BoardFrontController extends HttpServlet {
	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" BoardFrontController - doProcess() 호출");
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
		
		if(command.equals("/BoardWrite.bo")){
			System.out.println(" C : /BoardWrite.bo 호출 ");
			System.out.println(" C : DB사용X 정보입력페이지(view)");
			
			// 페이지이동 객체 생성
			forward = new ActionForward();
			forward.setPath("./board/boardWrite.jsp");
			forward.setRedirect(false);		
		}
		else if(command.equals("/BoardWriteAction.bo")){
			System.out.println(" C : /BoardWriteAction.bo 호출 ");
			// DB사용O, 페이지 이동
			
			action = new BoardWriteAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/BoardList.bo")){
			System.out.println(" C : /BoardList.bo 호출 ");
			// DB사용O, 해당 페이지에 출력
			
			action = new BoardListAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/BoardSearch.bo")){
			System.out.println(" C : /BoardSearch.bo 호출 ");
			// DB사용O, 페이지에 출력
			
			action = new BoardSearchAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/BoardContent.bo")){
			System.out.println(" C : /BoardContent.bo 호출 ");
			// DB사용, 페이지 이동
			
			action = new BoardContentAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/BoardUpdate.bo")){
			System.out.println(" C : /BoardUpdate.bo 호출 ");
			// DB정보를 가져와서 화면에 출력
			
			action = new BoardUpdateAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/BoardUpdateProAction.bo")){
			System.out.println(" C : /BoardUpdateProAction.bo 호출 ");
			// DB사용, 페이지이동
			
			action = new BoardUpdateProAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/BoardDelete.bo")){
			System.out.println(" C : /BoardDelete.bo 호출 ");
			// DB사용, 페이지이동
			
			action = new BoardDeleteAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/BoardReWrite.bo")){
			System.out.println(" C : /BoardReWrite.bo 호출 ");
			// DB사용X, 화면출력
			
			forward = new ActionForward();
			forward.setPath("./board/boardReWrite.jsp");
			forward.setRedirect(false);
		}
		else if(command.equals("/BoardReWriteAction.bo")){
			System.out.println(" C : /BoardReWriteAction.bo 호출 ");
			// DB사용, 페이지이동
			
			action = new BoardReWriteAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/BoardFileUpload.bo")){
			System.out.println(" C : /BoardFileUpload.bo 호출 ");
			// DB사용X, view페이지 이동
			
			forward = new ActionForward();
			forward.setPath("./board/boardUpload.jsp");
			forward.setRedirect(false);
		}
		else if(command.equals("/BoardFileUploadAction.bo")){
			System.out.println(" C : /BoardFileUploadAction.bo 호출 ");
			// DB사용, 페이지이동
			
			action = new BoardFileUploadAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/BoardMyList.bo")){
			System.out.println(" C : /BoardMyList.bo 호출 ");
			// DB사용, 페이지 이동
			
			action = new BoardMyListAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/BoardUserList.bo")){
			System.out.println(" C : /BoardUserList.bo 호출 ");
			// DB사용, 페이지 이동
			
			action = new BoardUserListAction();
			
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
