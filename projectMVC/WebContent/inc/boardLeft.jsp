<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="sidebar" class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
	<div class="widget">
		<h4 class="title">
			<span>Pages</span>
		</h4>
		<ul class="pages">
			<li><a href="./BoardWrite.bo">글쓰기</a></li>
			<li><a href="./BoardList.bo">게시판 글목록</a></li>
			<li><a href="./BoardFileUpload.bo">자료글쓰기</a></li>
			<c:if test="${sessionScope.nickname != null }">
				<li><a href="./BoardMyList.bo">내가 쓴 글</a></li>
			</c:if>
		</ul>
	</div>
</div>