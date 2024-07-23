package de.example.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "postblog")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Lob
    @Column(nullable = false, columnDefinition="Text")
    private String content;

    private String shortDescription;

    @CreationTimestamp
    private LocalDateTime createdOn ;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    //CascadeType.REMOVE heißt wenn einer diese entity löscht werden auch alle comments gelöscht die hier gespeichert sind
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();
}
