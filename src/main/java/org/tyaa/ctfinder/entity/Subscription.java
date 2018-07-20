package org.tyaa.ctfinder.entity;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Subscription {
	
	@Id
	@Expose (serialize = false, deserialize = false)
	private Long id;
	@Index
	@Expose
	private Long author_id;
	@Index
	@Expose
	private Long subscriber_id;

	public Subscription() {
		// TODO Auto-generated constructor stub
	}
	
	public Subscription(Long author_id, Long subscriber_id) {
		super();
		this.author_id = author_id;
		this.subscriber_id = subscriber_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(Long author_id) {
		this.author_id = author_id;
	}

	public Long getSubscriber_id() {
		return subscriber_id;
	}

	public void setSubscriber_id(Long subscriber_id) {
		this.subscriber_id = subscriber_id;
	}
}
