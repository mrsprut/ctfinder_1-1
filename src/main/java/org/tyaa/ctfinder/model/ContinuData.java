package org.tyaa.ctfinder.model;

import java.util.ArrayList;
import java.util.List;

import org.tyaa.ctfinder.entity.Offer;

import com.google.gson.annotations.Expose;

public class ContinuData {

	@Expose
	public String cursor_str = "";
	@Expose
	List items = new ArrayList();
	
	public ContinuData() {}
	public ContinuData(List _items, String _cursorStr) {
		
		super();
		if(_items != null && _items.size() > 0) {
			
			this.items.addAll(_items);
		}
		this.cursor_str = _cursorStr;
	}
}
