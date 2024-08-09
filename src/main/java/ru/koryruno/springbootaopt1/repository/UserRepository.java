package ru.koryruno.springbootaopt1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.koryruno.springbootaopt1.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
