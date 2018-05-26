package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Country {
	@Id
	private Long id;
	@Index
	private String title_key;

	public Country() {
		super();
	}

	public Country(String title_key) {
		super();
		this.title_key = title_key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle_key() {
		return title_key;
	}

	public void setTitle_key(String title_key) {
		this.title_key = title_key;
	}

}
