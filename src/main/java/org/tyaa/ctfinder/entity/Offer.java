package org.tyaa.ctfinder.entity;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Offer {
	@Id
	private int id;
	private int offer_type_id; 
	private int state_id;
	private String title_key;
	private String description_key;
	private int user_id;
	private int country_id;
	private int city_id;
	private Date created_at;
	private Date updated_at;
	
	
	public Offer(int id, int offer_type_id, int state_id, String title_key, String description_key, int user_id,
			int country_id, int city_id, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.offer_type_id = offer_type_id;
		this.state_id = state_id;
		this.title_key = title_key;
		this.description_key = description_key;
		this.user_id = user_id;
		this.country_id = country_id;
		this.city_id = city_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOffer_type_id() {
		return offer_type_id;
	}
	public void setOffer_type_id(int offer_type_id) {
		this.offer_type_id = offer_type_id;
	}
	public int getState_id() {
		return state_id;
	}
	public void setState_id(int state_id) {
		this.state_id = state_id;
	}
	public String getTitle_key() {
		return title_key;
	}
	public void setTitle_key(String title_key) {
		this.title_key = title_key;
	}
	public String getDescription_key() {
		return description_key;
	}
	public void setDescription_key(String description_key) {
		this.description_key = description_key;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getCountry_id() {
		return country_id;
	}
	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}
	public int getCity_id() {
		return city_id;
	}
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
}
