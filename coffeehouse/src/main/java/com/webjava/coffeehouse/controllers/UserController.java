package com.webjava.coffeehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webjava.coffeehouse.models.User;
import com.webjava.coffeehouse.models.UserRequest;
import com.webjava.coffeehouse.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository repo;
	
	@GetMapping("{user_id}")
	public ResponseEntity<User> getUser(@PathVariable int user_id){
		User user = repo.findById(user_id).orElse(null);
		
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(user);
	}
	
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@PutMapping("/update/{user_id}")
	public ResponseEntity<Object> updateUser(
			@PathVariable int user_id,
			@RequestBody UserRequest userDto
			){
		try {
			if (userDto.getEmail() == null || userDto.getPassword() == null) {
		        return ResponseEntity.badRequest().body("Email and password are required");
		    }

		    // Kiểm tra user
		    User user = repo.findByEmail(userDto.getEmail());
		    if (user == null) {
		        return ResponseEntity.badRequest().body("Invalid Email or Password");
		    }

		    // Kiểm tra password sử dụng BCryptPasswordEncoder
		    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		    if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
		        return ResponseEntity.badRequest().body("Invalid Email or Password");
		    }
		    
		    user.setUsername(userDto.getUsername());
		    user.setRole(userDto.getRole());
			user.setPassword(bCryptPasswordEncoder.encode(userDto.getNewPassword()));
			repo.save(user);
	    	
	        return ResponseEntity.ok("User updated successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating product");
	    }
	}
}