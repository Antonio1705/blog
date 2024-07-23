package de.example.blog.controller;

import de.example.blog.dto.PostDto;
import de.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String startSiteBlog(Model model){
        List<PostDto> allPosts = postService.findAllPosts();

        model.addAttribute("allPost",allPosts);

        return "blog/view_posts";
    }

    @GetMapping("/post/{postUrl}")
    public String showPostBlog(@PathVariable String postUrl, Model model){
        PostDto postByUrl = postService.findPostByUrl(postUrl);
        model.addAttribute("postByUrl",postByUrl);

        return "blog/blog_post";

    }

    @GetMapping("/page/search")
    public String searchPostBlog(@RequestParam(name = "query",required = false) String query, Model model){
        List<PostDto> postDtoList = postService.searchPosts(query);
        model.addAttribute("allPost",postDtoList);

        return "blog/view_posts";
    }
}
