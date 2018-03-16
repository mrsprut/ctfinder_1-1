package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Static_title {
	
	@Id
	private Long id;
	private String key;
	private Long lang_id;
	private String content;

	public Static_title() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getLang_id() {
		return lang_id;
	}

	public void setLang_id(Long lang_id) {
		this.lang_id = lang_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
