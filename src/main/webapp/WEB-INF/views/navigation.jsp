<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resource/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<link href="resource/css/navbar.css" rel="stylesheet" type="text/css">
</head>
<body>
	<nav class="navbar navbar-expand-lg">
	  <div class="container-fluid">
	    <a class="navbar-brand" onclick="location.href='home.do'">JH.GG</a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarSupportedContent">
	      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
	        <li class="nav-item">
	          <a class="nav-link" onclick="location.href='home.do'">홈</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="#">챔피언 분석</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" onclick="location.href='summonerRanking.do'">랭킹</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="#">커뮤니티</a>
	        </li>
	        
	      </ul>
	      <form class="d-flex" role="search">

	      </form>
	    </div>
	  </div>
	</nav>
	<script src="resource/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</body>
</html>