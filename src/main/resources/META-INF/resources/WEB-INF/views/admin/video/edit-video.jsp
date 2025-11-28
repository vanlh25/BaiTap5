<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="admin-taskbar mb-4">
    <a href="${pageContext.request.contextPath}/admin/home" class="btn btn-secondary">Home</a>
    <a href="${pageContext.request.contextPath}/profile" class="btn btn-secondary">Profile</a>
    <a href="${pageContext.request.contextPath}/admin/video/list" class="btn btn-primary active">Video</a>
</div>

<div class="admin-content mt-5">
    <h2>Sửa Video</h2>

    <form action="${pageContext.request.contextPath}/admin/video/edit" method="post" enctype="multipart/form-data">
        <input type="hidden" name="videoId" value="${video.videoId}">
        <input type="hidden" name="oldPoster" value="${video.poster}">

        <div class="form-group">
            <label>Title:</label>
            <input type="text" name="title" class="form-control" value="${video.title}" required>
        </div>

        <div class="form-group">
            <label>Category:</label>
            <select name="categoryId" class="form-control" required>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.categoryId}" <c:if test="${video.categoryId == cat.categoryId}">selected</c:if>>
                        ${cat.categoryName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Status:</label>
            <select name="active" class="form-control">
                <option value="1" <c:if test="${video.active == 1}">selected</c:if>>Hoạt động</option>
                <option value="0" <c:if test="${video.active == 0}">selected</c:if>>Khóa</option>
            </select>
        </div>

        <div class="form-group">
            <label>Poster hiện tại:</label><br>
            <c:choose>
                <c:when test="${not empty video.poster}">
                    <img id="preview" src="${pageContext.request.contextPath}/image?fname=${video.poster}"
                         style="width:100px; height:100px; object-fit:cover; border-radius:4px;">
                </c:when>
                <c:otherwise>
                    <img id="preview" src="#" style="display:none; width:100px; height:100px; object-fit:cover; border-radius:4px;">
                </c:otherwise>
            </c:choose>
        </div>

        <div class="form-group">
            <label>Thay đổi poster:</label>
            <input type="file" name="posterFile" class="form-control" onchange="previewImage(event)">
        </div>

        <div class="form-group">
            <label>Description:</label>
            <textarea name="description" class="form-control" rows="4">${video.description}</textarea>
        </div>

        <div class="form-group mt-3">
            <button type="submit" class="btn btn-primary">Cập nhật Video</button>
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
