package com.slastic.demo.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.slastic.demo.helper.Indices;

@Document(indexName = Indices.PRODUCT_INDEX)
public class Product {
	
	@Id
	@Field(type = FieldType.Keyword)
	private String id;
	@Field(type = FieldType.Keyword)
	private String name;
	@Field(type = FieldType.Text)
	private String description;
	@Field(type = FieldType.Keyword)
	private List<String> tags;
	@Field(type = FieldType.Text, name = "image_url")
	private String imageUrl;
	@Field(type = FieldType.Date, format = DateFormat.date_time)
	private Date created;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
