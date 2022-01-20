package com.bigmwaj.tuto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bigmwaj.tuto.dto.Product;
import com.bigmwaj.tuto.store.FakeStore;

@Service
public class ProductService {

	public List<Product> findAllProducts() {
		return FakeStore.products;		
	}

	public Optional<Product> findProductById(Integer id) {
		return FakeStore.products.stream().filter(e -> e.getId().equals(id)).findAny();		
	}
}
