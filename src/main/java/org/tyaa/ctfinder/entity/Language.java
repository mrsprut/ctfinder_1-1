package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Language {
	
	@Id
	private Long id;
	private String title_key;
	private String code;

	public Language() {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
