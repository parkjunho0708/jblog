<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

	$("#btn-category-add").click(function(){
		var categoryVo = {
				"categoryName" : $('input[name=categoryName]').val(),
				"categoryDesc" : $('input[name=categoryDesc]').val(),
				"userId" : $('input[name=userId]').val()
		};
		if(categoryVo == ''){
			alert("카테고리 항목을 모두 입력해주세요.");
			return;
		}
		
		// ajax 통신
		$.ajax({
			url: "${pageContext.servletContext.contextPath}/api/category/categoryinsert",
			contentType : "application/json; charset=utf-8",
			type: "post",
			dataType: "json", // JSON 형식으로 받을거다!! (MIME type)
			data: JSON.stringify(categoryVo),
			statusCode: {
			    404: function() {
			      alert("page not found");
			    }
			},
			success: function(data){
				var vo = JSON.parse(data);
				$(".category-tbody").append("<tr>" +
				        "<td>" + vo.categoryNo + "</td>" +
				        "<td>" + vo.categoryName + "</td>" +
				        "<td>" + vo.postCount + "</td>" +
				        "<td>" + vo.categoryRegdate + "</td>" +
				        "<td>" + 
				        "<a class='category-delete-link' href='${pageContext.servletContext.contextPath}/api/category/categorydelete?categoryNo=" + vo.categoryNo + "&userId=" + vo.userId + "'><img src='${pageContext.request.contextPath}/assets/images/delete.jpg'  class='delete-img' ></a>" +
				        "</td>" +
				        "</tr>");
			},
			error: function(xhr, error){
				console.error("error : " + error);
			}
		});
	});
	
	$(document).on("click", "a.category-delete-link", function(event) { // when clicking on the link
		event.preventDefault();
		var deleteUrl = $(this).attr('href'); // get the href of the anchor
		var clickedRow = $(this);
		$.ajax({
			url : deleteUrl,
			type : "delete",
			dataType : "json",
			statusCode: {
			    404: function() {
			      alert("page not found");
			    }
			},
			success : function(data) {
				$(clickedRow).parent().parent().remove();
			},
			error : function(xhr, error) {
				console.error("error : " + error);
			}
		});
	
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.servletContext.contextPath}/${authUser.userId}/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.servletContext.contextPath}/${authUser.userId}/admin/write">글작성</a></li>
				</ul>
				
		      	<table class="admin-cat">
		      		<thead>
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>날짜</th>
		      			<th>&nbsp;</th>      			
		      		</tr>
		      		</thead>
		      		
		      		<tbody class="category-tbody">
		      		<c:forEach items="${list}" var="vo" varStatus="status">
					<tr>
						<td>${vo.categoryNo}</td>
						<td>${vo.categoryName}</td>
						<td>${vo.postCount}</td>
						<td>${vo.categoryRegdate}</td>
						<td>
							<a class='category-delete-link' href='${pageContext.servletContext.contextPath}/api/category/categorydelete?categoryNo=${vo.categoryNo}&userId=${vo.userId}' ><img src='${pageContext.request.contextPath}/assets/images/delete.jpg' class='delete-img'></a>
						</td>
					</tr>
					</c:forEach> 
					</tbody>
							  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			<input type="hidden" name="userId" value="${authUser.userId}">
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" id="name-category-form" name="categoryName"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" id="contents-category-form" name="categoryDesc"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input id="btn-category-add" type="button" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>