<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 페이지</title>
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function() {
		//전달받은 값을 가지고 핸들링할 수 있게 만들어줌
		<c:if test = "${sessionScope.status eq 'emptyAccount'}">
			$("#errorAccount").show();
		</c:if>
		<c:if test = "${sessionScope.status eq 'emptyPassword'}">
			$("#errorPassword").show();
		</c:if>
		
		$("#loginBtn").click(function() {	
			/* validation check */
			if ($("#account").val() == "") {
				//alert("계정을 입력하세요!");
				$("#errorAccount").slideDown(300);
				$("#account").focus();
				return false;		
			} else {
				$("#errorAccount").slideUp(300);
			}
			
			if ($("#password").val() == "") {
				//alert("비밀번호를 입력하세요!");
				$("#errorPassword").slideDown(300);
				$("#password").focus();
				return false;
			} else {
				$("#errorPassword").slideUp(300);
			}
			/* validation check */

			$("#loginForm").attr({
				"action" : "<c:url value = "/login"/>",
				"method" : "post"
			}).submit();
		});
	});
</script>

</head>
<body>

	<form id="loginForm">
		<c:if test="${sessionScope.status eq 'fail'}"> <!--eq , == , equal  -->
		<div id="invalidAccountAndPassword">
			<div>계정 혹은 비밀번호가 잘못되었습니다.</div>
			<div>한번 더 확인 후 시도해 주세요.</div>
		</div>
		</c:if>
		<div>
			<input type="text" id="account" name="account" placeholder="계정" />
		</div>
		<div id="errorAccount" style="display: none;">계정을 입력하세요.</div>
		<div>
			<input type="password" id="password" name="password"
				placeholder="비밀번호" />
		</div>
		<div id="errorPassword" style="display: none;">비밀번호를 입력하세요.</div>
		<div>
			<input type="button" id="loginBtn" value="로그인" />
		</div>
	</form>

</body>
</html>