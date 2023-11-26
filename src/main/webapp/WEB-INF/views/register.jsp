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
			<div class="register-wrapper wrapper">
		        <h2>Login</h2>
		        <form method="post" action="registerAction.do" id="input-form">
		            <input type="text" name="id" placeholder="아이디" minlength="8" maxlength="15" required>
		            <input type="password" name="password" placeholder="비밀번호" minlength="8" maxlength="15" required>   
		            <input type="password" name="passwordCheck" placeholder="비밀번호 확인" minlength="8" maxlength="15" required>
		            <input type="text" name="nickname" placeholder="닉네임" minlength="5" maxlength="15" required>
		            <input type="email" name="email" placeholder="이메일" maxlength="30" required>		                     
		            <input type="submit" value="로그인">
		        </form>
		    </div>
	    </div>
	</div>
</body>
</html>