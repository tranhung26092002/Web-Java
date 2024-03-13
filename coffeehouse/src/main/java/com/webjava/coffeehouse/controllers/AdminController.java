package com.webjava.coffeehouse.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webjava.coffeehouse.models.User;
import com.webjava.coffeehouse.models.UserRequest;
import com.webjava.coffeehouse.repositories.AdminRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminRepository repo;
	
	@GetMapping
	public List<User> getUsers(){
		return repo.findAll();
	}
	
	@GetMapping("{user_id}")
	public ResponseEntity<User> getUser(@PathVariable int user_id){
		User user = repo.findById(user_id).orElse(null);
		
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(user);
	}
	
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@PostMapping("/add")
	public ResponseEntity<Object> createUser(
			@Valid @RequestBody UserRequest userDto,
			BindingResult result
			)  {
		
		if(result.hasErrors()) {
			var errorsList = result.getAllErrors();
			var errorsMap = new HashMap<String, String>();
			
			for(int i = 0; i < errorsList.size(); i++) {
				var error = (FieldError) errorsList.get(i);
				errorsMap.put(error.getField(), error.getDefaultMessage());
			}
			
			return ResponseEntity.badRequest().body(errorsMap);
		}
		
		User user = new User();
		
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		user.setRole(userDto.getRole());
		
		repo.save(user);

		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/delete/{user_id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int user_id){
		repo.deleteById(user_id);
		return ResponseEntity.ok("User deleted successfully");
	}
	
}
