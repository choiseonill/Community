<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 글 리스트</title>
<style>
td, th {
	border: 1px solid #333;
}
</style>
</head>
<body>
	<div id="wrapper" style="text-align: center;">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />

		<table style="margin-left: auto; margin-right: auto;">
			<tr>
				<!-- th는 볼드에 가운데 정렬 -->
				<th>ID</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
			<c:forEach items="${communityList}" var="contents">
				<tr>
					<!-- 왼쪽 정렬 -->
					<td>${contents.id}</td>
					<td><a href="<c:url value="/read/${contents.id}"/>">${contents.title}</a>
						<c:if test="${not empty contents.displayFilename}">
							<img src="<c:url value = "/static/img/save.png"/>"
								alt="${contents.displayFilename}" style="width: 10px;" />
						</c:if></td>
					<td><!-- 닉네임(이메일) -->
					<c:choose>
						<c:when test="${not empty contents.memberVO}">
							${contents.memberVO.nickname}&nbsp;[${contents.memberVO.email}]
						</c:when>
						<c:otherwise>
							탈퇴
						</c:otherwise>
						
					</c:choose>
					</td>
					<td>${contents.writeDate}</td>
					<td>${contents.viewCount}</td>
				</tr>
			</c:forEach>
			<!-- 게시글이 없을 때!!! 등록된 게시글이 없습니다. -->
			<!-- c:if는 if만!! //choose when otherwise로 elseif 대신함 -->
			<!-- not empty / empty -->
			<c:if test="${empty communityList}">
				<tr>
					<td colspan="5">등록된 게시글이 없습니다.</td>
				</tr>

			</c:if>
		</table>

		<div>
			<a href="<c:url value="/write"/>">글쓰기</a> <a
				href="<c:url value="/logout"/>">로그아웃</a>
		</div>
	</div>
</body>
</html>