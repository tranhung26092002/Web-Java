package com.webjava.coffeehouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webjava.coffeehouse.models.BillProduct;
import com.webjava.coffeehouse.repositories.BillProductRepository;

@RestController
@RequestMapping("/billproduct")
public class BillProductController {
	@Autowired
	private BillProductRepository repo;
	
	@GetMapping("{bill_id}")
    public ResponseEntity<Object> getBills(@PathVariable int bill_id) {
        List<BillProduct> billProduct = repo.findByBillId(bill_id);
        
        return ResponseEntity.ok(billProduct);

	}
	
}
