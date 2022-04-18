package com.bigmwaj.tuto;

import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.bigmwaj.tuto.dto.Product;
import com.bigmwaj.tuto.service.OrderService;
import com.bigmwaj.tuto.service.ProductService;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class ApplicationTests {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;

	@Test
	public void test1() {
		Mockito.when(productService.findProductById(anyInt())).thenReturn(Optional.empty());
		Optional<Product> product = productService.findProductById(1);
		Assert.assertEquals(product.isPresent(), false);
	}
}
