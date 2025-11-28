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
    @Column(name="UserName", nullable = false, length=100)
    private String userName;

    @Column(name="Password", nullable = false, length = 255)
    private String password;

    @Column(name="Phone",length = 20)
    private String phone;

    @Column(name="FullName", columnDefinition = "NVARCHAR(255)")
    private String fullName;

    @Column(name="Email",length = 255)
    private String email;

    @Column(name="Admin")
    private Boolean admin;

    @Column(name = "Active")
    private Boolean active;

    @Column(name="Images",columnDefinition = "TEXT")
    private String images;

    // User có nhiều Category
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Category> categories;
}
