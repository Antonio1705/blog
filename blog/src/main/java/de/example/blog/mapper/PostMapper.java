package de.example.blog.mapper;

import de.example.blog.dto.PostDto;
import de.example.blog.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    //map post entity to PostDto
    public PostDto mapToPostDto(Post post){
        System.out.println(post);
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .url(post.getUrl())
                .content(post.getContent())
                .shortDescription(post.getShortDescription())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .build();
        return postDto;
    }

    public Post mapToPostEntity(PostDto postDto){
        Post postEntity = Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .url(postDto.getUrl())
                .content(postDto.getContent())
                .shortDescription(postDto.getShortDescription())
                .createdOn(postDto.getCreatedOn())
                .updatedOn(postDto.getUpdatedOn())
                .build();
        return postEntity;
    }

}
