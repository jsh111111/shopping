<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 처리</title>
</head>
<body>
	<div class="container mt-5 mx-9">

		<h2 class='mb-2'>로그인</h2>
		<form action="/member/login" method="post">
			<input type="hidden" name="contentsno" value="${param.contentsno}">
			<input type="hidden" name="cateno" value="${param.cateno}">
			<input type="hidden" name="nowPage" value="${param.nowPage}">
			<input type="hidden" name="col" value="${param.col}"> <input
				type="hidden" name="word" value="${param.word}">

			<div class="mb-3 mt-3">
				<label for="id">아이디 </label>
				<div class="col-sm-5">
					<input type="text" class="form-control col-sm-6" id="id"
						placeholder="Enter id" name="id" required="required"
						value='${c_id_val}'>
				</div>
			</div>
			<div class="mb-3 mt-3">
				<label for="pwd">비밀번호</label>
				<div class="col-sm-5">
					<input type="password" class="form-control" id="pwd"
						placeholder="Enter password" name="passwd" required="required">
				</div>
			</div>
			<div class="form-check mb-3">

				<label class="form-check-label"> <c:choose>
						<c:when test="${c_id =='Y'}">
							<input class="form-check-input" type="checkbox" name="c_id"
								value="Y" checked="checked"> Remember ID
          				</c:when>
						<c:otherwise>
							<input class="form-check-input" type="checkbox" name="c_id"
								value="Y"> Remember ID
          				</c:otherwise>
					</c:choose>
				</label>
			</div>


			<button type="submit" class="btn btn-outline-info">로그인</button>
			<button type="button" class="btn btn-outline-success"
				onclick="location.href='agree'">회원가입</button>
			<button type="button" class="btn btn-outline-dark"
				data-bs-toggle="collapse" data-bs-target="#demo1">아이디 찾기</button>
			<button type="button" class="btn btn-outline-dark"
				data-bs-toggle="collapse" data-bs-target="#demo2">패스워드 찾기</button>

			<!-- 비동기, path로 사용 -->
			<div id="demo1" class="collapse">
				<div class="mb-3 mt-3">
					<label for="mname">이름</label>
					<div class="col-sm-5">
						<input type="text" class="form-control col-sm-6" id="mname"
							placeholder="Enter name" name="mname">
					</div>
				</div>
				<div class="mb-3 mt-3">
					<label for="email">이메일</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="email"
							placeholder="Enter email" name="email">
					</div>
				</div>
				<button type="submit" class="btn btn-outline-info">확인</button>
			</div>

			<div id="demo2" class="collapse">
				<div class="mb-3 mt-3">
					<label for="mname">이름 </label>
					<div class="col-sm-5">
						<input type="text" class="form-control col-sm-6" id="mname"
							placeholder="Enter name" name="mname">
					</div>
				</div>
				<div class="mb-3 mt-3">
					<label for="id">아이디</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="id"
							placeholder="Enter id" name="id">
					</div>
					<button type="submit" class="btn btn-outline-info">확인</button>
				</div>
			</div>

		</form>

	</div>
</body>
</html>