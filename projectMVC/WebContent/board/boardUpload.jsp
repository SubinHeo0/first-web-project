<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>MaxiBiz Bootstrap Business Template</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Favicons -->
<link href="img/favicon.png" rel="icon">
<link href="img/apple-touch-icon.png" rel="apple-touch-icon">

<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css?family=Ruda:400,900,700"
	rel="stylesheet">

<!-- Bootstrap CSS File -->
<link href="lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Libraries CSS Files -->
<link href="lib/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link href="lib/prettyphoto/css/prettyphoto.css" rel="stylesheet">
<link href="lib/hover/hoverex-all.css" rel="stylesheet">
<link href="lib/jetmenu/jetmenu.css" rel="stylesheet">
<link href="lib/owl-carousel/owl-carousel.css" rel="stylesheet">

<!-- Main Stylesheet File -->
<link href="css/style.css" rel="stylesheet">
<link rel="stylesheet" href="css/colors/blue.css">

<!-- =======================================================
    Template Name: MaxiBiz
    Template URL: https://templatemag.com/maxibiz-bootstrap-business-template/
    Author: TemplateMag.com
    License: https://templatemag.com/license/
  ======================================================= -->

<script src="./lib/jquery/jquery-3.6.0.js"></script>
<script type="text/javascript">
	
	var count = 0;
	var addCount;
	
	// jQuery
	$(document).ready(function(){
		
		// 파일 추가
		$('#addBtn').click(function(){
// 			alert('추가버튼클릭');
			
			for(var i=0; i<=count; i++){
				if(!document.getElementsByName("file"+i)[0]){
// 					alert('for문의 if절 만족');
					addCount = i;
// 					alert('for문의 if절 끝 addCount : '+addCount);
					break;
				}
				else addCount = count;
			}
			
			var str = "<span'><input type='file' name='file"+addCount+"' style='display: inline;'></span><br>";
			
			$('#fileGroup').append(str);
			count++;

		}); // 파일추가
		
		$('#subBtn').click(function(){
			
			if(!document.fr.subject.value){
				document.fr.subject.focus();
				return false;
			}
			if(!document.fr.content.value){
				document.fr.subject.focus();
				return false;
			}
			
			$('#count').val(count);
			
			if(count == 0){
				alert('couont==0');
				$('form').attr({
					enctype: '',
					action: './BoardWriteAction.bo'
				});
			} else {
				for (var i = 0; i < count; i++) {
	 				if(document.getElementsByName("file"+i)[0].value==''){
						alert('파일없음');
						$('form').attr({
							enctype: '',
							action: './BoardWriteAction.bo'
						});
	 				}
				}
			}
			
			
		});
		
	}); // jQuery
	
	// JS
	

</script>
  
  
</head>

<body>
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<!-- end header -->

<%
	String id = (String)session.getAttribute("id");
// 	String nickname = (String)session.getAttribute("nickname");
	
	if(id == null){
		response.sendRedirect("./MemberLogin.me");
	}
%>

	<section class="post-wrapper-top">
		<div class="container">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<ul class="breadcrumb">
					<li><a href="./Main.go">Home</a></li>
					<li>FileUpload</li>
				</ul>
				<h2>자료실</h2>
			</div>
		</div>
	</section>
	<!-- end post-wrapper-top -->

	<section class="section1">
		<div class="container clearfix">
<!-- 			<div class="clearfix" style="padding: 50px 50px 50px 50px;"> -->
				<div class="content pull-right col-lg-9 col-md-9 col-sm-9 col-xs-12 clearfix">
				<form name="fr" action="./BoardFileUploadAction.bo?id=<%=id %>" method="post" enctype="multipart/form-data">
					<input type="hidden" name="nickname" value="${sessionScope.nickname }">
					<input type="hidden" name="count" id="count" value="">
					<table class="table table-striped" data-effect="fade">
<!-- 						<thead> -->
<!-- 							<tr> -->
<!-- 								<th>Download Name</th> -->
<!-- 								<th>Files</th> -->
<!-- 							</tr> -->
<!-- 						</thead> -->
						<tbody>
							<tr>
								<td style="padding: 1.2em; text-align: center;">제목</td>
								<td><input type="text" class="form-control" name="subject" required></td>
							</tr>
							<tr>
								<td style="padding: 1.2em; text-align: center;">첨부파일</td>
								<td style="text-align: left;">
									<span id="fileGroup">
<!-- 										<span> -->
<!-- 											<input type="file" name="file2" style="display: inline;"> -->
<!-- 											<input type="button" class="button" id="DelBtn" value="삭제"> -->
<!-- 											<br> -->
<!-- 										</span> -->
									</span>
									<input type="button" class="button" id="addBtn" value="파일 추가">
								</td>
							</tr>
							<tr>
								<td style="padding: 5.2em; text-align: center;">내용</td>
								<td><textarea class="form-control" rows="10" name="content" required></textarea> </td>
							</tr>
						</tbody>
					</table>
					
					<div class="form-group pull-right">
						<input type="submit" class="button" id="subBtn" value="등록">
					</div>
					
				</form>

			</div>
			<!-- end content -->
			
			<jsp:include page="../inc/boardLeft.jsp"></jsp:include>
			<!-- end sidebar -->
		</div>
		<!-- end container -->
	</section>
	<!-- end section -->

	<jsp:include page="../inc/bottom.jsp"></jsp:include>
	<!-- end footer -->
	<div class="dmtop">Scroll to Top</div>

	<!-- JavaScript Libraries -->
	<script src="lib/jquery/jquery.min.js"></script>
	<script src="lib/bootstrap/js/bootstrap.min.js"></script>
	<script src="lib/php-mail-form/validate.js"></script>
	<script src="lib/prettyphoto/js/prettyphoto.js"></script>
	<script src="lib/isotope/isotope.min.js"></script>
	<script src="lib/hover/hoverdir.js"></script>
	<script src="lib/hover/hoverex.min.js"></script>
	<script src="lib/unveil-effects/unveil-effects.js"></script>
	<script src="lib/owl-carousel/owl-carousel.js"></script>
	<script src="lib/jetmenu/jetmenu.js"></script>
	<script src="lib/animate-enhanced/animate-enhanced.min.js"></script>
	<script src="lib/jigowatt/jigowatt.js"></script>
	<script src="lib/easypiechart/easypiechart.min.js"></script>

	<!-- Template Main Javascript File -->
	<script src="js/main.js"></script>

</body>
</html>
