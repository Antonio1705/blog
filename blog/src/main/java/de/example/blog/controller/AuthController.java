package de.example.blog.controller;

import de.example.blog.entity.User;
import de.example.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registrationHandling(Model model){
        User newEmptyUser = new User();

        model.addAttribute("newEmptyUser",newEmptyUser);


        return "register";
    }

    @PostMapping("/register/save")
    public String registrationSave(@Valid @ModelAttribute("newEmptyUser") User user, BindingResult bindingResult, Model model){

        Optional<User> userFindByEmail = userService.findByEmail(user.getEmail());
        if (userFindByEmail.isPresent()){
            bindingResult.rejectValue("email",null,"There is already a user with same email address ");
        }


        if (bindingResult.hasErrors()){
            model.addAttribute("newEmptyUser",user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }
}
