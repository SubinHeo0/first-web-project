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
  
<script type="text/javascript">
	// JS
	function delMem(obj){
		if(!confirm('['+obj+'] 님을 삭제하겠습니까?')){
			return false;
		}
	}

</script>
  
</head>

<body>
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<!-- end header -->

	<section class="post-wrapper-top">
		<div class="container">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<ul class="breadcrumb">
					<li><a href="index.html">Home</a></li>
					<li>회원관리</li>
				</ul>
				<h2>회원 관리</h2>
			</div>
		</div>
	</section>
	<!-- end post-wrapper-top -->

	<section class="section1">
		<div class="container clearfix" style="width: 70%;">
			<div class="content col-lg-12 col-md-12 col-sm-12 col-xs-12 clearfix">

				<div class="clearfix"></div>
				<hr>

				<table class="table table-striped" data-effect="fade">
					<thead>
						<tr>
							<th>아이디</th>
							<th>이름</th>
							<th>닉네임</th>
							<th>생년월일</th>
							<th>성별</th>
							<th>전화번호</th>
							<th>이메일</th>
							<th>주소</th>
							<th>회원가입일</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="dto" items="${memberList }">
							<tr>
								<td>${dto.id }</td>
								<td>${dto.name }</td>
								<td>${dto.nickname }</td>
								<td>${dto.birth }</td>
								<td>${dto.gender }</td>
								<td>${dto.tel }</td>
								<c:choose>
									<c:when test="${dto.email != null }">
										<td><a href="./AdminMail.ad?id=${dto.id }&pageNum=${pageNum }">${dto.email }</a></td>
									</c:when>
									<c:otherwise>
										<td style="text-align: center;">-</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${dto.addr_load != null }">
										<td>${dto.addr_load } ${dto.addr_detail }</td>
									</c:when>
									<c:otherwise>
										<td style="text-align: center;">-</td>
									</c:otherwise>
								</c:choose>
								<td><fmt:formatDate value="${dto.reg_date }" pattern="yy-MM-dd"/></td>
								<td><a href="./AdminMemberDelete.ad?id=${dto.id }&pageNum=${pageNum}" onclick="return delMem('${dto.id}');"><i class="fa fa-solid fa-trash"></i></a></td>
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
									href="./AdminMemberList.ad?pageNum=${startPage-pageBlock }">«</a></li>
							</c:if>
							<c:forEach var="i" begin="${startPage }" end="${endPage }"
								step="1">
								<li><a href="./AdminMemberList.ad?pageNum=${i }">${i }</a></li>
							</c:forEach>
							<c:if test="${endPage < pageCount }">
								<li><a
									href="./AdminMemberList.ad?pageNum=${startPage+pageBlock }">»</a></li>
							</c:if>
						</ul>
					</c:if>
				</div>


			</div>
			<!-- end content -->

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
