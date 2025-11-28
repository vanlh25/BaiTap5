package vn.iotstar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId", nullable = false, unique = true)
    private int id;

    @Column(name = "CategoryName", nullable = false, columnDefinition = "NVARCHAR(200)")
    private String categoryName;

    @Column(name = "CategoryCode", nullable = false, columnDefinition = "NVARCHAR(200)")
    private String code;

    @Column(name = "Images", columnDefinition = "NVARCHAR(1000)")
    private String images;

    @Column(name = "Status")
    private Integer status;

    // Category thuộc về 1 User
    @ManyToOne
    @JoinColumn(name = "UserName")
    private User user;

    // Category có nhiều video
    @OneToMany(mappedBy = "category")
    private List<Video> videos = new ArrayList<>();

    // Hàm tiện ích đồng bộ 2 chiều
    public Video addVideo(Video video) {
        this.videos.add(video);
        video.setCategory(this);
        return video;
    }

    public Video removeVideo(Video video) {
        this.videos.remove(video);
        video.setCategory(null);
        return video;
    }
}
