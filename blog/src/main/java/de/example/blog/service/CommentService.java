package de.example.blog.service;

import de.example.blog.entity.Comment;

public interface CommentService {

    public void createComment(String postUrl, Comment comment);
}
