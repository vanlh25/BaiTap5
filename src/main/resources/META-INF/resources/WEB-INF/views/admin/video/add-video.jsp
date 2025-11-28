<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="admin-taskbar mb-4">
    <a href="${pageContext.request.contextPath}/admin/home" class="btn btn-secondary">Home</a>
    <a href="${pageContext.request.contextPath}/profile" class="btn btn-secondary">Profile</a>
    <a href="${pageContext.request.contextPath}/admin/video/list" class="btn btn-primary active">Video</a>
</div>

<div class="admin-content mt-5">
    <h2>Thêm Video</h2>

    <form action="${pageContext.request.contextPath}/admin/video/add" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label>Title:</label>
            <input type="text" name="title" class="form-control" required>
        </div>

        <div class="form-group">
            <label>Category:</label>
            <select name="categoryId" class="form-control" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.categoryId}">${cat.categoryName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Status:</label>
            <select name="active" class="form-control">
                <option value="1">Hoạt động</option>
                <option value="0">Khóa</option>
            </select>
        </div>

        <div class="form-group">
            <label>Poster:</label>
            <input type="file" name="posterFile" class="form-control" onchange="previewImage(event)">
            <br>
            <img id="preview" src="#" style="display:none; width:100px; height:100px; object-fit:cover; border-radius:4px;">
        </div>

        <div class="form-group">
            <label>Description:</label>
            <textarea name="description" class="form-control" rows="4"></textarea>
        </div>

        <div class="form-group mt-3">
            <button type="submit" class="btn btn-success">Thêm Video</button>
            <a href="${pageContext.request.contextPath}/admin/video/list" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>

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

<style>
.form-group {
    margin-bottom: 15px;
}

.form-control {
    width: 50%;
}
</style>
