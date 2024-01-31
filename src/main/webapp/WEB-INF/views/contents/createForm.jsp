<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품등록</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ckeditor/ckeditor.js">	
</script>
<script type="text/JavaScript">

	function checkIn(f) {
		if (f.pname.value == "") {
			alert("상품명을 입력하세요");
			f.pname.focus()
			return false;
		}
		if (f.price.value == "") {
			alert("가격을 입력하세요");
			f.price.focus();
			return false;
		}
		if (CKEDITOR.instances['detail'].getData() == '') {
			window.alert('내용을 입력해 주세요.');
			CKEDITOR.instances['detail'].focus();
			return false;
		}
		if (f.stock.value == "") {
			alert("재고를 입력하세요");
			f.stock.focus();
			return false;
		}
	}
</script>
</head>
<body>
	<div class="container mt-3">
		<h3 class="">상품 등록</h3>
		<form class="" action="/contents/create" method="post"
			enctype="multipart/form-data">

			<div class="mb-3 mt-3">
				<label for="cateno">상품분류</label>
				<div class="col-sm-6">
					<select class="form-select" name="cateno">
						<option value="1" <c:if test= "${cateno==1}"> selected </c:if>>Jean</option>
						<option value="2" <c:if test= "${cateno==2}"> selected </c:if>>Bag</option>
						<option value="3" <c:if test= "${cateno==3}"> selected </c:if>>Shoes</option>
					</select>
				</div>
			</div>
			<div class="mb-3 mt-3">
				<label for="pname">상품명</label>
				<div class="col-sm-8">
					<input type="text" name="pname" id="pname" class="form-control">
				</div>
			</div>
			<div class="mb-3 mt-3">
				<label for="price">가격</label>
				<div class="col-sm-8">
					<input type="text" name="price" id="price" class="form-control">
				</div>
			</div>

			<div class="mb-3 mt-3">
				<label for="detail">상세정보</label>
				<div class="col-sm-8">
					<textarea rows="12" cols="7" id="detail" name="detail"
						class="form-control"></textarea>
				</div>
			</div>

			<div class="mb-3 mt-3">
				<label for="stock">재고</label>
				<div class="col-sm-6">
					<input type="text" name="stock" id="stock" class="form-control">
				</div>
			</div>
			<div class="mb-3 mt-3">
				<label for="filenameMF">상품이미지</label>
				<div class="col-sm-6">
					<input type="file" name="filenameMF" id="filenameMF"
						class="form-control">
				</div>
			</div>

			<button class="btn btn-primary">등록</button>

		</form>
	</div>
</body>
</html>