package com.webjava.coffeehouse.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bill_product")
public class BillProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bill_id")
    private int billId;

    @Column(name = "productName")
    private String productName;
    
    @Column(name = "quantity")
    private int quantity;
    
    @Column(name = "price")
    private int price;

    // Constructors
    public BillProduct() {
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
}
