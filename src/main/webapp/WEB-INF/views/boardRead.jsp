<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				<c:set var="id" value="session.id"/>
				<c:if test="${sessionScope.id eq board.writerId}">
					<div class="button">
						<button class="modify" onclick="location.href='boardModify.do?boardId=${board.boardId}'">수정</button>
						<button class="remove" onclick="if(confirm('정말로 삭제하시겠습니까?')){location.href='boardRemoveAction.do?boardId=${board.boardId}';}">삭제</button>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>