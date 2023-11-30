<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resource/img/icon.png" rel="shortcut icon" type="image/x-icon">
<link href="resource/css/boardRead.css" rel="stylesheet" type="text/css">
<title>JHGG</title>
</head>
<body>
	<div id="__next">
		<header><%@include file="navigation.jsp" %></header>
		<div>
			<div class="content">	
				<table class="input">	
					<thead>
						<tr>
							<th class="title">${board.title }</th>
						</tr>
						<tr class="boardInfo">
							<th>작성자 : ${board.writerNick }(${board.writerId })<br> 작성일 : ${board.redate }<br> 조회수 : ${board.target }</th>
						</tr>
					</thead>
					<tbody>
						<tr class="contentDetail">
							<td>${board.content }</td>
						</tr>
					</tbody>
				</table>
				<div class="button">
					<button class="modify">수정</button>
					<button class="remove">삭제</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>