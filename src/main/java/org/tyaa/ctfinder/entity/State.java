package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class State {
	
	@Id
	private Long id;
	@Index
	private String title_key;
	
	public State() {
		super();
	}
	
	public State(String title_key) {
		super();
		this.title_key = title_key;
	}
	
	public State(Long id, String title_key) {
		super();
		this.id = id;
		this.title_key = title_key;
		//this.offer_type_id = offer_type_id;
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

	/*public Long getOffer_type_id() {
		return offer_type_id;
	}

	public void setOffer_type_id(Long offer_type_id) {
		this.offer_type_id = offer_type_id;
	}*/

}
