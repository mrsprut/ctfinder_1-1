package org.tyaa.ctfinder.entity;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class User {
	
	@Id
	@Expose (serialize = false, deserialize = false)
	private Long id;
	@Index
	@Expose (serialize = false, deserialize = false)
	private String google_id;
	@Index
	@Expose
	private String name;
	@Expose
	private String email;
	@Expose
	private String pictureUrl;
	@Index
	@Expose (serialize = false, deserialize = false)
	private Long user_type_id;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoogle_id() {
		return google_id;
	}

	public void setGoogle_id(String google_id) {
		this.google_id = google_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return pictureUrl;
	}

	public void setPicture(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Long getUser_type_id() {
		return user_type_id;
	}

	public void setUser_type_id(Long user_type_id) {
		this.user_type_id = user_type_id;
	}

}
