<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="manager-taskbar mb-4">
	<a href="${pageContext.request.contextPath}/manager/home"
		class="btn btn-secondary">Home</a> <a
		href="${pageContext.request.contextPath}/profile"
		class="btn btn-secondary">Profile</a> <a
		href="${pageContext.request.contextPath}/manager/category/list"
		class="btn btn-primary active">Category</a>
</div>

<div class="manager-content mt-5">
	<h2>Danh sách Category</h2>

	<!-- Nút Thêm Category -->
	<div style="margin-bottom: 15px; text-align: center;">
		<a href="${pageContext.request.contextPath}/manager/category/add"
			class="btn btn-success">Thêm Category</a>
	</div>

	<table class="table category-table mx-auto">
		<thead>
			<tr>
				<th>#</th>
				<th>Tên danh mục</th>
				<th>Mã danh mục</th>
				<th>Hình ảnh</th>
				<th>Trạng thái</th>
				<th>Hành động</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="cat" items="${categories}" varStatus="loop">
				<tr>
					<td>${loop.index + 1}</td>
					<td>${cat.categoryName}</td>
					<td>${cat.categoryCode}</td>
					<td><c:choose>
							<c:when test="${not empty cat.images}">
								<img
									src="${pageContext.request.contextPath}/image?fname=${cat.images}"
									alt="${cat.categoryName}" class="category-img">
							</c:when>
							<c:otherwise>Không có hình</c:otherwise>
						</c:choose></td>
					<td><c:choose>
							<c:when test="${cat.status == 1}">
								<span class="text-success font-weight-bold">Hoạt động</span>
							</c:when>
							<c:otherwise>
								<span class="text-danger font-weight-bold">Khóa</span>
							</c:otherwise>
						</c:choose></td>
					<td><a
						href="${pageContext.request.contextPath}/manager/category/edit?id=${cat.categoryId}"
						class="btn btn-primary btn-sm">Sửa</a> <a
						href="${pageContext.request.contextPath}/manager/category/delete?id=${cat.categoryId}"
						class="btn btn-danger btn-sm"
						onclick="return confirm('Bạn có chắc muốn xóa ${cat.categoryName}?');">Xóa</a>
					</td>
				</tr>
			</c:forEach>

			<c:if test="${empty categories}">
				<tr>
					<td colspan="6" style="text-align: center;">Không có danh mục
						nào!</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</div>

<style>
.manager-taskbar .btn {
	margin-right: 10px;
	font-size: 16px;
	padding: 10px 20px;
	transition: all 0.2s ease;
}

.manager-taskbar .btn.active {
	transform: translateY(-2px);
	box-shadow: 0 3px 8px rgba(0, 0, 0, 0.25);
}

.manager-taskbar .btn:hover {
	opacity: 0.9;
}

.manager-content h2 {
	color: #333;
	margin-bottom: 20px;
	text-align: center;
}

.category-table {
	width: 90%;
	border-collapse: separate;
	border-spacing: 0 8px;
	background-color: #fff;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}

.category-table th, .category-table td {
	padding: 12px 15px;
	text-align: left;
	vertical-align: middle;
}

.category-table th {
	background-color: #f0f0f0;
	font-weight: bold;
	border-radius: 6px;
}

.category-table tr {
	background-color: #fafafa;
	border-radius: 6px;
}

.category-img {
	width: 60px;
	height: 60px;
	object-fit: cover;
	border-radius: 4px;
}
</style>
