package com.bigmwaj.tuto.store;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bigmwaj.tuto.dto.Order;
import com.bigmwaj.tuto.dto.OrderItem;
import com.bigmwaj.tuto.dto.Product;

public class FakeStore {

	public static final List<Product> products;

	public static final List<Order> orders;

	public static final List<OrderItem> orderItems;
	
	static {
		products = new ArrayList<>();
		orders = new ArrayList<>();
		orderItems = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			products.add(new Product(1, "Product " + i, 10. * (1 + i)));
		}

		int k = 0;
		int orderTotal = (int) (Math.random() * 10);
		for (int i = 0; i < orderTotal; i++) {
			orders.add(new Order(1, LocalDateTime.now()));
			
			for (int j = 0; j < 10; j++) {
				int qty = (int) (Math.random() * 10);
				if( 0 == qty ) {
					qty = 1;
				}
				orderItems.add(new OrderItem(k++, LocalDateTime.now(), qty, j, i));
			}
		}

	}
}
