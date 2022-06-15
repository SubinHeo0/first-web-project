<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
</head>

<body>
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<!-- end header -->

	<section class="post-wrapper-top">
		<div class="container">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<ul class="breadcrumb">
					<li><a href="./Main.go">Home</a></li>
					<li>글목록</li>
				</ul>
				<h2>${nickname }님의 글</h2>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<!-- search -->
				<div class="search-bar">
					<form action="./BoardSearch.bo" method="get">
						<input type="hidden" name="result" value="${result }">
						<input type="hidden" name="pageCount" value="${pageCount }">
						<input type="hidden" name="pageBlock" value="${pageBlock }">
						<input type="hidden" name="startPage" value="${startPage }">
						<input type="hidden" name="endPage" value="${endPage }">
						<fieldset>
							<input type="image" src="img/pixel.gif" class="searchsubmit"/>
							<input type="text" class="search_text showtextback" name="search" placeholder="검색내용을 입력하세요." />
						</fieldset>
					</form>
				</div>
				<!-- / end div .search-bar -->
			</div>
		</div>
	</section>
	<!-- end post-wrapper-top -->

	<section class="section1">
		<div class="container clearfix">
			<div
				class="content pull-right col-lg-9 col-md-9 col-sm-9 col-xs-12 clearfix">
				<div class="clearfix"></div>
				<hr>

				<table class="table table-striped" data-effect="fade">
					<thead>
						<tr>
							<th>No</th>
							<th>Title</th>
							<th>Writer</th>
							<th>Date</th>
							<th>Read</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="dto" items="${userBoardList }">
							<tr onclick="location.href='./BoardContent.bo?num=${dto.num}&pageNum=${pageNum }&my=${my }'; " style="cursor: pointer;">
								<td>${dto.num }</td>
								<td style="text-align: left; padding-left: 2em;">
									<c:if test="${dto.re_lev > 0 }">
										<img src="./img/level.gif" width="${dto.re_lev * 10 }">
<!-- 										<i class="fa fa-solid fa-arrow-right"></i>  -->
										<img src="./img/re.gif">
									</c:if>
									<c:if test="${dto.file != null }">
										<img src="./upload/sm_${dto.file.split(',')[0] }" style="border-radius: 10px;">
									</c:if>
									<b>${dto.subject }</b>
									<c:if test="${dto.file != null }">
										<i class="fa fa-picture-o"></i>
									</c:if>
									<c:if test="${dto.c_count > 0 }">
										<span style="padding-left: 0.5em; color:#337AB7;"> [ ${dto.c_count } ] </span>
									</c:if>
									<p style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 500px;">
									${dto.content }
									</p>
								</td>
								<td>${dto.nickname }</td>
								<td><fmt:formatDate value="${dto.date }" pattern="yy-MM-dd"/></td>
								<td>${dto.readcount }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="clearfix"></div>
				<hr>

				<div class=" text-center">
					<c:if test="${result != 0 }">
						<ul class="pagination">
							<c:if test="${startPage > pageBlock }">
								<li><a
									href="./BoardList.bo?pageNum=${startPage-pageBlock }">«</a></li>
							</c:if>
							<c:forEach var="i" begin="${startPage }" end="${endPage }"
								step="1">
								<li><a href="./BoardList.bo?pageNum=${i }">${i }</a></li>
							</c:forEach>
							<c:if test="${endPage < pageCount }">
								<li><a
									href="./BoardList.bo?pageNum=${startPage+pageBlock }">»</a></li>
							</c:if>
						</ul>
					</c:if>
				</div>
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
