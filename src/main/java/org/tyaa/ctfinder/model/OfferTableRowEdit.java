package org.tyaa.ctfinder.model;

import com.google.gson.annotations.Expose;

public class OfferTableRowEdit {

	@Expose
	public Long id;
	@Expose
	public String offer_type;
	@Expose
	public String title;
	@Expose
	public String content;
	
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
	
	public OfferTableRowEdit() {
		super();
	}
	
	public OfferTableRowEdit(
			Long id
			, String offer_type
			, String title
			, String content
			
			, String collaborators_count
			, String country
			, String city
			, String desired_start_date
			, String desired_finish_date
		) {
		super();
		this.id = id;
		this.offer_type = offer_type;
		this.title = title;
		this.content = content;
		
		this.collaborators_count = collaborators_count;
		this.country = country;
		this.city = city;
		this.desired_start_date = desired_start_date;
		this.desired_finish_date = desired_finish_date;
	}
}
