package com.cloudage.membercenter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.Transient;

import com.cloudage.membercenter.util.BaseEntity;
import com.cloudage.membercenter.util.DateRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Article extends DateRecord {
	
	User author;
	
	String title;
	String text;
	
	@ManyToOne(optional=false)
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}