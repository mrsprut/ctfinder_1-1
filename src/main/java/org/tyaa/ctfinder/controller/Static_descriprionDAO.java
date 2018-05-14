package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Static_description;

public class Static_descriprionDAO {
	
	public static void getStatic_description(String _id, Static_description _static_description) {
		
		Static_description static_descriprion =
				ofy().load().type(Static_description.class).id(Long.valueOf(_id)).now();
		
		if(static_descriprion != null) {
			
			_static_description.setId(static_descriprion.getId());
			_static_description.setKey(static_descriprion.getKey());
			_static_description.setLang_id(static_descriprion.getLang_id());
			_static_description.setContent(static_descriprion.getContent());
		}
	}

	public static void createStatic_descriprion(Static_description _static_descriprion) {

		ofy().save().entity(_static_descriprion).now();
	}
}
