package com.webjava.coffeehouse.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webjava.coffeehouse.models.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
	List<Bill> findByUserId(int userId);
	void deleteByUserId(int userId);
}


