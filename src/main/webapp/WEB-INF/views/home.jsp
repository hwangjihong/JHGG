<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resource/img/icon.png" rel="shortcut icon" type="image/x-icon">
<link href="resource/css/home.css" rel="stylesheet" type="text/css">
<title>JH.GG</title>

</head>
<body>
	<div id="__next">
		<header><%@include file="navigation.jsp" %></header> 
		<div class="content">
			<div class="banner"><img src="resource/img/main.png"></div>
			<div class="input" role="tablist">
				<div>
					<form action="rank.jsp" class="searchinfo">
						<div>							
							<small class="label">Region</small>							
							<div class="region">
								<span>Korea</span>	
							</div>	
						</div>
						<div class="focused">
							<label class="label">Search</label>
							<input name="search" autocapitalize="off" type="text" placeholder="소환사명...">
						</div>
						<button type="submit">.GG</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>