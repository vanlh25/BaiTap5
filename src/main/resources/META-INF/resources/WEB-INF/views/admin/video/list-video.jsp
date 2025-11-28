<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="admin-taskbar mb-4">
    <a href="${pageContext.request.contextPath}/admin/home" class="btn btn-secondary">Home</a>
    <a href="${pageContext.request.contextPath}/profile" class="btn btn-secondary">Profile</a>
    <a href="${pageContext.request.contextPath}/admin/video/list" class="btn btn-primary active">Video</a>
</div>

<div class="admin-content mt-5">
    <h2>Danh sách Video</h2>

    <div style="margin-bottom: 15px; display:flex; justify-content: space-between; align-items: center;">
        <a href="${pageContext.request.contextPath}/admin/video/add" class="btn btn-success">Thêm Video</a>
        <form method="get" action="${pageContext.request.contextPath}/admin/video/list">
            <input type="text" name="keyword" placeholder="Tìm kiếm..." value="${keyword}" class="form-control" style="display:inline-block; width: 200px;">
            <button type="submit" class="btn btn-primary">Search</button>
        </form>
    </div>

    <table class="table category-table mx-auto">
        <thead>
            <tr>
                <th>#</th>
                <th>Title</th>
                <th>Description</th>
                <th>Poster</th>
                <th>Views</th>
                <th>Active</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="video" items="${videos}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${video.title}</td>
                    <td>${video.description}</td>
                    <td>
                        <c:if test="${not empty video.poster}">
                            <img src="${pageContext.request.contextPath}/image?fname=${video.poster}" alt="${video.title}" style="width:80px; height:80px; object-fit:cover;">
                        </c:if>
                    </td>
                    <td>${video.views}</td>
                    <td>
                        <c:choose>
                            <c:when test="${video.active}">Hoạt động</c:when>
                            <c:otherwise>Khóa</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/video/edit/${video.videoId}" class="btn btn-primary btn-sm">Sửa</a>
                        <a href="${pageContext.request.contextPath}/admin/video/delete/${video.videoId}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa ${video.title}?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty videos}">
                <tr>
                    <td colspan="7" style="text-align:center;">Không có video nào!</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>
