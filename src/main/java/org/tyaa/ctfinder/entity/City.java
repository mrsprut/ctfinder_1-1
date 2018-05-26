package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class City {
	@Id
	private Long id;
	private Long country_id;
	private String title_key;
	
	public City() {
		super();
	}

	public City(Long country_id, String title_key) {
		super();
		this.country_id = country_id;
		this.title_key = title_key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	public String getTitle_key() {
		return title_key;
	}

	public void setTitle_key(String title_id) {
		this.title_key = title_id;
	}

}
