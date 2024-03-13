package com.webjava.coffeehouse.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webjava.coffeehouse.models.Bill;
import com.webjava.coffeehouse.models.BillProduct;
import com.webjava.coffeehouse.models.BillRequest;
import com.webjava.coffeehouse.models.ProductUser;
import com.webjava.coffeehouse.repositories.BillProductRepository;
import com.webjava.coffeehouse.repositories.BillRepository;
import com.webjava.coffeehouse.repositories.ProductUserRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/bills")
public class BillController {
	@Autowired
	private BillRepository repo;
	
	@GetMapping("{user_id}")
    public ResponseEntity<Object> getBills(@PathVariable int user_id) {
        List<Bill> bills = repo.findByUserId(user_id);
        
        if (bills == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(bills);
        }
	}
	
    @Autowired
	private ProductUserRepository productUserRepository;
    
    @Autowired
	private BillProductRepository billProductRepository;
	
	@PostMapping("/add/{user_id}")
	@Transactional
	public ResponseEntity<Object> addBill(
	    @RequestBody BillRequest  billRequest ,
	    @PathVariable int user_id
			) {
	    try {	    	
	        
            Bill newBill = new Bill();
            
            newBill.setUserId(user_id);
            newBill.setAddress(billRequest.getAddress());
            newBill.setPhoneNumber(billRequest.getPhoneNumber());
            newBill.setDeliveryMethod(billRequest.getDeliveryMethod());
            newBill.setDiscountCode(billRequest.getDiscountCode());
            newBill.setPaymentMethod(billRequest.getPaymentMethod());
            newBill.setTotalAmount(billRequest.getTotalAmount());
            newBill.setCreatedAt(new Date());
            
            repo.save(newBill);
	        
	        List<ProductUser> products = productUserRepository.findByUserId(user_id);
	        
	        for (ProductUser productuser : products) {
	            BillProduct billProduct = new BillProduct();
	            
	            billProduct.setBillId(newBill.getBillId());;
	            billProduct.setProductName(productuser.getProductName());
	            billProduct.setPrice(productuser.getPrice());
	            billProduct.setQuantity(productuser.getQuantity());
	            billProductRepository.save(billProduct);
	        }
	        
	        productUserRepository.deleteAllByUserId(user_id);
//	        
	        return ResponseEntity.ok( newBill);
	        
	    } catch (Exception e) {
	        String errorMessage = "Error creating product: " + e.getMessage();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	    }
	}

	@DeleteMapping("/delete/{user_id}")
	@Transactional
	public ResponseEntity<Object> deleteAllBill(@PathVariable int user_id){    	
		repo.deleteByUserId(user_id);		
		return ResponseEntity.ok("Bills deleted successfully");
	}
}
