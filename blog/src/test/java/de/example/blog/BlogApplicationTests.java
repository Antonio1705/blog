package de.example.blog;

import de.example.blog.entity.Post;
import de.example.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	PostRepository postRepository;

	@Test
	void contextLoads() {


	}


}
