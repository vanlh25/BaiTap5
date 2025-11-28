<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${pageTitle != null ? pageTitle : "Bài Tập JSP"}</title>
    <link rel="stylesheet" href="<c:url value='/webjars/bootstrap/5.3.2/css/bootstrap.min.css'/>">
</head>
<body>
    <!-- Header -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#">Thông tin sinh viên</a>
            <span class="navbar-text">
                Tên: Nguyễn Văn A | MSSV: 12345678
            </span>
        </div>
    </nav>

    <!-- Body: nơi chèn nội dung trang -->
    <div class="container mt-4">
        <jsp:include page="${sitemeshBody}"/>
    </div>

    <!-- Footer -->
    <footer class="bg-light text-center text-lg-start mt-4">
        <div class="text-center p-3 bg-dark text-white">
            <a href="https://github.com/username/baitap" target="_blank" class="text-white">GitHub Bài Tập</a>
        </div>
    </footer>

    <script src="<c:url value='/webjars/bootstrap/5.3.2/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>
