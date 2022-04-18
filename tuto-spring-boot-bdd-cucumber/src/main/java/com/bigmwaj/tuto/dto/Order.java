package com.bigmwaj.tuto.dto;

import java.time.LocalDateTime;

public class Order {

	private Integer id;
	
	private LocalDateTime date;

	public Order() {
		
	}
	
	public Order(Integer id, LocalDateTime date) {
		super();
		this.id = id;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
}
