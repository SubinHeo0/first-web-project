<%@page import="com.town.member.db.MemberDTO"%>
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
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$('#pw').blur(function() {
			// 			var in_id = $('#id').val();
			// 			var in_pw = $('#pw').val();

			$.ajax({
				type : "post",
				url : "./MemberPwCheckAction.me",
				data : {
					id : $('#id').val(),
					pw : $('#pw').val()
				},
				success : function(data) {
					$('#pwCheck').html(data);
				},
				error : function error() {
					alert('시스템 문제발생');
				}
			});
		});

		// 비밀번호 변경 버튼
		// 비번 입력 후 버튼을 눌렀다는 거 자체가 일단 blur됐다는 뜻으로 위 ajax가 실행됨
		$('#pwChange').click(function() {
			if ($('#pw').val().length == 0) {
				$('#pw').focus();
			}

			// 			$.ajax({
			// 				type: "post",
			// 				url: "./MemberPwCheckAction.me",
			// 				data: {id : $('#id').val(), pw : $('#pw').val()},
			// 				success: function(data){
			// 							$('#pwCheck').html(data)
			// 							alert('비밀번호확인완료')
			// 						},
			// 				error: function error(){ alert('시스템 문제발생'); }
			// 			});

			// 			alert('비밀번호 확인 완료');

			if ($('#chkPw').val() == 0) { // 비번 틀림
				$('#pw').focus();
			} else if ($('#chkPw').val() == 1) { // 비번 일치
				$('#upwDiv').css("display", "");
				$('#upw2Div').css("display", "");
			}

		});
		// 비밀번호 변경 버튼

		// 비밀번호 글자수 체크
		$('#upw').blur(function() {
			if ($('#upw').val().length < 8) {
				$('#upwCheckVal').attr('value', '0');

				$('#upwCheck').css({
					"display" : "",
					"color" : "red"
				});
			} else { // 글자수가 8이상일 때(통과:1)
				$('#upwCheckVal').attr('value', '1');

				$('#upwCheck').css({
					"display" : "none"
				});
			}
		});

		// 비밀번호 재확인 일치 체크
		$('#upw2').blur(function() {
			if ($('#upw').val() != $('#upw2').val()) {
				$('#upw2CheckVal').attr('value', '0');

				$('#upw2Check').css({
					"display" : "",
					"color" : "red"
				});
			} else { // 비번일치 통과(1)
				$('#upw2CheckVal').attr('value', '1');

				$('#upw2Check').css({
					"display" : "none"
				});
			}
		});
		
		// unique제약조건 체크(닉네임)
		$('#nickname').blur(function(){
			$.ajax({
				type: "post",
				url: "./MemberNicknameCheckAction.me",
				data: {nickname : $('#nickname').val()},
				success: function(data){ $('#nicknameCheck').html(data); },
				error: function error(){ alert('시스템 문제발생'); }
			});
		});
	
		// unique제약조건 체크(전화번호)
		$('#tel').blur(function(){
			$.ajax({
				type: "post",
				url: "./MemberTelCheckAction.me",
				data: {tel : $('#tel').val()},
				success: function(data){ $('#telCheck').html(data); },
				error: function error(){ alert('시스템 문제발생'); }
			});
		});
		
		// unique제약조건 체크(이메일)
		$('#email').blur(function(){
			$.ajax({
				type: "post",
				url: "./MemberEmailCheckAction.me",
				data: {email : $('#email').val()},
				success: function(data){ $('#emailCheck').html(data); },
				error: function error(){ alert('시스템 문제발생'); }
			});
			
		});

		$('form').submit(function() {
			// 비번 틀림
			if ($('#chkPw').val() == 0) {
				$('#pw').focus();
				return false;
			}
			if ($('#upwCheckVal').val() == 0) {
				$('#upw').focus();
				return false;
			}
			if ($('#upw2CheckVal').val() == 0) {
				$('#upw2').focus();
				return false;
			}
			if($('#chkNickname').val() == -1 || $('#chkNickname').val() == 0){
				$('#nickname').focus();
				return false;
			}
			if($('#chkTel').val() == 0){
				$('#tel').focus();
				return false;
			}
			if($('#chkEmail').val() == 0){
				$('#email').focus();
				return false;
			}
		});
		
		$('#deleteBtn').click(function(){
			if($('#pw').val().length == 0 || $('#chkPw').val() == 0){
				$('#pw').focus();
			} else if($('#chkPw').val() == 1){
				
				if(confirm('정말로 탈퇴하시겠습니까')){
					location.href='./MemberDelete.me?id='+$('#id').val();
				}
			}

		});

	}); // JQuery

	//JS
	// 우편번호
	function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("sample4_extraAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
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
					<li><a href="./Main.mem">Home</a></li>
					<li>Modify</li>
				</ul>
				<h2>회원정보 수정</h2>
			</div>
		</div>
	</section>
	<!-- end post-wrapper-top -->

	<%
		MemberDTO dto = (MemberDTO) request.getAttribute("dto");
	%>

	<section class="section1">
		<div class="container clearfix">
			<div class="content col-lg-8 col-md-8 col-sm-8 col-xs-12 clearfix">
				<div class="clearfix"></div>

				<div class="clearfix"></div>
				<div class="divider"></div>

				<h4 class="title"><%=session.getAttribute("nickname")%></h4>

				<form id="updateform" action="./MemberInfoProAction.me" method="post" name="updateform">
					<div class="form-group">
						<label for="id">아이디 <span class="required">*</span></label>
						<input type="text" name="id" id="id" class="form-control" value="<%=dto.getId()%>" readonly>
						<span id="idCheck"></span>
					</div>
					<div class="form-group">
						<label for="pw">현재 비밀번호 <span class="required">*</span></label>
						<span id="pwCheck"></span>
						<input type="password" name="pw" id="pw" class="form-control" placeholder="비밀번호를 입력해주세요" required maxlength="16">
					</div>
					<div class="form-group">
						<input type="button" class="button" id="pwChange" value="비밀번호 변경">
					</div>
					<div class="form-group" id="upwDiv" style="display: none;">
						<label for="upw">변경할 비밀번호 <span class="required">*</span></label>
						<input type="password" name="upw" id="upw" class="form-control" placeholder="비밀번호를 입력해주세요" maxlength="16">
						<div id="upwCheck" style="display: none;">
							8~16자로 입력해주세요
							<input type="hidden" id="upwCheckVal" value="1" name="upwCheckVal">
						</div>
					</div>
					<div class="form-group" id="upw2Div" style="display: none;">
						<label for="upw2">변경할 비밀번호 재확인 <span class="required">*</span></label>
						<input type="password" name="upw2" id="upw2" class="form-control" placeholder="비밀번호를 입력해주세요" maxlength="16">
						<div id="upw2Check" style="display: none;">
							비밀번호가 일치하지 않습니다
							<input type="hidden" id="upw2CheckVal" value="1" name="upw2CheckVal">
						</div>
					</div>
					<div class="form-group">
						<label for="name">이름 <span class="required">*</span></label>
						<input type="text" name="name" class="form-control" value="<%=dto.getName()%>" required>
					</div>
					<div class="form-group">
						<label for="nickname">닉네임 <span class="required">*</span></label>
						<input type="text" name="nickname" id="nickname" class="form-control" value="<%=dto.getNickname()%>" required>
						<span id="nicknameCheck"></span>
					</div>
					<div class="form-group">
						<label for="birth">생년월일 <span class="required">*</span></label>
						<input type="text" name="birth" class="form-control" value="<%=dto.getBirth()%>" readonly>
					</div>
					<div class="form-group">
						<label for="gender">성별 <span class="required">*</span></label><br>
						<input type="radio" name="gender" value="남"
							<%if (dto.getGender().equals("남")) {%>
							 checked 
							<%}%>
						>남
						<input type="radio" name="gender" value="여"
							<%if (dto.getGender().equals("여")) {%>
							 checked 
							<%}%>
						>여
					</div>
					<div class="form-group">
						<label for="tel">휴대전화 <span class="required">*</span></label>
						<input type="tel" name="tel" id="tel" class="form-control" value="<%=dto.getTel()%>" required>
						<span id="telCheck"></span>
					</div>
					<hr>
					<div class="form-group">
						<label for="email">이메일</label>
						<input type="email" name="email" id="email" class="form-control" 
							<%if (dto.getEmail() == null) {%>
								 placeholder="example@yoursite.com" 
							<%} else {%>
							 	value="<%=dto.getEmail()%>" 
							<%}%>
						>
						<span id="emailCheck"></span>
					</div>
					<div class="form-group">
						<label for="addr">주소</label><br>
						<input type="text" id="sample4_postcode" name="addr_num" class="form-control" style="width: 20%; display: inline;"
							<%if (dto.getAddr_num() == null) {%>
								placeholder="우편번호"
							<%} else {%>
								value="<%=dto.getAddr_num() %>"
							<%} %>
						>
						<input type="button" onclick="sample4_execDaumPostcode()" class="button" value="우편번호 찾기"><br>
						<input type="text" id="sample4_roadAddress" name="addr_load" class="form-control" style="width: 50%;"
							<%if (dto.getAddr_load() == null) {%>
								placeholder="도로명주소"
							<%} else {%>
								value="<%=dto.getAddr_load() %>"
							<%} %>
						>
						<input type="hidden" id="sample4_jibunAddress" name="addr" class="form-control" style="width: 50%;"
							<%if (dto.getAddr() == null) {%>
								placeholder="지번주소"
							<%} else {%>
								value="<%=dto.getAddr() %>"
							<%} %>
						>
						<span id="guide" style="color:#999;display:none"></span>
						<input type="text" id="sample4_detailAddress" name="addr_detail" class="form-control"
							<%if (dto.getAddr_detail() == null) {%>
								placeholder="상세주소"
							<%} else {%>
								value="<%=dto.getAddr_detail() %>"
							<%} %>
						>
						<input type="text" id="sample4_extraAddress" name="addr_dong" class="form-control"
							<%if (dto.getAddr_dong() == null) {%>
								placeholder="참고항목"
							<%} else {%>
								value="<%=dto.getAddr_dong() %>"
							<%} %>
						>
					</div>

					<div class="form-group">
						<input type="submit" class="button" value="정보수정">
						<input type="button" class="button" id="deleteBtn" value="탈퇴하기" >
					</div>

				</form>
			</div>
			<!-- end content -->





		</div>
		<!-- end container -->
	</section>
	<!-- end section -->

	<!-- footer -->
	<jsp:include page="../inc/bottom.jsp"></jsp:include>
	<!-- footer -->
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
