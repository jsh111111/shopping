<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<script type="text/javascript">
	function read(id) {
		var url = "${root}/admin/member/read";
		url += "?id=" + id;

		location.href = url;

	}
</script>
</head>
<body>
	<div class="container mt-3">
		<h2>회원목록</h2>
		${admin} <br>
		<form method="post" action="list">
			<div class="row">
				<div class='col'>
					<select class="form-select" name="col">
						<option value="mname"
							<c:if test="${col=='mname' }">selected</c:if>>성명</option>
						<option value="id" <c:if test="${col=='id' }">selected</c:if>>아이디</option>
						<option value="email"
							<c:if test="${col=='email' }">selected</c:if>>이메일</option>
						<option value="total"
							<c:if test="${col=='total' }">selected</c:if>>전체출력</option>
					</select>
				</div>

				<div class="col">
					<input type="text" class="form-control" name="word"
						required="required" value="${word}">
				</div>
				<div class='col'>
					<button class="btn btn-info">검색</button>
					<button class="btn btn-info" type="button"
						onclick="location.href='create'">등록</button>
				</div>
			</div>
		</form>

		<br>

		<c:forEach var="dto" items="${list}">
			<table class="table table-bordered">
				<tr>
					<td rowspan="5" class="col-sm-2"><img
						src="${root}/member/storage/${dto.fname}" class="img-rounded"
						width="200px" height="200px"></td>
					<th class="col-sm-2">아이디</th>
					<td class="col-sm-8"><a href="javascript:read('${dto.id}')">${dto.id}</a></td>
				</tr>
				<tr>
					<th class="col-sm-2">성명</th>
					<td class="col-sm-8">${dto.mname}</td>
				</tr>
				<tr>
					<th class="col-sm-2">전화번호</th>
					<td class="col-sm-8">${dto.tel}</td>
				</tr>
				<tr>
					<th class="col-sm-2">이메일</th>
					<td class="col-sm-8">${dto.email}</td>
				</tr>
				<tr>
					<th class="col-sm-2">주소</th>
					<td class="col-sm-8">(${dto.zipcode}) ${dto.address1}
						${dto.address2}</td>
				</tr>
			</table>
		</c:forEach>
		${paging}

	</div>
</body>
</html>