package com.webjava.coffeehouse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webjava.coffeehouse.models.BillProduct;


@Repository
public interface BillProductRepository extends JpaRepository<BillProduct, Integer> {

	List<BillProduct> findByBillId(int billId);

	
}
