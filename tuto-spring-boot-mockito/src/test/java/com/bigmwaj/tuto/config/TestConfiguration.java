package com.bigmwaj.tuto.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.bigmwaj.tuto.service.ProductService;

@Profile("test")
@Configuration
public class TestConfiguration {

	@Primary
	@Bean
	public ProductService getProductService() {
		return Mockito.mock(ProductService.class);
	}
}
