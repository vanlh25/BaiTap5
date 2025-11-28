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
    @Column(name="VideoId", nullable = false)
    private int videoId;

    @Column(name = "Title",length = 255)
    private String title;

    @Column(name = "Poster", columnDefinition = "TEXT")
    private String poster;

    @Column(name = "Views")
    private int views;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Active")
    private Boolean active;

    // Mỗi video thuộc 1 category
    @ManyToOne
    @JoinColumn(name="CategoryId")
    private Category category;
}
