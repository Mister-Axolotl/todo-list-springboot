package fr.gregory.todolistspringboot.controller;

import fr.gregory.todolistspringboot.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login"; // Affiche le template login.html
    }

    // POST /login supprimé: géré par Spring Security formLogin
}
