package com.webjava.coffeehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webjava.coffeehouse.models.LoginRequest;
import com.webjava.coffeehouse.models.User;
import com.webjava.coffeehouse.models.UserRequest;
import com.webjava.coffeehouse.repositories.AuthRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthRepository repo;
	
	@GetMapping
	public void isAuthentication() {
		
    }
	
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@PostMapping("/register")
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
		user.setRole("customer");
		
		repo.save(user);

		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
	    // Kiểm tra xem email và mật khẩu có được cung cấp không
	    if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
	        return ResponseEntity.badRequest().body("Email and password are required");
	    }

	    // Kiểm tra user
	    User user = repo.findByEmail(loginRequest.getEmail());
	    if (user == null) {
	        return ResponseEntity.badRequest().body("Invalid Email or Password");
	    }

	    // Kiểm tra password sử dụng BCryptPasswordEncoder
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
	        return ResponseEntity.badRequest().body("Invalid Email or Password");
	    }

	    // Tạo mã thông báo (token)
	    String jwtToken = Jwts.builder()
    	    .claim("user_id", user.getUserId())
    	    .claim("username", user.getUsername())
    	    .claim("role", user.getRole())
    	    .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
    	    .compact();

	    // Trả về mã thông báo (token)
	    return ResponseEntity.ok(jwtToken);
	}
}