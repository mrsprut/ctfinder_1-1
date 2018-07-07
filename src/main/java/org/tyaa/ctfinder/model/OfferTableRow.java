package org.tyaa.ctfinder.model;

import com.google.gson.annotations.Expose;

public class OfferTableRow {

	@Expose
	public Long id;
	@Expose
	public String offer_type;
	@Expose
	public String title;
	@Expose
	public String state;
	@Expose
	public String created_at;
	
	public OfferTableRow() {
		super();
	}
	
	public OfferTableRow(
			Long id
			, String offer_type
			, String title
			, String state
			, String created_at
		) {
		super();
		this.id = id;
		this.offer_type = offer_type;
		this.title = title;
		this.state = state;
		this.created_at = created_at;
	}
}
