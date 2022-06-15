<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.FileInputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>fileDown.jsp</h1>
	
	<h2> 파일 다운로드 </h2>
	
	<%
	 // 다운로드할 파일명
	 String fileName = request.getParameter("fileName");
	
	 // 파일을 업로드한 곳의 주소
	 String savePath = "upload";
	 
	 // 업로드된 폴더접근
	 ServletContext ctx = getServletContext();
	 String downLoadPath = ctx.getRealPath(savePath);
	 
	 System.out.println(downLoadPath);
	 
	 // 내가 다운로드할 파일의 전체 경로
	 String downFilepath = downLoadPath + "\\" + fileName;
	 
	 System.out.println("다운로드 파일 전체 경로 : " +downFilepath);
	 
	 ///////////////////////////////////////////////////////////////////////////
	 // 파일의 데이터를  담아서 저장하는 배열
	 byte[] b = new byte[4096];	// 4 * 1024 = 4KB
	 
	 // 파일 입력스트림 객체
	 FileInputStream fis = new FileInputStream(downFilepath);
	 
	 // 다운로드할 파일의 MIME타입을 확인
	 // MIME 타입이란 클라이언트에게 전송된 문서의 다양성을 알려주기 위한 메커니즘
	 // 웹에서 파일의 확장자는 별 의미가 없습니다.
	 // 그러므로, 각 문서와 함께 올바른 MIME 타입을 전송하도록,
	 // 서버가 정확히 설정하는 것이 중요합니다.
	 // 브라우저들은 리소스를 내려받았을 때 해야 할 기본 동작이
	 // 무엇인지를 결정하기 위해 대게 MIME 타입을 사용합니다.
	 
	 String MimeType = ctx.getMimeType(downFilepath);
	 System.out.println("MimeType : "+MimeType);
	 
	 // MIME타입이 없을 경우
	 if(MimeType == null){
		 MimeType = "application/octect-stream";
		 // 이진파일의 기본값 (알려지지 않은 파일의 형태)
	 }
	 
	 // 응답할 데이터의 MIME 타입으로 JSP페이지 내용의 형태를 변경
	 response.setContentType(MimeType);
	 
	 ///////////////////////////////////////////////////////////////////////
	 
	 // 브라우저별 인코딩 /인터넷 익스플로어(ie)
	 
	 String agent = request.getHeader("User-Agent");
	 // User-Agent에 MSIE 또는 Trident라는 글자가 들어있는지 검사
	 boolean ieBrowser = (agent.indexOf("MSIE") > -1) || (agent.indexOf("Trident") > -1);
	 
	 if(ieBrowser){
		 // ie : 다운로드시 한글 깨짐, 공백문자 표시 +
		 
		 // 주소줄에 있는 파일네임을 가져와서 utf-8로 바꿈
		 // %20 : 공백문자
		 // + 표시되는 문자를 공백문자로 바꿈
		 fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
	 
	 } else{
		 // 그 외 나머지 브라우저 : 인코딩처리 (한글처리)
		 
		 fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
		 
	 }
	 
	 /////////////////////////////////////////////////////////////////////////
	 
	 // 모든 파일이 다운로드 형태로 실행설정
	 response.setHeader("Content-Disposition", "attachment; filename="+fileName);
	 
	 out.clear();
	 out = pageContext.pushBody();
	 
	 // 출력 스트림 객체
	 
	 ServletOutputStream out2 = response.getOutputStream();
	 
	 int data = 0;
	 
	 // 파일을 배열의 크기만큼씩 읽어오기 / 파일이 끝날 때까지
	 // -1 : EOF(End Of File)
	 while( (data = fis.read(b,0,b.length)) != -1 ){
		 out2.write(b,0,data);
	 }
	 
	 // 배열에 공백을 채우기
	 out2.flush();
	 out2.close();
	 fis.close();
	 
	%>
	
<%-- 	<%=fileName %> --%>

	
	
	
</body>
</html>