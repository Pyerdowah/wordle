package com.example.wordle.repository;

import com.example.wordle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.login = ?1")
    Optional<User> findUserByLogin(String login);

    @Modifying
    @Query("update User u set u.login = :#{#user.login}, u.password = :#{#user.password} where u.userId = :#{#user.userId}")
    void update(User user);
}