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
</head>

<body>
	<%
	 session.invalidate();
	%>
	<script type="text/javascript">
		alert("로그아웃되었습니다!");
		location.href = './Main.go';
	</script>

</body>
</html>
