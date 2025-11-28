package vn.iotstar.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoModel {

    private int videoId;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    // Tên file lưu vào DB
    private String poster;

    // File upload từ form
    private MultipartFile posterFile;

    private int views;

    private String description;

    private int active;

    private int categoryId; // foreign key

    private Boolean isEdit = false;
}
