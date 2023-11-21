<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resource/img/icon.png" rel="shortcut icon" type="image/x-icon">
<link href="resource/css/rank.css" rel="stylesheet" type="text/css">
<title>JH.GG</title>
</head>
<body>
	<div id="__next">
		<header><%@include file="navigation.jsp" %></header> 
		<div class="content-header">
			<h1>랭크</h1>
		</div>
		<div class="content-container">
			<div>
				<table class="ranking">
					<thead>
						<tr>
							<th>#</th>
							<th>소환사</th>
							<th>티어</th>
							<th>LP</th>
							<th>승률</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="rank" items="${list }">
							<c:set var="i" value="${i + 1}"/>
							<tr class="summoner">
								<td>${startRank + i }</td>
								<td>${rank.summonerName }</td>
								<td>Challenger</td>
								<td>${rank.leaguePoints }</td>
								<td>${rank.wins }W / ${rank.losses }L</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="page">
					<div>
						<c:forEach var="i" begin="1" end="${pageCount }">
							<c:choose>
								<c:when test="${page != i}">
									<a href="summonerRanking.do?page=${i }">
										<div class="pages">
											${i }
										</div>
									</a>
								</c:when>
								<c:otherwise>
									<a href="summonerRanking.do?page=${i }">
										<div class="selectedPage">
											${i }
										</div>
									</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>