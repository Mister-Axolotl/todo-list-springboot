package fr.gregory.todolistspringboot.controller;

import fr.gregory.todolistspringboot.model.Task;
import fr.gregory.todolistspringboot.model.User;
import fr.gregory.todolistspringboot.repository.TaskRepository;
import fr.gregory.todolistspringboot.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String list(Model model, Principal principal) {
        User current = userRepository.findByEmail(principal.getName());
        List<Task> tasks = (current == null) ? List.of() : taskRepository.findByUser(current);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, Principal principal) {
        User current = userRepository.findByEmail(principal.getName());
        if (current == null) return "redirect:/tasks";
        Optional<Task> opt = taskRepository.findById(id);
        if (opt.isEmpty() || !opt.get().getUser().getId().equals(current.getId())) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", opt.get());
        return "task-detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping
    public String create(@RequestParam String title,
                         @RequestParam(required = false) String description,
                         Principal principal) {
        User current = userRepository.findByEmail(principal.getName());
        if (current == null) return "redirect:/tasks";
        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setDone(false);
        t.setUser(current);
        taskRepository.save(t);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Principal principal) {
        User current = userRepository.findByEmail(principal.getName());
        if (current != null) {
            taskRepository.findById(id).ifPresent(t -> {
                if (t.getUser().getId().equals(current.getId())) {
                    taskRepository.deleteById(id);
                }
            });
        }
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/done")
    public String markDone(@PathVariable Long id, Principal principal) {
        User current = userRepository.findByEmail(principal.getName());
        if (current != null) {
            taskRepository.findById(id).ifPresent(t -> {
                if (t.getUser().getId().equals(current.getId())) {
                    t.setDone(true);
                    taskRepository.save(t);
                }
            });
        }
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/undone")
    public String markUndone(@PathVariable Long id, Principal principal) {
        User current = userRepository.findByEmail(principal.getName());
        if (current != null) {
            taskRepository.findById(id).ifPresent(t -> {
                if (t.getUser().getId().equals(current.getId())) {
                    t.setDone(false);
                    taskRepository.save(t);
                }
            });
        }
        return "redirect:/tasks";
    }
}