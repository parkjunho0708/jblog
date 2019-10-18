<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="${pageContext.servletContext.contextPath}/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
$(function(){
	$("#input-userId").change(function(){
		$("#btn-check-userId").show();
		$("#img-checked").hide();
	});
	
	$("#btn-check-userId").click(function(){
		var userId = $("#input-userId").val();
		if(userId == ''){
			return;
		}
		
		// ajax 통신
		$.ajax({
			url: "${pageContext.servletContext.contextPath}/api/user/checkuserId?userId=" + userId,
			type: "get",
			dataType: "json",
			data: "",
			success: function(response){
				if(response.result == "fail"){
					console.error(response.message);
					return;
				}
				
				if(response.data == true){
					alert("이미 존재하는 메일입니다.");
					$("#input-userId").val("");
					$("#input-userId").focus();
					return;
				}
				
				$("#btn-check-userId").hide();
				$("#img-checked").show();
			},
			error: function(xhr, error){
				console.error("error : " + error);
			}
		});
		
	});
});
</script>
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<form:form
			modelAttribute="userVo"
			class="join-form" 
			id="join-form" 
			name="joinForm"
			method="post" 
			action="${pageContext.servletContext.contextPath }/user/join">
			
			<label class="block-label" for="userName">이름</label>
			<form:input path="userName" />
			<p style="font-weight:bold; color:red; text-align:left; padding:2px 0 0 0">
				<form:errors path="userName" />
			</p>	
			
			<label class="block-label" for="userId">아이디</label>
			<form:input path="userId" />
			<input id="btn-check-userId" type="button" value="id 중복체크">
			<img id="img-checked" style='width:20px; display:none' src='${pageContext.servletContext.contextPath }/assets/images/check.png'/>
			<p style="font-weight:bold; color:red; text-align:left; padding:2px 0 0 0">
				<form:errors path="userId" />
			</p>	

			<label class="block-label" for="userPassword">패스워드</label>
			<form:password path='userPassword' />

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form:form>
	</div>
</body>
</html>
