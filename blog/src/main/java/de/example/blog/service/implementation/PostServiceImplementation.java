package de.example.blog.service.implementation;

import de.example.blog.dto.PostDto;
import de.example.blog.entity.Post;
import de.example.blog.mapper.PostMapper;
import de.example.blog.repository.PostRepository;
import de.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostMapper postMapper;

    @Override
    public List<PostDto> findAllPosts() {
        List<PostDto> postDtoList = postRepository.findAll().stream().map(post -> postMapper.mapToPostDto(post)).sorted(Comparator.comparing(PostDto::getId).reversed()).toList();

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

    @Override
    public PostDto findPostById(Long postId) {
        try {
            Post post = postRepository.findById(postId).get();

            PostDto postDto = postMapper.mapToPostDto(post);
            return postDto;
        }catch (Exception e){
            throw new RuntimeException("Error: "+ e.getMessage());
        }

    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(postDto.getId());

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setContent(postDto.getContent());
            post.setTitle(postDto.getTitle());
            post.setShortDescription(postDto.getShortDescription());
            // Ensure the `createdOn` field is not updated

            postRepository.save(post);

            return postDto;
        }
        throw  new RuntimeException("server fehler ");
    }
}
