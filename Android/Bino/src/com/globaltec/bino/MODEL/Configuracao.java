package com.globaltec.bino.MODEL;

public class Configuracao {

	private String base_url;
	private String content_url;
	private String posts_url;
	
	public Configuracao() {
		
	}

	public String getBaseUrl() {
		return base_url;
	}

	public void setBaseUrl(String base_url) {
		this.base_url = base_url;
	}

	public String getContentUrl() {
		return content_url;
	}

	public void setContentUrl(String content_url) {
		this.content_url = content_url;
	}

	public String getPostsUrl() {
		return posts_url;
	}

	public void setPostsUrl(String posts_url) {
		this.posts_url = posts_url;
	}
	
}
