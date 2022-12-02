package com.slastic.demo.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.slastic.demo.model.Product;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String>{
	
	
}
