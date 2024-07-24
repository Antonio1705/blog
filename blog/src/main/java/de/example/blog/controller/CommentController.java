package de.example.blog.controller;

import de.example.blog.dto.PostDto;
import de.example.blog.entity.Comment;
import de.example.blog.service.CommentService;
import de.example.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;


    //heandle save comment
    @PostMapping("/{postUrl}/comments")
    public String addCommentToPost(@PathVariable String postUrl, @Valid @ModelAttribute(name = "emptyComment") Comment comment, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("postByUrl",postService.findPostByUrl(postUrl));
            model.addAttribute("unwichtigeatribt","TESTTESTTESTTEST");
            model.addAttribute("emptyComment",comment);
            return "blog/blog_post";
        }
        commentService.createComment(postUrl,comment);
        return "redirect:/post/"+postUrl;
    }
}
