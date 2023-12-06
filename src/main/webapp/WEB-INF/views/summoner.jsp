<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resource/img/icon.png" rel="shortcut icon" type="image/x-icon">
<link href="resource/css/summoner.css" rel="stylesheet" type="text/css">
<title>JHGG</title>
</head>
<body>
	<div id="__next">
		<header><%@include file="navigation.jsp" %></header>
		<div class="content">
			<div class="wrapper">
				<div class="header-profile-info">
					<div class="profile-icon">
						<img alt="profile image" src="https://ddragon.leagueoflegends.com/cdn/13.24.1/img/profileicon/${profileIconId }.png">
						<div class="level">
							<span class="level">${summonerLevel }</span>
						</div>
					</div>
					<div class="info">
						<div class="name">
							<h1>
								<strong class="gameName">${gameName }</strong>
								<span class="tagLine">#${tagLine }</span>
							</h1>
						</div>
					</div>
					<div class="entries">
						<div class="entries-content">
							<div>
								<img src="resource/img/Rank=${tier }.png">
							</div>
							<div class="info">
								<div class="tier">${tier }${rank }</div>
								<div class="lp">${leaguePoints }LP</div>
							</div>
							<div class="win-lose-container">
								<div class="win-lose">${wins }승${losses }패</div>
								<div class="ratio">${ratio }%</div>
							</div>
						</div>
					</div>			
				</div>
			</div>
		</div>
	</div>
</body>
</html>