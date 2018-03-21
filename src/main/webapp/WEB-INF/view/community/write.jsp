<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- spring validation check -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 등록 페이지</title>
<script src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"
	type="text/javascript"></script>
<!-- static 이후부터가 폴더명
여기서 static은 mapping에 작성해준거!!!
<mvc:resources location="/WEB-INF/static" mapping="/static/**"/>-->
<script type="text/javascript">
	$().ready(function() { /* 콜백함수!! */
		
		<c:if test="${mode == 'modify' && not empty communityVO.displayFilename }">
		$("#file").closest("div").hide();
		</c:if>
		
		
		
		$("#displayFilename").change(function(){
			var isChecked = $(this).prop("checked");
			if (isChecked){
				$("label[for=displayFilename]").css({
					"text-decoration-line": "line-through",
					"text-decoration-style":"double"
				});
				
				$("#file").closest("div").show();
			}
			else{
				$("label[for=displayFilename]").css({
					"text-decoration-line": "none"
				});
				$("#file").closest("div").hide();
			}
		});
		
		$("#writeBtn").click(function() {
			
			// 수정일시 체크하여 해당 컨트롤러 modify로 보내준다
			var mode = "${mode}";
			if(mode == "modify") {
				
				var url = "<c:url value="/modify/${communityVO.id}"/>";
			}
			else {
				var url = "<c:url value="/write"/>"
			}
			
			
			var writeForm = $("#writeForm");
			/* 속성을 제어시킴 */
			writeForm.attr({
				"method" : "post",
				"action" : url
			});
			writeForm.submit();
		});
	});
</script>

</head>
<body>
	<div id="wrapper" style="text-align:center;">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		<form:form modelAttribute="writeForm" enctype="multipart/form-data">
			<!-- method = "post" action = ""는 attr로 할꺼임 -->
			<div>
				제목 : <input type="text" id="title" name="title"
					value="${communityVO.title }" />
			</div>
			<div>
				<!-- 체크하고자 하는 form element의 name을 가져다가 적어주기 -->
				<form:errors path="title" />
			</div>
			<!-- <span id = "errorTitle" style = "display:none;">제목을 입력하세요.</span> -->
			<div>
				내용 :
				<textarea id="contents" name="contents">${communityVO.contents }</textarea>
			</div>
			
			<c:if test="${mode == 'modify' && not empty communityVO.displayFilename}">
				<div>
					<input type="checkbox"
							id="displayFilename"
							name="displayFilename"
							value="${communityVO.displayFilename}" />
					<label for="displayFilename">
					${communityVO.displayFilename}
					</label>
				</div>
			</c:if>
			<div>
				<form:errors path="contents" />
			</div>
			<!-- <span id = "errorContents" style = "display:none;">내용을 입력하세요.</span> -->
			<%-- <div>
				<!-- 입력하지 않고 숨긴 거처럼 보이게???? :: 데이터는 있지만 사용자가 없는거처럼 보임-->
				<!-- 닉네임 :-->
				<input type="hidden" id="nickname" name="nickname"
					value="${sessionScope.__USER__.nickname}" />
			</div> --%>
			<div>
				<!-- 아이디 :-->
				<input type="hidden" id="account" name="account"
					value="${sessionScope.__USER__.account}" />
			</div>
			<%-- <div>
				날짜 : <input type="date" id="writeDate" name="writeDate"
					value="${communityVO.writeDate }" />
			</div> --%>
			<div>
				<form:errors path="writeDate" />
			</div>
			<!-- <span id = "errorDate" style = "display:none;">날짜를 선택하세요.</span> -->
			<div>
				<input type="file" id="file" name="file" />
			</div>
			<div>
				<input type="submit" id="writeBtn" value="등록" />
			</div>

		</form:form>
	</div>
</body>
</html>