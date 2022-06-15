<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	// jQuery
	$(document).ready(function() {

		// 수정버튼 클릭시 수정페이지로 이동(정보 포함)
		$('#upBtn').click(function() {
			$('#fr').attr("action", "./BoardUpdate.bo");
			$('#fr').attr("method", "get");
			$('#fr').submit();
		});

		// 삭제버튼 클릭시 삭제페이지로 이동(정보 포함)
		$('#delBtn').click(function() {
			if (confirm('게시글을 삭제하시겠습니까?')) {
				$('#fr').attr("action", "./BoardDelete.bo");
				$('#fr').attr("method", "get");
				$('#fr').submit();
			}
		});

		// 댓글등록버튼
		$('#commentBtn').click(function() {
			if ($('#comment').val() != '') {
				$.ajax({
					type : "post",
					url : "./Comment.co",
					data : {
						b_num : $('#num').val(),
						comment : $('#comment').val()
					},
					success : function(data) {
						// 						alert('댓글등록완료');
						$('#comment').val('');
						location.reload();
					},
					error : function error() {
						alert('시스템 문제발생');
					}
				});
			} else {
				$('#comment').focus();
			}
		});

		
		// 댓글삭제버튼
		$('.button').click(function(){
			
			// 클래스 이름이 button인건 많으니까 id값을 가져와서 그게 delete와 관련된 id면 다음 동작 수행
			var id_check = $(this).attr('id');
// 			alert(id_check);
			if(id_check.substring(0, 13)== 'commentDelBtn'){
				var commentNum = id_check.substring(13);
				
				if(confirm('댓글을 삭제하시겠습니까?')){
					$.ajax({
						type : "post",
						url : "./CommentDelete.co",
						data : {
							num : commentNum,
							b_num : $('#num').val()
						},
						success : function(data){
							location.reload();
						},
						error : function error(){
							alert('시스템 문제발생');
						}
					}); // ajax
				}	// if
				
			}	// if
						
		}); // 댓글삭제버튼
		
		
		// 댓글수정버튼
		$('.tabActive').click(function(){
			var id_check = $(this).attr('id');
			var commentNum = id_check.substring(12);
			
// 			$('#commentUpForm'+commentNum).css("display","");
			$('.comment'+commentNum).hide();
			$('#commentUpForm'+commentNum).show();
			
			// 수정 취소 버튼
			$('#commentUpCancelBtn'+commentNum).click(function(){
				$('#commentUpForm'+commentNum).hide();
				$('.comment'+commentNum).show();
			});
			
			// 수정 완료 버튼
			$('#commentUpCompleteBtn'+commentNum).click(function(){
				var content = $('#commentUp'+commentNum).val();

				$.ajax({
					type : "post",
					url : "./CommentUpdate.co",
					data : {
						// 댓글번호, 댓글내용 들고가기
						num : commentNum,
						content : content
					},
					success : function(data){
						location.reload();
					},
					error : function error(){
						alert('시스템 문제발생');
					}
				});
				
				$('#commentUpForm'+commentNum).hide();
				$('.comment'+commentNum).show();
				
			});
			
			
		});	// 댓글수정버튼
		
		
		// 대댓글버튼
		$('.button').click(function(){
			var id_check = $(this).attr('id');
			var commentNum = id_check.substring(12);

			if(id_check.substring(0, 12)== 'commentReBtn'){
				$('#commentReForm'+commentNum).show();
				
				// 대댓글 취소 버튼
				$('#commentReCancelBtn'+commentNum).click(function(){
		            $('#commentReForm'+commentNum).hide();
		            $('.comment'+commentNum).show();
		         });
				
				// 대댓글 등록 버튼
				 $('#commentReCompleteBtn'+commentNum).click(function(){
			            var content = $('#commentRe'+commentNum).val();
			            
			            $.ajax({
			               type : "post",
			               url : "./CommentReWrite.co",
			               data : {
			                  // 댓글번호, 댓글내용 들고가기
			                  num : commentNum,
			                  content : content
			               },
			               success : function(data){
			                  location.reload();
			               },
			               error : function error(){
			                  alert('시스템 문제발생');
			               }
			            });
			            
			            $('#commentReForm'+commentNum).hide();
			            
			         });

			}	// if
						
		}); // 대댓글버튼
		
		

	}); // JQuery

	// JS
