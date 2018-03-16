package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Static_title;

public class Static_titleDAO {
	
	public static void getStatic_title(String _id, Static_title _static_title) {
		
		Static_title static_title =
				ofy().load().type(Static_title.class).id(Long.valueOf(_id)).now();
		
		if(static_title != null) {
			
			_static_title.setId(static_title.getId());
			_static_title.setLang_id(static_title.getLang_id());
			_static_title.setKey(static_title.getKey());
			_static_title.setContent(static_title.getContent());
		}
	}

	public static void createStatic_title(Static_title _static_title) {

		ofy().save().entity(_static_title).now();
	}
}
