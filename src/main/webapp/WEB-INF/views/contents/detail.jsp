<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세</title>
<script type="text/javascript">
	function cart() {
		if ('${sessionScope.id}' == '') {
			alert('먼저 로그인을 하세요');
			let url = '/member/login';
			url += '?contentsno=${dto.contentsno}';
			location.href = url;
			return;
		}
		//카트테이블에 등록하고 등록 확인 창 보여주기 (비동기)
	}
	function order() {
		if ('${sessionScope.id}' == '') {
			alert('먼저 로그인을 하세요');
			let url = '/member/login';
			url += '?contentsno=${dto.contentsno}';
			location.href = url;
			return;
		}
		//주문서 작성으로 이동 주문생성 (비동기)
	}
</script>
</head>
<body>
	<div class="container mt-3">
		<div class="row">
			<div class="col-sm-3">
				<h4>
					<i class="bi bi-box2-heart"></i> 상품 정보
				</h4>
				<img class="img-rounded" src="/contents/storage/${dto.filename}"
					style="width: 250px">
				<div class="caption">
					<p>상품명:${dto.pname}</p>
					<p>${dto.detail }</p>
				</div>
			</div>
			<div class="col-sm-6">
				<h4>
					<i class="bi bi-rulers"></i> 사이즈 및 수량
				</h4>
				<ul class="list-group">
					<li class="list-group-item">사이즈 : <c:choose>
							<c:when test="${dto.cateno==1}">
								<select class="form-select">
									<option selected>사이즈 선택</option>
									<option value="L">Large</option>
									<option value="M">Medium</option>
									<option value="S">Small</option>
								</select>
							</c:when>
							<c:when test="${dto.cateno==2 }">
								<select class="form-select" disabled="disabled">
									<option selected>사이즈 선택</option>
								</select>
							</c:when>
							<c:when test="${dto.cateno==3 }">
								<select class="form-select">
									<option selected>사이즈 선택</option>
									<option value="220">220</option>
									<option value="230">230</option>
									<option value="240">240</option>
									<option value="250">250</option>
									<option value="260">260</option>
								</select>
							</c:when>
						</c:choose>
					<li class="list-group-item">가격 : ${dto.price }
					<li class="list-group-item">재고 : ${dto.stock }
					<li class="list-group-item">수량 : <input type="number"
						name="quantity" min=0 max=20 value="1">
					<li class="list-group-item"><a href="javascript:cart()"> <i
							class="bi bi-cart4 fs-5" title="장바구니 담기"></i></a> <a
						href="javascript:order()"> <i
							class="bi bi-bag-heart-fill fs-5" title="주문하기"></i></a> <a
						href="javascript:history.back()"> <i
							class="bi bi-arrow-return-left fs-5"></i>
					</a>
				</ul>
			</div>
		</div>

	</div>
	<!--container-->
</body>
</html>