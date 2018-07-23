package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;

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
			
			_static_title.created_at = static_title.created_at;
		}
	}
	
	public static void getStaticTitleByKey(String _key, Static_title _static_title) {
		
		Static_title static_title =
				ofy().load()
				.type(Static_title.class)
				.filter("key", _key)
				.first()
				.now();
		
		if(static_title != null) {
			
			_static_title.setId(static_title.getId());
			_static_title.setKey(static_title.getKey());
			_static_title.setLang_id(static_title.getLang_id());
			_static_title.setContent(static_title.getContent());
			
			_static_title.created_at = static_title.created_at;
		}
	}
	
	public static void getStaticTitleByKeyAndLang(
			String _key
			, Long _langId
			, Static_title _static_title
			) {
		
		Static_title static_title =
				ofy().load()
				.type(Static_title.class)
				.filter("key", _key)
				.filter("lang_id", _langId)
				.first()
				.now();
		
		if(static_title != null) {
			
			_static_title.setId(static_title.getId());
			_static_title.setKey(static_title.getKey());
			_static_title.setLang_id(static_title.getLang_id());
			_static_title.setContent(static_title.getContent());
			
			_static_title.created_at = static_title.created_at;
		}
	}
	
	public static void getStaticTitleByContentAndLang(
			String _content
			, Long _langId
			, Static_title _static_title
			) {
		
		Static_title static_title =
				ofy().load()
				.type(Static_title.class)
				.filter("content", _content)
				.filter("lang_id", _langId)
				.first()
				.now();
		
		if(static_title != null) {
			
			_static_title.setId(static_title.getId());
			_static_title.setKey(static_title.getKey());
			_static_title.setLang_id(static_title.getLang_id());
			_static_title.setContent(static_title.getContent());
			
			_static_title.created_at = static_title.created_at;
		}
	}

	public static void createStatic_title(Static_title _static_title) {

		if(_static_title.created_at == null) {
			_static_title.created_at = new Date();
		}
		ofy().save().entity(_static_title).now();
	}
}
