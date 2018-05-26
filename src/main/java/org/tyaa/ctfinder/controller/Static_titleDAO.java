package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Static_title;
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
	
	public static void getStaticTitleByKey(String _key, Static_title _Static_title) {
		
		Static_title Static_title =
				ofy().load()
				.type(Static_title.class)
				.filter("key", _key)
				.first()
				.now();
		
		if(Static_title != null) {
			
			_Static_title.setId(Static_title.getId());
			_Static_title.setKey(Static_title.getKey());
			_Static_title.setLang_id(Static_title.getLang_id());
			_Static_title.setContent(Static_title.getContent());
		}
	}
	
	/*public static void getStaticTitleByKeyAndSubstring(
			String _key
			, Static_title _Static_title
			, String _substring) {
		
		Static_title Static_title =
				ofy().load()
				.type(Static_title.class)
				.filter("key", _key)
				.first()
				.now();
		
		if(Static_title != null) {
			
			_Static_title.setId(Static_title.getId());
			_Static_title.setKey(Static_title.getKey());
			_Static_title.setLang_id(Static_title.getLang_id());
			_Static_title.setContent(Static_title.getContent());
		}
	}*/

	public static void createStatic_title(Static_title _static_title) {

		ofy().save().entity(_static_title).now();
	}
}
