package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="videos")
@NamedQuery(name="Video.findAll", query="SELECT v FROM Video v")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="videoId")
    private int id;

    @Column(length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String poster;

    @Column
    private int views;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Boolean active;

    // Mỗi video thuộc 1 category
    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category;
}
