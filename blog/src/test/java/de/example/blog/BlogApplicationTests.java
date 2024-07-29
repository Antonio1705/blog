package de.example.blog;

import de.example.blog.entity.Comment;
import de.example.blog.entity.Post;
import de.example.blog.entity.Role;
import de.example.blog.repository.CommentRepository;
import de.example.blog.repository.PostRepository;
import de.example.blog.repository.RoleRepository;
import de.example.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	PostRepository postRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
		userRepository.findByEmail("nana@gmail.com").get().getRoleList().forEach(role -> System.out.println(role.getName()+"<<<<<"));

	}


}
