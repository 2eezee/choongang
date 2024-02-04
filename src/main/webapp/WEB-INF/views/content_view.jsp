<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="modify" method="post">
		<input type="hidden" name="bId" value="${mvc_board.bId }">
		<table border="1">
			<tr>
				<td>번호</td><td> ${mvc_board.bId }</td>
			</tr>
			<tr>
				<td>히트</td><td> ${mvc_board.bHit }</td>
			</tr>
			<tr>
				<td>이름</td><td><input type="text" name="bName" value="${mvc_board.bName }"></td>
			</tr>
			<tr>
				<td>제목</td>
				<td> <input type="text" name="bTitle" value="${mvc_board.bTitle }"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td> <textarea rows="10" name="bContent">${mvc_board.bContent }</textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="수정"> &nbsp;&nbsp;
				<a href="list">목록보기</a> &nbsp;&nbsp;
				<a href="delete?bId=${mvc_board.bId }">삭제</a> &nbsp;&nbsp;
				<a href="reply_view?bId=${mvc_board.bId }">답변</a></td>
			</tr>
		</table>
		
	</form>

</body>
</html>