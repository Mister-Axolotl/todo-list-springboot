package fr.gregory.todolistspringboot;

import fr.gregory.todolistspringboot.model.Task;
import fr.gregory.todolistspringboot.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import fr.gregory.todolistspringboot.repository.TaskRepository;
import fr.gregory.todolistspringboot.repository.UserRepository;

@SpringBootApplication
public class TodoListSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListSpringbootApplication.class, args);
    }
}
