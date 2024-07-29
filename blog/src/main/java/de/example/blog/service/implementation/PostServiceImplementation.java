package de.example.blog.service.implementation;

import de.example.blog.dto.PostDto;
import de.example.blog.entity.Post;
import de.example.blog.entity.User;
import de.example.blog.mapper.PostMapper;
import de.example.blog.repository.PostRepository;
import de.example.blog.repository.UserRepository;
import de.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostMapper postMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PostDto> findAllPosts() {
        List<PostDto> postDtoList = postRepository.findAll().stream().map(post -> postMapper.mapToPostDto(post)).sorted(Comparator.comparing(PostDto::getId).reversed()).toList();

        return postDtoList;
    }

    @Transactional
    @Override
    public PostDto createPost(PostDto postDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Error: No authenticated user found");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email + "<<<<email"));
        Post newPost = postMapper.mapToPostEntity(postDto);
        newPost.setCreatedBy(user);
        postRepository.save(newPost);
        return postDto;

    }

    @Override
    public PostDto findPostById(Long postId) {
        try {
            Post post = postRepository.findById(postId).get();

            PostDto postDto = postMapper.mapToPostDto(post);
            return postDto;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }

    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(postDto.getId());

        if (optionalPost.isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new RuntimeException("Error: No authenticated user found");
            }
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email + "<<<<email"));


            Post post = optionalPost.get();
            post.setContent(postDto.getContent());
            post.setTitle(postDto.getTitle());
            post.setShortDescription(postDto.getShortDescription());
            post.setCreatedBy(user);
            // Ensure the `createdOn` field is not updated

            postRepository.save(post);

            return postDto;
        }
        throw new RuntimeException("server fehler ");
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto findPostByUrl(String url) {

        Optional<Post> byUrl = postRepository.findByUrl(url);

        if (byUrl.isPresent()) {
            PostDto postDto = postMapper.mapToPostDto(byUrl.get());
            return postDto;
        }

        throw new RuntimeException("Server error-----");
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDto> searchPosts(String query) {
        List<PostDto> postsDtoListOfSearch = postRepository.searchPosts(query).stream().map(post -> postMapper.mapToPostDto(post)).toList();

        return postsDtoListOfSearch;
    }

    @Override
    public List<PostDto> findPostByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Error: No authenticated user found");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email + "<<<<email"));

        Long userId = user.getId();
        System.out.println(userId + "<<<<<<<<<<<<<<<<<<<<<<<<<<ID");
        List<Post> postByUser = postRepository.findByCreatedById(userId);

        List<PostDto> postDtoByUser= postByUser.stream().map(post -> postMapper.mapToPostDto(post)).toList();

        return postDtoByUser;
    }
}
