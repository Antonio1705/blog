package de.example.blog.service.implementation;

import de.example.blog.dto.PostDto;
import de.example.blog.entity.Post;
import de.example.blog.mapper.PostMapper;
import de.example.blog.repository.PostRepository;
import de.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostMapper postMapper;

    @Override
    public List<PostDto> findAllPosts() {
        List<PostDto> postDtoList = postRepository.findAll().stream().map(post -> postMapper.mapToPostDto(post)).toList();

        return postDtoList;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        try {
            Post newPost = postMapper.mapToPostEntity(postDto);
            postRepository.save(newPost);
            return postDto;
        }catch (Exception e){
            throw new RuntimeException("Error: "+ e.getMessage());
        }
    }
}
