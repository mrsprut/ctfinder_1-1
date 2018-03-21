package org.tyaa.ctfinder.model;

import java.util.ArrayList;
//import java.util.List;

import com.google.gson.annotations.Expose;

public class RespData {

	@Expose
	public ArrayList result = new ArrayList();
	@Expose
	public String error = "";
	
	public RespData() {}
	
	public RespData(ArrayList result) {
		
		super();
		if(result != null && result.size() > 0) {
			
			this.result.addAll(result);
		}
	}
	
	public RespData(String error) {
		
		super();
		this.error = error;
	}
	
	public RespData(ArrayList result, String error) {
		
		super();
		if(result != null && result.size() > 0) {
			
			this.result.addAll(result);
		}
		this.error = error;
	}
}
