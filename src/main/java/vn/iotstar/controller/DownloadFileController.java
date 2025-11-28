package vn.iotstar.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DownloadFileController {

    @Value("${file.upload-dir}") // đọc từ application.properties
    private String uploadDir;

    @GetMapping("/image")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fname") String fileName) {
        try {
            // Chuẩn hóa đường dẫn, tránh lỗi ../
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build(); // trả 404 nếu không có file
            }

            // Xác định kiểu file mặc định
            String contentType = "application/octet-stream";

            // Trả file
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
