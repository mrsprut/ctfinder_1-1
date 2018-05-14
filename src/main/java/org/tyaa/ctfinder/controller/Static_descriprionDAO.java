package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Static_description;;

public class Static_descriprionDAO {
	
	public static void getStatic_descriprion(String _id, Static_description _static_description) {
		
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