</script>


</head>

<body>
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<!-- end header -->

	<section class="post-wrapper-top">
		<div class="container">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<ul class="breadcrumb">
					<li><a href="./Main.go">Home</a></li>
					<li>Content</li>
				</ul>
				<h2>게시글 본문(내용)</h2>
			</div>
		</div>
	</section>
	<!-- end post-wrapper-top -->

	<form method="post" id="fr">
		<input type="hidden" id="num" name="num" value="${dto.num }">
		<input type="hidden" id="pageNum" name="pageNum" value="${pageNum }">
	</form>

	<section class="section1">
		<div class="container clearfix">
			<div
				class="content pull-right col-lg-9 col-md-9 col-sm-9 col-xs-12 clearfix">
				<c:if test="${dto.file == null }">
					<div class="clearfix"></div>
					<hr>
				</c:if>

				<!-- SLIDE POST -->
				<article class="blog-wrap text-center">


					<c:if test="${dto.file != null }">
						<div class="blog-media">
							<div id="myCarousel" class="carousel slide">
								<div class="carousel-inner">

									<c:choose>
										<c:when test="${fn:length(dto.file.split(',')) > 1 }">
											<c:forEach var="file" items="${dto.file.split(',') }">
												<c:choose>
													<c:when test="${file eq dto.file.split(',')[0] }">
														<div class="item active" style="width: 100%; height: 500px; object-fit: cover;">
															<a href="./board/fileDown.jsp?fileName=${dto.file.split(',')[0] }">
																<img src="./upload/${dto.file.split(',')[0] }" style="width: 100%; height: 500px; object-fit: cover;"></a>
														</div>
													</c:when>
													<c:otherwise>
														<div class="item">
															<a href="./board/fileDown.jsp?fileName=${file }"><img
																src="./upload/${file }"
																style="width: 100%; height: 500px; object-fit: cover;"></a>
														</div>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<div class="item active">
												<a
													href="./board/fileDown.jsp?fileName=${dto.file.split(',')[0] }"><img
													src="./upload/${dto.file.split(',')[0] }"
													style="width: 100%; height: 500px; object-fit: cover;"></a>
											</div>
										</c:otherwise>
									</c:choose>

									<!-- end item -->

								</div>
								<!-- carousel inner -->

								<c:if test="${fn:length(dto.file.split(',')) > 1  }">
									<a class="left carousel-control" href="#myCarousel"
										data-slide="prev"> <span class="icon-prev"></span></a>
									<a class="right carousel-control" href="#myCarousel"
										data-slide="next"> <span class="icon-next"></span></a>
								</c:if>
							</div>
							<!-- end carousel -->
						</div>

					</c:if>


					<header class="page-header blog-title">
						<div class="author-wrap">
							<span class="inside">
								<a href="#"><img class="img-responsive" src="img/basic_profile.jpg"></a>
							</span>
						</div>
						<h3 class="general-title">${dto.subject }</h3>
						<div class="post-meta">
							<p>
								<a href="./BoardUserList.bo?nickname=${dto.nickname }">${dto.nickname }</a>
								<span class="sep">/</span>
								<c:if test="${dto.addr != null }">
								${dto.addr }
								<span class="sep">/</span>
								</c:if>
								<span class="publish-on"> <fmt:formatDate
										value="${dto.date }" pattern="yy-MM-dd" />
								</span> <span class="sep">/</span> 조회: ${dto.readcount }
							</p>
						</div>
					</header>

					<div class="post-desc">
