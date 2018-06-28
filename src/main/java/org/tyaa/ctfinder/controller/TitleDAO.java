package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

//import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Title;

public class TitleDAO {
	
	public static void getTitle(String _id, Title _title) {
		
		Title title =
				ofy().load().type(Title.class).id(Long.valueOf(_id)).now();
		
		if(title != null) {
			
			_title.setId(title.getId());
			_title.setLang_id(title.getLang_id());
			_title.setKey(title.getKey());
			_title.setContent(title.getContent());
		}
	}
	
	public static void getTitleByKey(String _key, Title _title) {
		
		Title title =
				ofy().load()
				.type(Title.class)
				.filter("key", _key)
				.first()
				.now();
		
		if(title != null) {
			
			_title.setId(title.getId());
			_title.setKey(title.getKey());
			_title.setLang_id(title.getLang_id());
			_title.setContent(title.getContent());
		}
	}
	
	/*public static void getStaticTitleByContentAndLang(
			String _content
			, Long _langId
			, Static_title _static_title
			) {
		
		Static_title Static_title =
				ofy().load()
				.type(Static_title.class)
				.filter("content", _content)
				.filter("lang_id", _langId)
				.first()
				.now();
		
		if(Static_title != null) {
			
			_static_title.setId(Static_title.getId());
			_static_title.setKey(Static_title.getKey());
			_static_title.setLang_id(Static_title.getLang_id());
			_static_title.setContent(Static_title.getContent());
		}
	}*/

	public static void createTitle(Title _title) {

		ofy().save().entity(_title).now();
	}
}
