package com.slastic.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slastic.demo.data.dto.SearchRequestDTO;
import com.slastic.demo.helper.Indices;
import com.slastic.demo.model.Product;
import com.slastic.demo.repository.ProductRepository;
import com.slastic.demo.util.SearchUtil;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	RestHighLevelClient client;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public void save(final Product product) {
		product.setCreated( new Date());
		productRepository.save(product);
	}
	
	public Product findById(final String id) {
		
		return productRepository.findById(id).orElse(null);
	}
	
	public List<Product> search(final SearchRequestDTO dto){
		final SearchRequest request = SearchUtil.buildSearchRequest(Indices.PRODUCT_INDEX, dto);
		
		if(request == null)
			return Collections.emptyList();
		
		return searchInternal(request);
	}
	
	public List<Product> getAllProductsCreatedSince(final Date date){
		final SearchRequest request = SearchUtil.buildSearchRequest(Indices.PRODUCT_INDEX, "created", date);
		
		if(request == null)
			return Collections.emptyList();
		
		return searchInternal(request);
	}
	
	public List<Product> searchCreatedSince(final SearchRequestDTO dto, final Date date){
		final SearchRequest request = SearchUtil.buildSearchRequest(Indices.PRODUCT_INDEX, dto, date);
		
		if(request == null)
			return Collections.emptyList();
		
		return searchInternal(request);
	}
	

	private List<Product> searchInternal(final SearchRequest request) {
		try {
			final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
			
			final SearchHit[] searchHits = response.getHits().getHits();
			final List<Product> prods = new ArrayList<>(searchHits.length);
			
			MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			
			for(SearchHit hit: searchHits) {
				prods.add(MAPPER.readValue(hit.getSourceAsString(), Product.class));
			}
			
			return prods;
			
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

}	
