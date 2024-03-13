package com.webjava.coffeehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webjava.coffeehouse.models.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {
	
}


