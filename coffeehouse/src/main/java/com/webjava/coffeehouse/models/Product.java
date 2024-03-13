package com.webjava.coffeehouse.models;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

	@Id
    @Column(name = "product_id")
    private int id;
    
    @Column(name = "product_name")
    private String name;
    
    @Column(name = "price")
    private double price;
    
    @Column(name = "description")
    private String description;

    // Constructors
    public Product() {
    	
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
