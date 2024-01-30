<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<div class="container mt-3">

		<div class="toast show">
			<div class="toast-header">
				<strong class="me-auto">오류 메세지 !</strong>
				<button type="button" class="btn-close" data-bs-dismiss="toast"
					onclick="history.back()"></button>
			</div>
			<div class="toast-body">
				<p>에러가 발생했습니다.</p>
				<br>
				<p>잠시후 다시 확인하세요</p>
			</div>
		</div>
	</div>

</body>
</html>