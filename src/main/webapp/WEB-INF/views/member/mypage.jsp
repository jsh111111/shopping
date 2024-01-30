<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bootstrap Example</title>
<script type="text/javascript">
	function updateFile() {
		let url = "/member/updateFile";
		url += "?fname=${dto.fname}";
		//alert(url);
		location.href = url;
	}
</script>
</head>
<body>
	<div class="container mt-3">
		<div class="row">
			<div class="col-sm-3">
				<h4>
					<i class="bi bi-people-fill"></i> 나의 정보
				</h4>
				<img class="img-rounded" src="/member/storage/${dto.fname}"
					style="width: 280px">
				<p>
					<a href="javascript:updateFile()">사진수정</a>
				</p>
				<p>ID:${dto.id}, 성명:${dto.mname}</p>
				<p>배송지:(${dto.zipcode })${dto.address1 } ${dto.address2}</p>
			</div>

			<div class="col-sm-6">
				<h4>
					<i class="fa-solid fa-shirt"></i> 주문 내역 및 리뷰 작성
				</h4>
				<ul class="list-group m-3">
					<c:choose>
						<c:when test="${empty dto.list }">주문한 상품이 없습니다.</c:when>

						<c:otherwise>
							<c:forEach var="order" items="${dto.list}">
								<p class='h5 mt-3 mb-auto'>
									<i class="bi bi-calendar-date-fill"></i>
									${fn:substring(order.odate,0,10)}
								<p>
								<li class="list-group-item">결제금액 : ${order.total} 원, 주문상태 :
									${order.ostate} <c:forEach var="detail" items="${order.list}">
										<li class="list-group-item">${detail.pname},
											${detail.quantity}개 <a
											href="review/create/{order.contentsno}"><span
												class="badge rounded-pill bg-dark">Rivew</span></a>
										</li>
									</c:forEach>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>