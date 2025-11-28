package vn.iotstar.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {

    private int categoryId;

    @NotEmpty(message = "Category name cannot be empty")
    private String categoryName;

    @NotEmpty(message = "Category code cannot be empty")
    private String categoryCode;

    private String images;

    private int status;

    private String userName; // foreign key

    private Boolean isEdit = false;
}
