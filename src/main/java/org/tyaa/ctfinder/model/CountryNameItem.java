package org.tyaa.ctfinder.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;

public class CountryNameItem {

	@Expose
	public Map<String, Object> name =
			new HashMap<String, Object>();

	public CountryNameItem(String _name) {
		super();
		name.put(_name, null);
	}
}
