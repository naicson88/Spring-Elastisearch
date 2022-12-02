package com.slastic.demo.data.dto;

public class PagedRequestDTO {
	
	private static final int DEFAULT_SIZE = 100;
	int page;
	int size;
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}	
