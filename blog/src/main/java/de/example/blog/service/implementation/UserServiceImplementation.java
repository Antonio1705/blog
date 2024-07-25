package de.example.blog.service.implementation;

import de.example.blog.entity.Role;
import de.example.blog.entity.User;
import de.example.blog.repository.RoleRepository;
import de.example.blog.repository.UserRepository;
import de.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User saveUser(User user) {

        Role roleGuest = roleRepository.findByName("ROLE_GUEST");
        user.setRoleList(Arrays.asList(roleGuest));
        User userSave= userRepository.save(user);


        return userSave;
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
