package com.bigmwaj.tuto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bigmwaj.tuto.dto.Order;
import com.bigmwaj.tuto.dto.OrderItem;
import com.bigmwaj.tuto.store.FakeStore;

@Service
public class OrderService {

	public List<Order> findAllOrders() {
		return FakeStore.orders;		
	}

	public Optional<Order> findOrderById(Integer id) {
		return FakeStore.orders.stream().filter(e -> e.getId().equals(id)).findAny();		
	}
	
	public List<OrderItem> findOrderItems(Integer productId) {
		return FakeStore.orderItems.stream().filter(e -> e.getOrderId().equals(productId)).collect(Collectors.toList());		
	}
	
}
