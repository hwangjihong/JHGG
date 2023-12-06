<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resource/img/icon.png" rel="shortcut icon" type="image/x-icon">
<link href="resource/css/board.css" rel="stylesheet" type="text/css">
<title>JHGG</title>
</head>
<body>
	<div id="__next">
		<header><%@include file="navigation.jsp" %></header>
		<div class="content-header">
			<h1>게시판</h1>
		</div>
		<div class="content-container">
			<div>
				<table class="comunity-table">
					<thead>
						<tr>
							<th>#</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
						</tr>
					</thead>
					<tbody>
							<c:forEach var="list" items="${list }">
								<tr class="info">
									<td>${list.boardId}</td>
									<td><a onclick="location.href='boardRead.do?boardId=${list.boardId}'">${list.title }</a></td>
									<td>${list.writerNick }(${list.writerId })</td>
									<td>${list.redate }</td>
									<td>${list.target }</td>
								</tr>
							</c:forEach>
					</tbody>
				</table>
				<div class="page">
					<div class="tool">
						<div>
							<form action="boardSearch.do">
								<input class="input" type="text" name="data">
								<input class="search" type="submit" value="검색">
							</form>
						</div>
						<div>
							<button onclick="location.href='boardWrite.do'">글쓰기</button>
						</div>						
					</div>
					<div>
						<c:forEach var="i" begin="1" end="${pageCount }">
							<c:choose>
								<c:when test="${page != i}">
									<c:choose>
										<c:when test="${data != null }">
											<a href="boardSearch.do?data=${data }&&page=${i }">
												<div class="pages">
													${i }
												</div>
											</a>
										</c:when>
										<c:otherwise>
											<a href="board.do?page=${i }">
												<div class="pages">
													${i }
												</div>
											</a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${data != null }">
											<a href="boardSearch.do?data=${data }&&page=${i }">
												<div class="selectedPage">
													${i }
												</div>
											</a>
										</c:when>
										<c:otherwise>
											<a href="board.do?page=${i }">
												<div class="selectedPage">
													${i }
												</div>
											</a>
										</c:otherwise>
									</c:choose>
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