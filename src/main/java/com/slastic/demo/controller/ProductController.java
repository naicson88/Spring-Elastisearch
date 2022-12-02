package com.slastic.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slastic.demo.data.dto.SearchRequestDTO;
import com.slastic.demo.model.Product;
import com.slastic.demo.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	ProductService service;
	
	@PostMapping("/save")
	public void save(@RequestBody Product product) {
		service.save(product);
	}
	
	@GetMapping("/{id}")
	public Product findById(@PathVariable String id) {
		return service.findById(id);
	}
	
	@PostMapping("/search")
	public List<Product> search(@RequestBody final SearchRequestDTO dto){
		return service.search(dto);
	}
	
	@GetMapping("/search/since/{date}")
	public List<Product> getAllProductsByDateSince(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date){
		return service.getAllProductsCreatedSince(date);
	}
	
	@PostMapping("/search/createdsince/{date}")
	public List<Product> searchCreatedSince(
			@RequestBody final SearchRequestDTO dto, 
			@PathVariable 
			@DateTimeFormat(pattern = "yyyy-MM-dd") final Date date){
		
		return service.searchCreatedSince(dto, date);
	}
}
