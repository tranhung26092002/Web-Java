package com.webjava.coffeehouse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webjava.coffeehouse.models.User;

@Repository
public interface ManagerRepository extends JpaRepository<User, Integer> {

	List<User> findByRole(String role);

}