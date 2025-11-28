<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="admin-taskbar mb-4">
    <a href="${pageContext.request.contextPath}/admin/home" class="btn btn-secondary">Home</a>
    <a href="${pageContext.request.contextPath}/profile" class="btn btn-secondary">Profile</a>
    <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-primary active">Category</a>
</div>

<div class="admin-content mt-5">
    <h2>Thêm Category</h2>

    <form action="${pageContext.request.contextPath}/admin/category/add" 
          method="post" enctype="multipart/form-data">

        <div class="form-group">
            <label>Tên danh mục:</label>
            <input type="text" name="categoryName" class="form-control" required>
        </div>

        <div class="form-group">
            <label>Mã danh mục:</label>
            <input type="text" name="categoryCode" class="form-control" required>
        </div>

        <div class="form-group">
            <label>Trạng thái:</label>
            <select name="status" class="form-control">
                <option value="1">Hoạt động</option>
                <option value="0">Khóa</option>
            </select>
        </div>

        <div class="form-group">
            <label>Hình ảnh:</label>
            <input type="file" name="imagesFile" class="form-control" onchange="previewImage(event)">
        </div>

        <div class="form-group mt-2">
            <img id="preview" src="#" alt="Ảnh xem trước" 
                 style="display: none; width: 100px; height: 100px; object-fit: cover; border: 1px solid #ccc;">
        </div>

        <div class="form-group mt-3">
            <button type="submit" class="btn btn-success">Thêm Category</button>
            <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-secondary">Hủy</a>
        </div>

    </form>
</div>

<style>
.form-group { margin-bottom: 15px; }
.form-control { width: 50%; }
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
