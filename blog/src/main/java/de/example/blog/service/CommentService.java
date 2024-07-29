package de.example.blog.service;

import de.example.blog.entity.Comment;

import java.util.List;

public interface CommentService {

    public void createComment(String postUrl, Comment comment);

    List<Comment> findAllComments();

    void deleteCommentById(Long commentId);

    List<Comment> findCommentByPost();

    Comment newComment();
}
