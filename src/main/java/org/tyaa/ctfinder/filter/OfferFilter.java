package org.tyaa.ctfinder.filter;

//import java.util.Date;

public class OfferFilter extends AbstractFilter {
	
	public enum Order {Asc, Desc}

	public String createdDateFrom = null;
	public String createdDateTo = null;
	public String[] projection = null;
	//public Long userId = null;
	
	public Order orderByCreated = null;
	public Order orderByUrgency = null;
	public Order orderByStart = null;
	public Order orderByFinish = null;
	
	public String titleKey = null;
}
