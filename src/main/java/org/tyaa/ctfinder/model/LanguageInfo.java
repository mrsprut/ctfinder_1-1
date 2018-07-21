package org.tyaa.ctfinder.model;

import com.google.gson.annotations.Expose;

public class LanguageInfo {

	@Expose
	public String code;
	@Expose
	public String name;
	@Expose
	public Boolean active;
	
	public LanguageInfo() {}

	public LanguageInfo(String code, String name, Boolean active) {
		super();
		this.code = code;
		this.name = name;
		this.active = active;
	}
}
