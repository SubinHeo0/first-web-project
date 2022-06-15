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


<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e3aa938726619bec8bc5728b2b993b07&libraries=services"></script>


</head>

<body>
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<!-- end header -->

	<%
		String id = (String) session.getAttribute("id");
		// 	String nickname = (String)session.getAttribute("nickname");

		if (id == null) {
			response.sendRedirect("./MemberLogin.me");
		}
	%>

	<section class="post-wrapper-top">
		<div class="container">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
				<ul class="breadcrumb">
					<li><a href="./Main.go">Home</a></li>
					<li>Map</li>
				</ul>
				<h2>지도</h2>
			</div>
		</div>
	</section>
	<!-- end post-wrapper-top -->

	<section class="section1">
		<div class="container clearfix">
			<div class="clearfix" style="padding: 50px 50px 50px 50px;">


				<!-- 지도 -->
				<div class="map_wrap">
					<div id="map" style="width: 500px; height: 400px; margin: auto; border: 1px solid;"></div>
					<div class="hAddr">
						<span class="title">지도중심기준 행정동 주소정보</span> <span id="centerAddr"></span>
					</div>
				</div>
				<!-- 지도 -->

				<script type="text/javascript">
					function onGeoOk(position) {

						var lat = position.coords.latitude;
						var lon = position.coords.longitude;
						console.log("You live in", lat, lon);

						var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
						mapOption = {
							center : new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표
							level : 3
						// 지도의 확대 레벨 
						};

						// 지도를 생성합니다
						var map = new kakao.maps.Map(mapContainer, mapOption);

						/////////////////////////////////////////////////////////////////////////
						// 주소-좌표 변환 객체를 생성합니다
						var geocoder = new kakao.maps.services.Geocoder();
						/////////////////////////////////////////////////////////////////////////

						// 현재 위치의 위도와 경도
						var markerPosition = new kakao.maps.LatLng(lat, lon);

						// 마커 (처음은 현재위치)
						var marker = new kakao.maps.Marker({
							position : markerPosition
						});

						// 현재위치 마커 지도에 출력
						marker.setMap(map);

						/////////////////////////////////////////////////////////////////////////
						// 클릭한 위치에 대한 주소를 표시할 인포윈도우
						var infowindow = new kakao.maps.InfoWindow({
							zindex : 1
						});

						// 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
						searchAddrFromCoords(map.getCenter(), displayCenterInfo);

						// 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
						kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
							searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
								if (status == kakao.maps.services.Status.OK) {
									var detailAddr = !!result[0].road_address ? '<div>도로명주소 : '+result[0].road_address.address_name+'</div>' : 
										detailAddr += '<div>지번 주소 : '+result[0].address.address_name+'</div>';

									var content = '<div class="bAddr">'+'<span class="titld">법정동 주소정소</span>'+detailAddr+'</div>';

									// 마커를 클릭한 위치에 표시합니다 
									marker.setPosition(mouseEvent.latLng);
									marker.setMap(map);

									// 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
									infowindow.setContent(content);
									infowindow.open(map, marker);
								}
							});
						});

						// 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
						kakao.maps.event.addListener(map, 'idle', function() {
							searchAddrFromCoords(map.getCenter(), displayCenterInfo);
						});

						function searchAddrFromCoords(coords, callback) {
							// 좌표로 행정동 주소 정보를 요청합니다
							geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
						}

						function searchDetailAddrFromCoords(coords, callback) {
							// 좌표로 법정동 상세 주소 정보를 요청합니다
							geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
						}

						// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
						function displayCenterInfo(result, status) {
							if (status == kakao.maps.services.Status.OK) {
								var infoDiv = document.getElementById('centerAddr');

								for (var i = 0; i < result.length; i++) {
									// 행정동의 region_type 값은 'H' 이므로
									if (result[i].region_type === 'H') {
										infoDiv.innerHTML = result[i].address_name;
										break;
									}
								}
							}
						}

						/////////////////////////////////////////////////////////////////////////

						// 			// 지도에 마커를 표시합니다
						// 			marker.setMap(map);

						// 			// 지도에 클릭 이벤트를 등록합니다
						// 			// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
						// 			kakao.maps.event.addListener(map, 'click', function(mouseEvent){

						// 				// 클릭한 위도, 경도 정보를 가져옵니다 
						// 				var latlng = mouseEvent.latLng;

						// 				// 마커 위치를 클릭한 위치로 옮깁니다
						// 				marker.setPosition(latlng);

						// 				console.log("클릭한 위치 : ", latlng.getLat(), latlng.getLng());

						// 			});

					}

					function onGeoError() {
						alert("현재 위치를 알 수 없습니다.");
					}

					navigator.geolocation.getCurrentPosition(onGeoOk,
							onGeoError);
				</script>


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