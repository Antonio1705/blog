package de.example.blog.service.implementation;

import de.example.blog.entity.Comment;
import de.example.blog.entity.Post;
import de.example.blog.entity.User;
import de.example.blog.repository.CommentRepository;
import de.example.blog.repository.PostRepository;
import de.example.blog.repository.UserRepository;
import de.example.blog.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    public CommentServiceImplementation(CommentRepository commentRepository,PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void createComment(String postUrl, Comment comment) {

        Post post = postRepository.findByUrl(postUrl).get();
        comment.setPost(post);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> findCommentByPost() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Error: No authenticated user found");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email + "<<<<email"));

        List<Comment> comments = commentRepository.findByPostCreatedById(user.getId());

        return comments;
    }
}
