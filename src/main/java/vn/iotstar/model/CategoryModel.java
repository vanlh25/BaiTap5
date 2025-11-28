package vn.iotstar.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {

    private int categoryId;

    @NotEmpty(message = "Category name cannot be empty")
    private String categoryName;

    @NotEmpty(message = "Category code cannot be empty")
    private String categoryCode;

    // Tên file lưu vào DB
    private String images;

    // File upload từ form
    private MultipartFile imagesFile;

    private int status;

    private String userName; 

    private Boolean isEdit = false;
}
