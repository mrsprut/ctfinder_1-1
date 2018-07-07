package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Static_description;

public class Static_descriprionDAO {
	
	public static void getStatic_description(String _id, Static_description _static_description) {
		
		Static_description static_description =
				ofy().load().type(Static_description.class).id(Long.valueOf(_id)).now();
		
		if(static_description != null) {
			
			_static_description.setId(static_description.getId());
			_static_description.setKey(static_description.getKey());
			_static_description.setLang_id(static_description.getLang_id());
			_static_description.setContent(static_description.getContent());
		}
	}
	
	public static void getStaticDescriptionByKey(String _key, Static_description _static_description) {
		
		Static_description static_description =
				ofy().load()
				.type(Static_description.class)
				.filter("key", _key)
				.first()
				.now();
		
		if(static_description != null) {
			
			_static_description.setId(static_description.getId());
			_static_description.setKey(static_description.getKey());
			_static_description.setLang_id(static_description.getLang_id());
			_static_description.setContent(static_description.getContent());
		}
	}
	
	public static void getStaticDescriptionByKeyAndLang(
			String _key
			, Long _langId
			, Static_description _static_description
			) {
		
		Static_description static_description =
				ofy().load()
				.type(Static_description.class)
				.filter("key", _key)
				.filter("lang_id", _langId)
				.first()
				.now();
		
		if(static_description != null) {
			
			_static_description.setId(static_description.getId());
			_static_description.setKey(static_description.getKey());
			_static_description.setLang_id(static_description.getLang_id());
			_static_description.setContent(static_description.getContent());
		}
	}

	public static void createStatic_descriprion(Static_description _static_descriprion) {

		ofy().save().entity(_static_descriprion).now();
	}
}
