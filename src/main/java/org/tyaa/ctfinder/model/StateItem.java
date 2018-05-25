package org.tyaa.ctfinder.model;

import com.google.gson.annotations.Expose;

public class StateItem {

	@Expose
	private Long id;
	@Expose
	private String title;
	
	public StateItem() {}
	
	public StateItem(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
}
