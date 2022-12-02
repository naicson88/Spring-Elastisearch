package com.slastic.demo.config;


import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.slastic.demo.repository")
@ComponentScan(basePackages = {"com.slastic.demo"})
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration{

	@Bean
	@Override
	public RestHighLevelClient elasticsearchClient() {
		final ClientConfiguration client = ClientConfiguration.builder()
				.connectedTo("localhost:9200")
				.build();
				
		return RestClients.create(client).rest();
	}
	
	
//	@Bean
//	public ElasticsearchOperations elasticTemplate() {
//		return new ElasticsearchRestTemplate(elasticsearchClient());
//	}
//	

}
