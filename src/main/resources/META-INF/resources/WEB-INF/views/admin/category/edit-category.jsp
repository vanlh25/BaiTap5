<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="admin-taskbar mb-4">
    <a href="${pageContext.request.contextPath}/admin/home" class="btn btn-secondary">Home</a>
    <a href="${pageContext.request.contextPath}/profile" class="btn btn-secondary">Profile</a>
    <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-primary active">Category</a>
</div>

<div class="admin-content mt-5">
    <h2>Sửa Category</h2>

    <form action="${pageContext.request.contextPath}/admin/category/edit"
          method="post" enctype="multipart/form-data">
        <input type="hidden" name="categoryId" value="${category.categoryId}">

        <div class="form-group">
            <label>Tên danh mục:</label>
            <input type="text" name="categoryName" class="form-control"
                   value="${category.categoryName}" required>
        </div>

        <div class="form-group">
            <label>Mã danh mục:</label>
            <input type="text" name="categoryCode" class="form-control"
                   value="${category.categoryCode}" required>
        </div>

        <div class="form-group">
            <label>Trạng thái:</label>
            <select name="status" class="form-control">
                <option value="1" <c:if test="${category.status == 1}">selected</c:if>>Hoạt động</option>
                <option value="0" <c:if test="${category.status == 0}">selected</c:if>>Khóa</option>
            </select>
        </div>

        <div class="form-group">
            <label>Hình ảnh hiện tại:</label><br>
            <c:choose>
                <c:when test="${not empty category.images}">
                    <img id="preview" src="${pageContext.request.contextPath}/image?fname=${category.images}" 
                         alt="${category.categoryName}" class="category-img">
                </c:when>
                <c:otherwise>
                    <img id="preview" src="#" style="display:none;" class="category-img">
                </c:otherwise>
            </c:choose>
        </div>

        <div class="form-group">
            <label>Thay đổi hình ảnh:</label>
            <input type="file" name="imagesFile" class="form-control" onchange="previewImage(event)">
        </div>

        <div class="form-group mt-3">
            <button type="submit" class="btn btn-primary">Cập nhật Category</button>
            <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-secondary">Hủy</a>
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

.category-img {
    width: 80px;
    height: 80px;
    object-fit: cover;
    border-radius: 4px;
}
</style>

<script>
function previewImage(event) {
    const preview = document.getElementById('preview');
    const file = event.target.files[0];
    if(file) {
        preview.src = URL.createObjectURL(file);
        preview.style.display = 'block';
    } else {
        preview.src = '#';
        preview.style.display = 'none';
    }
}
</script>
