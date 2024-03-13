package com.webjava.coffeehouse.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webjava.coffeehouse.models.ProductUserRequest;
import com.webjava.coffeehouse.models.ProductUser;
import com.webjava.coffeehouse.repositories.ProductUserRepository;

@RestController
@RequestMapping("/products")
public class ProductsUserController {
	
	@Autowired
	private ProductUserRepository repo;
	
	@GetMapping("{user_id}")
	public ResponseEntity<Object> getProducts(@PathVariable int user_id){
		List<ProductUser> products = repo.findByUserId(user_id);
        
		return ResponseEntity.ok(products);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Object> addProduct(
	    @RequestBody ProductUserRequest  productRequest
			) {
	    try {
	    	
	    	int userId = productRequest.getUserId();
	    	String productName = productRequest.getProductName();
	    	
	        ProductUser existingProductUser = repo.findByUserIdAndProductName(userId, productName);

	        if (existingProductUser != null) {
	            existingProductUser.setQuantity(existingProductUser.getQuantity() + 1);
	            repo.save(existingProductUser);
	        } else {
	        	
	            ProductUser newProductUser = new ProductUser();
	            
	            newProductUser.setUserId(userId);
	            newProductUser.setProductName(productName);
	            newProductUser.setPrice(productRequest.getPrice());
	            newProductUser.setQuantity(1);
	            
	            repo.save(newProductUser);
	        }

	    	
	        return ResponseEntity.ok(productRequest.getPrice());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating product");
	    }
	}

	
	@PutMapping("update/{productId}")
	public ResponseEntity<Object> updateProduct(
		    @RequestBody ProductUserRequest  productRequest ,
		    @PathVariable int productId
				) {
	    try {
	    		    	
	        ProductUser productUser = repo.findById(productId);
            
            productUser.setQuantity(productRequest.getQuantity());
            repo.save(productUser);
	    	
	        return ResponseEntity.ok("Product updated successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product");
	    }
	}

	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable int productId){
		
		repo.deleteById(productId);
		return ResponseEntity.ok("Product deleted successfully");
	}
}
