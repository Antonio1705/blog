package de.example.blog;

import de.example.blog.entity.Comment;
import de.example.blog.entity.Post;
import de.example.blog.repository.CommentRepository;
import de.example.blog.repository.PostRepository;
import de.example.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	PostRepository postRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	UserRepository userRepository;

	@Test
	void contextLoads() {

	}


}
