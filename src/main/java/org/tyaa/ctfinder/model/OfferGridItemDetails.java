package org.tyaa.ctfinder.model;

import com.google.gson.annotations.Expose;

public class OfferGridItemDetails {

	@Expose
	public Long id;
	@Expose
	public String offer_type;
	@Expose
	public String title;
	@Expose
	public String state;
	@Expose
	public String image;
	@Expose
	public String content;
	@Expose
	public String created_at;
	
	@Expose
	public String author_name;
	@Expose
	public Long author_id;
	@Expose
	public Boolean subscription;
	
	@Expose
	public String collaborators_count;
	@Expose
	public String country;
	@Expose
	public String city;
	@Expose
	public String desired_start_date;
	@Expose
	public String desired_finish_date;
	
	public OfferGridItemDetails() {
		super();
	}
	
	public OfferGridItemDetails(
			Long id
			, String offer_type
			, String title
			, String state
			, String image
			, String content
			, String created_at
			
			, String country
			, String city
			
			, String author_name
			, Long author_id
			, Boolean subscription
		) {
		super();
		this.id = id;
		this.offer_type = offer_type;
		this.title = title;
		this.state = state;
		this.image = image;
		this.content = content;
		this.created_at = created_at;
		
		this.country = country;
		this.city = city;
		
		this.author_name = author_name;
		this.author_id = author_id;
		this.subscription = subscription;
	}
}
