<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 페이지</title>
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function() {
		$("#loginBtn").click(function() {
			$("#loginForm").attr({
				"action" : "<c:url value = "/login"/>",
				"method" : "post"
			}).submit();
		});
	});
</script>

</head>
<body>
	<div id="wrapper" style="text-align:center;">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		<form:form modelAttribute="loginForm">
			<c:if test="${sessionScope.status eq 'fail'}">
				<div id="invalidEmailAndPassword">
					<div>계정 혹은 비밀번호가 잘못되었습니다.</div>
					<div>한번 더 확인 후 시도해 주세요.</div>
				</div>
			</c:if>
			<div>
				<input type="text" id="email" name="email" placeholder="계정" />
			</div>
			<form:errors path="email" />
			<div>
				<input type="password" id="password" name="password"
					placeholder="비밀번호" />
			</div>
			<form:errors path="password" />
			<div>
				<input type="button" id="loginBtn" value="로그인" />
			</div>
		</form:form>
		<div>
			아직 회원이 아니신가요??<a href="<c:url value="/regist"/>">가입하기</a>
		</div>
	</div>
</body>
</html>