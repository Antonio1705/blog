package de.example.blog.controller;

import de.example.blog.dto.PostDto;
import de.example.blog.entity.Comment;
import de.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    //heandle save comment
    @PostMapping("/{postUrl}/comments")
    public String addCommentToPost(@PathVariable String postUrl, @ModelAttribute(name = "emptyComment") Comment comment, Model model){
        commentService.createComment(postUrl,comment);
        return "redirect:/post/"+postUrl;
    }
}
