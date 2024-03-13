package com.webjava.coffeehouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webjava.coffeehouse.models.ProductUser;
import com.webjava.coffeehouse.models.User;
import com.webjava.coffeehouse.repositories.ManagerRepository;
import com.webjava.coffeehouse.repositories.ProductUserRepository;

@RestController
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private ProductUserRepository productUserRepository;
	
	@Autowired
	private ManagerRepository repo;

	@GetMapping
	public List<User> getCustomers() {
	    List<User> customers = repo.findByRole("customer");
	    return customers;
	}

	@GetMapping("{user_id}")
	public ResponseEntity<Object> getProducts(@PathVariable int user_id){
		List<ProductUser> products = productUserRepository.findByUserId(user_id);
        
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(products);
        }
	}
	
}