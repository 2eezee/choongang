<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
	<h1>게시판</h1>
	<table>
		<tr>
			<td>번호</td><td>이름</td><td>제목</td><td>날짜</td>	<td>히트</td>
		</tr>
	    <c:forEach items="${boardList }" var="mvc_board">
			<tr>
				<td>${mvc_board.bId}</td>
				<td>${mvc_board.bName}</td>
				<td>
					<c:forEach begin="1" end="${mvc_board.bIndent}">-</c:forEach>
								<a href="content_view?bId=${mvc_board.bId}">${mvc_board.bTitle}</a></td>
							<td>${mvc_board.bDate}</td>
							<td>${mvc_board.bHit}</td>
						</tr>
		</c:forEach>
		<tr>
			<td colspan="5"> <a href="write_view">글작성</a> </td>
		</tr>
	</table>
</body>
</html>