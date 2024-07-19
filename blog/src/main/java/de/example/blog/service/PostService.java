package de.example.blog.service;

import de.example.blog.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {
    List<PostDto> findAllPosts();

    PostDto createPost(PostDto postDto);
}
