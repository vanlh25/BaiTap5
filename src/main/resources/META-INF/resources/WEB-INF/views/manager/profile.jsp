<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="manager-taskbar mb-4">
    <!-- Home -->
    <a href="${pageContext.request.contextPath}/manager/home"
       class="btn btn-secondary">Home</a>
    <!-- Profile luôn active -->
    <a href="${pageContext.request.contextPath}/profile"
       class="btn btn-primary active">Profile</a>
    <!-- Category -->
    <a href="${pageContext.request.contextPath}/manager/category/list"
       class="btn btn-info">Category</a>
</div>

<div class="manager-content mt-5">
    <h2>Thông tin cá nhân</h2>
    <form action="<c:url value='/profile'/>" method="post" enctype="multipart/form-data" class="text-left col-md-6 offset-md-3">
        <div class="form-group">
            <label for="fullName">Họ và tên:</label>
            <input type="text" class="form-control" id="fullName" name="fullName" value="${user.fullName}" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
        </div>

        <div class="form-group">
            <label for="phone">Số điện thoại:</label>
            <input type="text" class="form-control" id="phone" name="phone" value="${user.phone}" required>
        </div>

        <div class="form-group">
            <label>Ảnh hiện tại:</label><br>
            <img id="preview" src="<c:url value='/image?fname=${user.images != null ? user.images : "default.png"}'/>" 
                 style="width:100px; height:100px; object-fit:cover; border-radius:4px; margin-bottom:10px;">
        </div>

        <div class="form-group">
            <label for="images">Thay đổi ảnh:</label>
            <input type="file" class="form-control-file" id="images" name="images" accept="image/*" onchange="previewNewImage(event)">
            <input type="hidden" name="oldImage" value="${user.images}">
        </div>

        <button type="submit" class="btn btn-primary mt-3">Cập nhật</button>
    </form>
</div>

<script>
function previewNewImage(event) {
    const preview = document.getElementById('preview');
    const file = event.target.files[0];
    if(file) {
        preview.src = URL.createObjectURL(file);
    }
}
</script>

<style>
.manager-taskbar .btn {
    margin-right: 10px;
    transition: all 0.2s ease;
    font-size: 16px;
    padding: 10px 20px;
}
.manager-taskbar .btn.active {
    transform: translateY(-2px);
    box-shadow: 0 3px 8px rgba(0,0,0,0.25);
}
.manager-taskbar .btn:hover {
    opacity: 0.9;
}

.manager-content h2 {
    color: #333;
    margin-bottom: 30px;
}

/* Form styling */
.manager-content .form-group {
    margin-bottom: 15px;
}
.manager-content .form-control {
    width: 100%;
}
</style>
