package de.example.blog.repository;

import de.example.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<Post> findByUrl(String url);

    @Query("SELECT p FROM Post p WHERE p.title LIKE CONCAT('%', :query, '%') OR p.shortDescription LIKE CONCAT('%', :query, '%')")
    List<Post> searchPosts(String query);

}
