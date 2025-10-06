package fr.gregory.todolistspringboot.controller;

import fr.gregory.todolistspringboot.model.User;
import fr.gregory.todolistspringboot.repository.TaskRepository;
import fr.gregory.todolistspringboot.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public PageController(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        return "main"; // templates/main.html
    }

    @GetMapping(path = "/users", produces = MediaType.TEXT_HTML_VALUE)
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users"; // templates/users.html
    }

    // Mapping /tasks supprimé: désormais géré par TaskController
}