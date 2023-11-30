<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="resource/img/icon.png" rel="shortcut icon" type="image/x-icon">
<link href="resource/css/boardWrite.css" rel="stylesheet" type="text/css">
<title>JHGG</title>
</head>
<body>
	<div id="__next">
		<header><%@include file="navigation.jsp" %></header>
		<div>
			<div class="content">	
				<div class="input">	
					<div>
					<form class="form" action="boardWriteAction.do">
							<input type="text" class="form-title" placeholder="제목" maxlength="40" name="contentTitle">
							<textarea type="text" class="form-content" placeholder="글 내용을 작성하세요" maxlength="1024" name="contentDetail"></textarea>
							<input type="submit" class="button" value="작성">
					</form>
					
					</div>
				</div>	
			</div>
		</div>
	</div>
</body>
</html>