<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath }" />
<c:choose>
	<c:when
		test="${not empty sessionScope.id && sessionScope.grade == 'A'}">
		<c:set var="str">관리자 페이지 입니다.</c:set>
	</c:when>
	<c:when
		test="${not empty sessionScope.id && sessionScope.grade != 'A'}">
		<c:set var='str'>안녕하세요  ${sessionScope.id }(${sessionScope.mname })님!</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="str">기본 페이지 입니다.</c:set>
	</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html lang="en">
<head>
<title>shop</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.0.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css">
<script src="https://kit.fontawesome.com/71c72323b0.js"
	crossorigin="anonymous"></script>
<style type="text/css">
#grade {
	color: white;
}
</style>
<script type="text/javascript">
    function getCategory(){
        return fetch("/contents/getCategory")
                        .then(response => response.json());
    }
    window.onload=function(){
          getCategory()
                .then(data => {  
                   console.log(data);
               for (let i = 0; i < data.length; i++) {
                  $('#pmenu').append("<li><a  class='dropdown-item' href='/contents/mainlist/"+data[i].cateno+"'>" + data[i].catename + "</a></li>");
               }                  
                        
                }).catch(console.log);
    };//페이지로딩
        
</script>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="/"><i class="bi bi-shop"> </i></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="collapsibleNavbar">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="/"><i
							class="fa-solid fa-shirt"></i></a></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" role="button"
						data-bs-toggle="dropdown">Product</a>
						<ul class="dropdown-menu" id="pmenu"></ul></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" role="button"
						data-bs-toggle="dropdown">Community</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="${root}/member/mypage">Mypage</a></li>
							<li><a class="dropdown-item" href="${root}/contents/detail">Review</a></li>
							<li><a class="dropdown-item" href="${root}/notice/list">Notice</a></li>
							<li><a class="dropdown-item" href="#">Q&A</a></li>
						</ul></li>
					<li class="nav-item"><a class="nav-link" id="grade" href="#"><i
							class="fa-solid fa-cat"></i> ${str}</a></li>
				</ul>
				<ul class="navbar-nav ms-auto">
					<c:choose>
						<c:when test="${empty sessionScope.id }">
							<li class="nav-item"><a href="${root}/member/agree"
								class="nav-link m-2"><i class="fa-solid fa-user-plus"></i>
									Sign Up</a></li>
							<li class="nav-item"><a href="${root}/member/login"
								class="nav-link m-2 "><i
									class="fa-solid fa-arrow-right-to-bracket"></i> Login</a></li>

						</c:when>
						<c:when
							test="${not empty sessionScope.id && sessionScope.grade == 'A'}">
							<li class="nav-item"><a href="${root}/admin/contents/create"
								class="nav-link m-2 "><i class="fa-brands fa-shopify"></i>
									상품등록</a></li>
							<li class="nav-item"><a href="${root}/contents/list"
								class="nav-link m-2 "><i class="fa-brands fa-shopify"></i>
									상품목록</a></li>
							<li class="nav-item"><a href="${root}/admin/member/list"
								class="nav-link m-2 "><i class="fa-solid fa-user-group"></i>
									회원목록</a></li>
							<li class="nav-item"><a href="${root}/member/logout"
								class="nav-link m-2 "><i
									class="fa-solid fa-arrow-right-from-bracket"></i> Logout</a></li>

						</c:when>
						<c:otherwise>
							<li class="nav-item"><a href="${root}/cart/list"
								class="nav-link m-2 "><i class="bi bi-cart4"></i> Cart</a></li>
							<li class="nav-item"><a href="${root}/member/update/${sessionScope.id}/"
								class="nav-link m-2 "><i class="fa-solid fa-user-pen"></i>
									정보 수정</a></li>
							<li class="nav-item"><a href="${root}/member/logout"
								class="nav-link m-2 "><i
									class="fa-solid fa-arrow-right-from-bracket"></i> Logout</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>