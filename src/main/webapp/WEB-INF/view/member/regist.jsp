<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/button.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/input.css"/>" />
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function() {
		/* 키업이벤트 */
		$("#email").keyup(function(){
			var value = $(this).val();
			if(value != ""){
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else{
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}
		});
		
		$("#nickname").keyup(function(){
			var value = $(this).val();
			if(value != ""){
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else{
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}
		});
		
		$("#password").keyup(function(){
			var value = $(this).val();
			var pwdconfirm = $("#password-confirm").val();
			if(value != ""){
				
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else{
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}
			
			/* 위에 있는 비밀번호를 바꿨을 때 */
			if(value != pwdconfirm){
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password-confirm").removeClass("valid");
				$("#password-confirm").addClass("invalid");
			}
			else{
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password-confirm").removeClass("invalid");
				$("#password-confirm").addClass("valid");
			}
	
		});
		
		$("#password-confirm").keyup(function(){
			var value = $(this).val();
			var password = $("#password").val();
			
			if(value != password){
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password").removeClass("valid");
				$("#password").addClass("invalid");
			}
			else{
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password").removeClass("invalid");
				$("#password").addClass("valid");
			}
		});
		
		/* 클릭클릭 */
		$("#registBtn").click(function() {
			if ($("#email").val() == "") {
				alert("이메일을 입력하세요!");
				$("#email").focus();
				$("#email").addClass("invalid");
				return false;
			}

			if ($("#nickname").val() == "") {
				alert("닉네임을 입력하세요!");
				$("#nickname").focus();
				$("#nickname").addClass("invalid");
				return false;
			}

			if ($("#password").val() == "") {
				alert("비밀번호를 입력하세요!");
				$("#password").focus();
				$("#password").addClass("invalid");
				return false;
			}
			
			
			/* 값 전달!!!! */
			$("#registForm").attr({
				"method":"post",
				"action":"<c:url value="/regist"/>"
			})
			.submit();
		});
	});
</script>
</head>
<body>
	<div id="wrapper" style="text-align:center;">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		<form:form modelAttribute="registForm">
			<div>
				<%-- TODO Email 중복검사 하기 (ajax) --%>
				<input type="email" id="email" name="email" 
				placeholder="이메일을 입력하세요" value="${registForm.email }"/>
			<div><form:errors path="email"/></div>
			</div>
			<div>
				<%-- TODO Nickname 중복검사 하기 (ajax) --%>
				<input type="text" id="nickname" name="nickname"
					placeholder="닉네임을 입력하세요" value="${registForm.nickname }"/>
			<div><form:errors path="nickname"/></div>
			</div>
			<div>
				<input type="password" id="password" name="password"
					placeholder="비밀번호를 입력하세요" />
			<div><form:errors path="password"/></div>
			</div>
			<div>
				<input type="password" id="password-confirm" placeholder="비밀번호를 입력하세요" />
			</div>
			<div>
				<div id="registBtn" class="button">회원가입</div>
			</div>
		</form:form>
	</div>

</body>
</html>