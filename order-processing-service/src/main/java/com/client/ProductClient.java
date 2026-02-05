package com.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.dto.ProductStockDto;

@FeignClient(name = "product-service", path = "/api/v1/products")
public interface ProductClient {

	@GetMapping("/{id}/stock")
	ProductStockDto getStock(@PathVariable("id") Long productId);

	@PostMapping("/{id}/stock/reserve")
	void reserve(@PathVariable("id") Long productId,
			@RequestParam("qty") Integer qty);

	@PostMapping("/{id}/stock/commit")
	void commit(@PathVariable("id") Long productId,
			@RequestParam("qty") Integer qty);

	@PostMapping("/{id}/stock/release")
	void release(@PathVariable("id") Long productId,
			@RequestParam("qty") Integer qty);
}
