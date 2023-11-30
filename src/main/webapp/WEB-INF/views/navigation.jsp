<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resource/css/navbar.css" rel="stylesheet" type="text/css">
</head>
<body>
	<nav class="navbar">
	  <div class="container">
	    <a class="navbar-brand" onclick="location.href='home.do'">JH.GG</a>
	    <div class="collapse">
	      <ul class="navbar-nav">
	        <li class="nav-item">
	          <a class="nav-link" onclick="location.href='home.do'">홈</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" onclick="location.href='summonerRanking.do'">랭킹</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" onclick="location.href='board.do'">게시판</a>
	        </li>	        
	      </ul>
	      <div>
		  	<c:choose>
				<c:when test="${sessionScope.id == null}">
					<a class="loginout" onclick="location.href='login.do'">로그인</a>
				</c:when>
				<c:otherwise>
					<a class="loginout" onclick="location.href='logout.do'">로그아웃</a>					
				</c:otherwise>
			</c:choose>
	      	
	      </div>
	    </div>
	  </div>
	</nav>
	
</body>
</html>