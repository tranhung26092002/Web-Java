package com.webjava.coffeehouse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webjava.coffeehouse.models.ProductUser;


@Repository
public interface ProductUserRepository extends JpaRepository<ProductUser, Integer> {

	List<ProductUser> findByUserId(int userId);

	void deleteAllByUserId(int userId);
	ProductUser findById(int id);

	ProductUser findByUserIdAndProductName(int userId, String productName);
}

