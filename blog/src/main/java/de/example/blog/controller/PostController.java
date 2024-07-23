package de.example.blog.controller;

import de.example.blog.dto.PostDto;
import de.example.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/posts-admin")
    public String getAllPosts(Model model) {

        List<PostDto> allPosts = postService.findAllPosts();
        model.addAttribute("allPosts", allPosts);
        return "/admin/posts";
    }


    @GetMapping("/start")
    public String getStartPage() {
        return "index";
    }

    @GetMapping("/admin/newpost")
    public String newPostForm(Model model) {
        PostDto postDto = new PostDto();
        model.addAttribute("newPost", postDto);
        return "admin/create_post";
    }

    @PostMapping("/admin/post")
    public String newPostSave(@Valid @ModelAttribute("newPost") PostDto postDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postDto);
            return "/admin/create_post";
        }

        postDto.setUrl(getUrl(postDto.getTitle()));
        postService.createPost(postDto);
        return "redirect:/api/posts-admin";
    }

    //handle method to handle edit post request from posts.html
    @GetMapping("/admin/post/{postId}/edit")
    public String editPostForm(@PathVariable Long postId, Model model) {

        PostDto postById = postService.findPostById(postId);

        model.addAttribute("postUpdate", postById);

        return "admin/edit_post";

    }

    @PostMapping("/admin/posts/{postId}")
    public String handleUpdatePosts(@PathVariable Long postId, @Valid @ModelAttribute("postUpdate") PostDto modelAttributePostDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postUpdate", modelAttributePostDto);
            return "/admin/edit_post";
        }
        modelAttributePostDto.setId(postId);
        modelAttributePostDto.setUrl(getUrl(modelAttributePostDto.getTitle()));
        postService.updatePost(modelAttributePostDto);
        return "redirect:/api/posts-admin";

    }

    @GetMapping("/admin/post/{postId}/delete")
    public String deletePostHandler(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/api/posts-admin";
    }

    @GetMapping("/admin/post/{postUrl}/view")
    public String viewPostHandler(@PathVariable String postUrl, Model model) {

        PostDto postDto = postService.findPostByUrl(postUrl);

        model.addAttribute("post", postDto);
        return "admin/view_post";
    }

    @GetMapping("/admin/posts/search")
    public String searchPosts(@RequestParam(value = "query") String query, Model model) {
        List<PostDto> postDtoListSearch = postService.searchPosts(query);
        model.addAttribute("allPosts", postDtoListSearch);
        return "admin/posts";
    }

    private static String getUrl(String postTitle) {
        String title = postTitle.trim().toLowerCase();
        String url = title.replace("\\s+", "-");
        url = url.replaceAll("[^A-Za-z0-9]", "-");
        return url;
    }
}
