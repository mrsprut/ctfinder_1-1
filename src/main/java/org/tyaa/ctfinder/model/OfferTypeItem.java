package org.tyaa.ctfinder.model;

import com.google.gson.annotations.Expose;

public class OfferTypeItem {

	@Expose
	private Long id;
	@Expose
	private String description;
	
	public OfferTypeItem() {}
	
	public OfferTypeItem(Long id, String description) {
		super();
		this.id = id;
		this.description = description;
	}
}
