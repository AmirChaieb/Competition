package com.Amir.Competition.repository;
import com.Amir.Competition.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface userRepository extends JpaRepository <user, Long>
{
    Optional<user> findByEmail(String email);
}