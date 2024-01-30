<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>조회</title>
<script type="text/javascript">
	function updateFile() {
		var url = "updateFile/${dto.contentsno}/${dto.filename}";
		location.href = url;
	}
	function update() {
		var url = "update/${dto.contentsno}";
		location.href = url;
	}
	function del() {
		let url = "delete/${dto.contentsno}/${dto.filename}";
		if (confirm("상품을 삭제하시겠습니까?")) {
			location.href = url;
		}
	}
</script>
</head>
<body>
	<div class="container mt-3">
		<h3>조회</h3>
		<ul class="list-group">
			<li class="list-group-item"><img
				src="/contents/storage/${dto.filename}" class="img-rounded"
				width="150px" height="150px"></li>
			<li class="list-group-item">상품명: ${dto.pname}</li>
			<li class="list-group-item" style="height: 300px; overflow-y: scroll">${dto.detail}</li>
			<li class="list-group-item">가격: ${dto.price}</li>
			<li class="list-group-item">제고: ${dto.stock}</li>
			<li class="list-group-item">등록일: ${dto.rdate}</li>
		</ul>
		<br>
		<button type="button" class="btn btn-light"
			onclick="location.href='/admin/contents/create'">등록</button>
		<button type="button" class="btn btn-light" onclick="update()">수정</button>
		<button type="button" class="btn btn-light" onclick="updateFile()">사진수정</button>
		<button type="button" class="btn btn-light" onclick="del()">삭제</button>
		<button type="button" class="btn btn-light"
			onclick="location.href='./list'">목록</button>
	</div>
</body>
</html>