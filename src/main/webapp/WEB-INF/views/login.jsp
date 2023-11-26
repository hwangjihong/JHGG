<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JG.HH</title>
<link href="resource/img/icon.png" rel="shortcut icon" type="image/x-icon">
<link href="resource/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="__next">
		<header><%@include file="navigation.jsp" %></header>
		<div class="content">
			<div class="login-wrapper wrapper">
		        <h2>Login</h2>
		        <form method="post" action="#" id="input-form">
		            <input type="text" name="userName" placeholder="아이디">
		            <input type="password" name="userPassword" placeholder="비밀번호">          
		            <input type="submit" value="로그인">
		        </form>
		        <a onclick="location.href='register.do'">회원가입</a>
		    </div>
	    </div>
	</div>

</body>
</html>