package com.slastic.demo.util;

import java.util.Date;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.slastic.demo.data.dto.SearchRequestDTO;


import org.elasticsearch.action.search.SearchRequest;

public final class SearchUtil {
	
	private SearchUtil() {}
	
	public static QueryBuilder getQueryBuilder(final SearchRequestDTO dto) {
		if(dto == null) {return null;}
		
		final List<String> fields = dto.getFields();
		
		if(fields ==  null || fields.isEmpty())
			return null;
		
		if(fields.size() > 1) {
			MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
					.type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
					.operator(Operator.AND);
				
			fields.forEach(queryBuilder:: field);
			
			return queryBuilder;
		}
		
		return fields.stream().findFirst().map(field -> QueryBuilders.matchQuery(field, dto.getSearchTerm())
				.operator(Operator.AND))
				.orElse(null);
	}
	

	
	public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDTO dto) {
		
		try {
			int from = dto.getPage() <= 0 ? 0 : dto.getPage() * dto.getSize();
			
			SearchSourceBuilder builder = new SearchSourceBuilder()
					.from(from)
					.size(dto.getSize())
					.postFilter(getQueryBuilder(dto));
			
			if(dto.getSortBy() != null) {
				builder = builder.sort(dto.getSortBy(),
						dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
			}
			
			SearchRequest request = new SearchRequest(indexName);
			request.source(builder);
			
			return request;
			
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDTO dto, final Date date) {
		
		try {
			
			QueryBuilder searchQuery = getQueryBuilder(dto);
			QueryBuilder dateQuery = getQueryBuilder("created", date);
			
			BoolQueryBuilder boolQuery =  QueryBuilders.boolQuery().must(searchQuery).must(dateQuery);
			
			SearchSourceBuilder builder = new SearchSourceBuilder()
					.postFilter(boolQuery);
			
			if(dto.getSortBy() != null) {
				builder = builder.sort(dto.getSortBy(),
						dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
			}
			
			SearchRequest request = new SearchRequest(indexName);
			request.source(builder);
			
			return request;
			
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	public static SearchRequest buildSearchRequest(final String indexName, final String field, final Date date) {
		try {
			SearchSourceBuilder builder = new SearchSourceBuilder()
					.postFilter(getQueryBuilder(field, date));
			
			SearchRequest request = new SearchRequest(indexName);
			request.source(builder);
			
			return request;
			
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	public static QueryBuilder getQueryBuilder(final String field, final Date date) {
		return QueryBuilders.rangeQuery(field).gte(date); // greater than or equal
	}
}
