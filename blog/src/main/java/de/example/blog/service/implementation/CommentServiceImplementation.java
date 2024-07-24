package de.example.blog.service.implementation;

import de.example.blog.entity.Comment;
import de.example.blog.entity.Post;
import de.example.blog.repository.CommentRepository;
import de.example.blog.repository.PostRepository;
import de.example.blog.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImplementation(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
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
}
