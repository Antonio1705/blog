package de.example.blog.service;

import de.example.blog.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {
    List<PostDto> findAllPosts();

    PostDto createPost(PostDto postDto);

    PostDto findPostById(Long postId);

    PostDto updatePost(PostDto postDto);

    void deletePost(Long id);

    PostDto findPostByUrl(String url);

    List<PostDto> searchPosts(String query);
}
