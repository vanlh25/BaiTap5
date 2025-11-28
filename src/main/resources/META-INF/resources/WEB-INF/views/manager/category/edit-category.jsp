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
	<h2>Sửa Category</h2>

	<form action="${pageContext.request.contextPath}/manager/category/edit"
		method="post" enctype="multipart/form-data">
		<input type="hidden" name="action" value="edit"> <input
			type="hidden" name="categoryId" value="${category.categoryId}">
		<input type="hidden" name="oldImage" value="${category.images}">

		<div class="form-group">
			<label>Tên danh mục:</label> <input type="text" name="categoryName"
				class="form-control" value="${category.categoryName}" required>
		</div>

		<div class="form-group">
			<label>Mã danh mục:</label> <input type="text" name="categoryCode"
				class="form-control" value="${category.categoryCode}" required>
		</div>

		<div class="form-group">
			<label>Trạng thái:</label> <select name="status" class="form-control">
				<option value="1"
					<c:if test="${category.status == 1}">selected</c:if>>Hoạt
					động</option>
				<option value="0"
					<c:if test="${category.status == 0}">selected</c:if>>Khóa</option>
			</select>
		</div>

		<div class="form-group">
			<label>Hình ảnh hiện tại:</label><br>
			<c:choose>
				<c:when test="${not empty category.images}">
					<img
						src="${pageContext.request.contextPath}/uploads/${category.images}"
						alt="${category.categoryName}"
						style="width: 80px; height: 80px; object-fit: cover;">
				</c:when>
				<c:otherwise>Không có hình</c:otherwise>
			</c:choose>
		</div>

		<div class="form-group">
			<label>Thay đổi hình ảnh:</label> <input type="file" name="images"
				class="form-control">
		</div>

		<div class="form-group mt-3">
			<button type="submit" class="btn btn-primary">Cập nhật
				Category</button>
			<a href="${pageContext.request.contextPath}/manager/category/list"
				class="btn btn-secondary">Hủy</a>
		</div>
	</form>

</div>

<style>
.form-group {
	margin-bottom: 15px;
}

.form-control {
	width: 50%;
}
</style>
