package com.bigmwaj.tuto.dto;

import java.time.LocalDateTime;

public class OrderItem {
	
	private Integer id;
	
	private LocalDateTime date;
	
	private Integer qty;
	
	private Integer productId;
	
	private Integer orderId;

	public OrderItem() {
		super();
	}

	public OrderItem(Integer id, LocalDateTime date, Integer qty, Integer productId, Integer orderId) {
		super();
		this.id = id;
		this.date = date;
		this.qty = qty;
		this.productId = productId;
		this.orderId = orderId;
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

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
}
