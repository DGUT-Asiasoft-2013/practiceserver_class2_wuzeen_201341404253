package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import com.cloudage.membercenter.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Comment extends DateRecord{
	String content;
	User author;
	Article article;
	
	
	
	@ManyToOne(optional=false)
	public User getAuthor() {
		return author;
	}
	
	@ManyToOne(optional=false)
	public Article getArticle() {
		return article;
	}
	

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
	public void setArticle(Article article) {
		this.article = article;
	}	
}