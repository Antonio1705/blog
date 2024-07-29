package de.example.blog.service.implementation;

import de.example.blog.entity.Role;
import de.example.blog.entity.User;
import de.example.blog.repository.RoleRepository;
import de.example.blog.repository.UserRepository;
import de.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role roleGuest = roleRepository.findByName("ROLE_USER");
        user.setRoleList(Arrays.asList(roleGuest));
        User userSave= userRepository.save(user);


        return userSave;
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
