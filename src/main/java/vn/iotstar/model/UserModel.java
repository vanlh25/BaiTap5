package vn.iotstar.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @NotEmpty(message = "Username cannot be empty")
    private String userName;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    private String phone;

    private String fullName; 

    private String email;

    private boolean admin;

    private int active;

    private String images;

    private Boolean isEdit = false;

    // Thêm trường để bind Remember Me
    private boolean rememberMe;
}
