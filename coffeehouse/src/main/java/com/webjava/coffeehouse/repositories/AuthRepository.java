package com.webjava.coffeehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webjava.coffeehouse.models.User;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}

