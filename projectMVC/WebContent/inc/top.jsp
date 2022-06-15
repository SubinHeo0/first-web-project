<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="topbar clearfix">
	<div class="container">
		<div class="col-lg-12 text-right">
			<div class="social_buttons">
			<%
			 // 로그인 X (join, login)
			 //String id = (String)session.getAttribute("id");
			 String nickname = (String)session.getAttribute("nickname");
			if(nickname == null){
				%>
				<a href="./MemberLogin.me" data-toggle="tooltip" data-placement="bottom" title="Login"><i class="fa fa-solid fa-lock"></i></a>
				<a href="./MemberJoin.me" data-toggle="tooltip" data-placement="bottom" title="Join"><i class="fa fa-solid fa-user-plus"></i></a>
				<%
			} else {
				// 로그인 O (user, logout)
				%>
				<a href="./MemberInfo.me" data-toggle="tooltip" data-placement="bottom" title="<%=nickname%>"><i class="fa fa-solid fa-user"></i></a>
				<a href="./MemberLogout.me" data-toggle="tooltip" data-placement="bottom" title="Logout"><i class="fa fa-solid fa-power-off"></i></a>
				<%
			}
			
			%>
				
				
			</div>
		</div>
	</div>
	<!-- end container -->
</div>
<!-- end topbar -->

<header class="header">
	<div class="container">
		<div class="site-header clearfix">
			<div class="col-lg-3 col-md-3 col-sm-12 title-area">
				<div class="site-title" id="title">
					<a href="./Main.go" title="">
						<h4>
							MAXI<span>BIZ</span>
						</h4>
					</a>
				</div>
			</div>
			<!-- title area -->
			<div class="col-lg-9 col-md-12 col-sm-12">
				<div id="nav" class="right">
					<div class="container clearfix">
						<ul id="jetmenu" class="jetmenu blue">
							<li class="active"><a href="index.html">Home</a></li>
							<li><a href="#">동네생활</a>
								<ul class="dropdown">
									<li><a href="./BoardWrite.bo">글쓰기</a></li>
									<li><a href="./BoardList.bo">글목록</a></li>
									<li><a href="./BoardFileUpload.bo">자료글쓰기</a></li>
									<c:if test="${sessionScope.nickname != null }">
										<li><a href="./BoardMyList.bo">내가 쓴 글</a></li>
									</c:if>
								</ul>
							</li>
							<li><a href="./Map.ma">지도</a>
							<c:if test="${sessionScope.id eq 'admin' }">
							<li><a href="./AdminMemberList.ad">회원관리</a>
							</li>
<!-- 							<li><a href="#">게시글관리</a> -->
<!-- 							</li> -->
							</c:if>
						</ul>
					</div>
				</div>
				<!-- nav -->
			</div>
			<!-- title area -->
		</div>
		<!-- site header -->
	</div>
	<!-- end container -->
</header>