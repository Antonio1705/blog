package de.example.blog.controller;

import de.example.blog.dto.PostDto;
import de.example.blog.entity.Comment;
import de.example.blog.service.CommentService;
import de.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String startSiteBlog(Model model){
        List<PostDto> allPosts = postService.findAllPosts();

        model.addAttribute("allPost",allPosts);

        return "blog/view_posts";
    }

    @GetMapping("/post/{postUrl}")
    public String showPostBlog(@PathVariable String postUrl, Model model){
        PostDto postByUrl = postService.findPostByUrl(postUrl);
        Comment emptyComment = commentService.newComment();

        model.addAttribute("postByUrl",postByUrl);
        model.addAttribute("emptyComment", emptyComment);

        return "blog/blog_post";

    }

    @GetMapping("/page/search")
    public String searchPostBlog(@RequestParam(name = "query",required = false) String query, Model model){
        List<PostDto> postDtoList = postService.searchPosts(query);
        model.addAttribute("allPost",postDtoList);

        return "blog/view_posts";
    }
}
