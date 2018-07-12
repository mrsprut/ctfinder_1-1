package org.tyaa.ctfinder.model;

import com.google.gson.annotations.Expose;

public class OfferGridItem {

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
	public String content_preview;
	@Expose
	public String created_at;
	
	public OfferGridItem() {
		super();
	}
	
	public OfferGridItem(
			Long id
			, String offer_type
			, String title
			, String state
			, String image
			, String content_preview
			, String created_at
		) {
		super();
		this.id = id;
		this.offer_type = offer_type;
		this.title = title;
		this.state = state;
		this.image = image;
		this.content_preview = content_preview;
		this.created_at = created_at;
	}
}
