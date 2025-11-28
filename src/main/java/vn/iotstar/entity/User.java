package vn.iotstar.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="username", length=100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 20)
    private String phone;

    @Column(name="fullname", columnDefinition = "NVARCHAR(255)")
    private String fullname;

    @Column(length = 255)
    private String email;

    @Column
    private Boolean admin;

    @Column
    private Boolean active;

    @Column(columnDefinition = "TEXT")
    private String images;

    // User có nhiều Category
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Category> categories;
}
