package vn.iotstar.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoModel {

    private int videoId;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    private String poster;

    private int views;

    private String description;

    private int active;

    private int categoryId; // foreign key

    private Boolean isEdit = false;
}
