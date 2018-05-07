package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Offer_type {
	
	@Id
	private Long id;
	@Index
	private String description_key;

	public Offer_type() {
		super();
	}

	public Offer_type(Long id, String description_key) {
		super();
		this.id = id;
		this.description_key = description_key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription_key() {
		return description_key;
	}

	public void setDescription_key(String description_key) {
		this.description_key = description_key;
	}

}
