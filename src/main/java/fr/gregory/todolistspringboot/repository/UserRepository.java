package fr.gregory.todolistspringboot.repository;

import fr.gregory.todolistspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
