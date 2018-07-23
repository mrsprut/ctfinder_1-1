package org.tyaa.ctfinder.entity;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Static_title {
	
	@Id
	@Expose (serialize = false, deserialize = false)
	private Long id;
	@Index
	@Expose
	private String key;
	@Index
	@Expose (serialize = false, deserialize = false)
	private Long lang_id;
	@Index
	@Expose
	private String content;
	@Index
	@Expose
	public Date created_at;

	public Static_title() {
	}
	
	public Static_title(String key, Long lang_id, String content) {
		super();
		this.key = key;
		this.lang_id = lang_id;
		this.content = content;
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