<%-- 						<p style="margin-top: 2em;">${dto.content }</p> --%>
						<p style="margin-top: 2em; white-space: pre-line;">${dto.content }</p>
					</div>

				</article>

				<hr>
				<div>
					<div class="form-group pull-right">
						<c:if test="${dto.nickname == sessionScope.nickname }">
							<input type="submit" class="button" id="upBtn" value="수정">
							<input type="submit" class="button" id="delBtn" value="삭제">
						</c:if>
						<input type="submit" class="button" id="reBtn" value="답글"
							onclick="location.href='./BoardReWrite.bo?num=${dto.num}&re_ref=${dto.re_ref }&re_lev=${dto.re_lev }&re_seq=${dto.re_seq }';">
						<c:choose>
							<c:when test="${my == 0 }">
								<input type="button" class="button" value="목록"
									onclick="location.href='./BoardList.bo?pageNum=${pageNum}';">
							</c:when>
							<c:when test="${my == 1 }">
								<input type="button" class="button" value="목록"
									onclick="location.href='./BoardMyList.bo?pageNum=${pageNum}';">
							</c:when>
							<c:when test="${my == -1 }">
								<input type="button" class="button" value="목록"
									onclick="location.href='./BoardUserList.bo?nickname=${dto.nickname }&pageNum=${pageNum}';">
							</c:when>
						</c:choose>
					</div>
				</div>


				<div id="comments_wrapper">
<%-- 					<h4 class="title">댓글 ${dto.c_count }</h4> --%>
					<br>
					<!-- 댓글폼 -->
					<div class="comments_form" style="margin: 60px 0;">
						<div>
							<textarea class="form-control" name="comment" id="comment"
								rows="3" placeholder="댓글을 입력해주세요." required></textarea>
							<input type="button" id="commentBtn" class="button" value="댓글달기">
						</div>
					</div>
					<!-- 댓글폼 -->
					
					<h4 class="title">댓글 ${dto.c_count }</h4>

					<c:if test="${dto.c_count > 0 }">
						<c:forEach var="comment" items="${commentList }">
							<ul class="comment-list">
								<li>
									<article class="comment${comment.num }">
										<c:if test="${comment.re_lev > 0 }">
											<span style="float: left; margin-bottom: 7em; margin-right: 1.2em;">
												<img src="./img/level.gif" width="${comment.re_lev * 20 }">
												<img src="./img/re.gif">
											</span>
										</c:if>
<!-- 										<img src="img/basic_profile.jpg" class="comment-avatar" style="border: none;"> -->
										<div>
											<img class="commentProfile" src="img/basic_profile.jpg">
											<h4 class="comment-author" style="padding-top: 0.7em;">${comment.nickname }
												<small class="comment-meta">${comment.addr }
												</small>
												<div class="form-group pull-right">
													<input type="button" id="commentReBtn${comment.num }" class="button" value="답글">
													<c:if test="${sessionScope.nickname == comment.nickname }">
														<input type="button" id="commentUpBtn${comment.num }" class="button tabActive" value="수정">
														<input type="button" id="commentDelBtn${comment.num }" class="button" value="삭제">
													</c:if>
												</div>
											</h4>
											<div id="comment_content" style="padding-top: 1em; padding-bottom: 1em;">${comment.content }</div>
											<small class="comment-meta" style="padding: 0;"><fmt:formatDate value="${comment.date }" pattern="yy-MM-dd" /></small>
										</div>
									</article>
									
									<!-- 댓글수정폼 -->
									<div id="commentUpForm${comment.num }" style="display: none;">
										<textarea class="form-control" name="commentUp${comment.num }"
											id="commentUp${comment.num }" rows="3" required>${comment.content }</textarea>
										<div>
											<input type="button" id="commentUpCompleteBtn${comment.num }" class="button" value="수정">
											<input type="button" id="commentUpCancelBtn${comment.num }" class="button" value="취소">
										</div>
									</div>
									
									<!-- 대댓글폼 -->
									<div id="commentReForm${comment.num }" style="display: none;">
										<textarea class="form-control" name="commentRe${comment.num }"
											id="commentRe${comment.num }" rows="3" required></textarea>
										<div>
											<input type="button" id="commentReCompleteBtn${comment.num }" class="button" value="등록">
											<input type="button" id="commentReCancelBtn${comment.num }" class="button" value="취소">
										</div>
									</div>
									
									<hr style="">
									


								</li>
							</ul>
						</c:forEach>
					</c:if>
					<!-- End .comment-list -->

					<div class="clearfix"></div>



				</div>
				<!-- div comments -->






			</div>
			<!-- end content -->


			<!-- BEGIN SIDEBAR -->

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