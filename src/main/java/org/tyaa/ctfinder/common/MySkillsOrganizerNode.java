package org.tyaa.ctfinder.common;

public class MySkillsOrganizerNode {

	public String user;
	
	public String title;
	public String type;
	public String description;
	public String status;
	public Boolean access;
	
	public Integer left;
	public Integer top;
	public Integer radius;
	
	public MySkillsOrganizerNode(
			String user
			, String title
			, String type
			, String description
			, String status
			, Boolean access
			, Integer left
			, Integer top
			, Integer radius
		) {
		super();
		this.user = user;
		this.title = title;
		this.type = type;
		this.description = description;
		this.status = status;
		this.access = access;
		this.left = left;
		this.top = top;
		this.radius = radius;
	}
	
	
}
