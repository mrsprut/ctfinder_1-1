package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

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
	
	public static void getTitlesByKey(String _key, List<Title> _titles) {
		
		List<Title> titles =
				ofy().load()
				.type(Title.class)
				.filter("key", _key)
				.list();
		
		if(titles.size() > 0) {
			
			_titles.addAll(titles);
		}
	}
	
	public static void getTitleBySubstring(String _content, Title _title) {
		
		Title title =
				ofy().load()
				.type(Title.class)
				.filter("content", _content)
				.first()
				.now();
		
		if(title != null) {
			
			_title.setId(title.getId());
			_title.setKey(title.getKey());
			_title.setLang_id(title.getLang_id());
			_title.setContent(title.getContent());
		}
	}
	
	public static void getTitleByKeyAndLang(
			String _key
			, Long _langId
			, Title _title
			) {
		
		Title title =
				ofy().load()
				.type(Title.class)
				.filter("key", _key)
				.filter("lang_id", _langId)
				.first()
				.now();
		
		if(title != null) {
			
			_title.setId(title.getId());
			_title.setKey(title.getKey());
			_title.setLang_id(title.getLang_id());
			_title.setContent(title.getContent());
		}
	}
	//Получение объекта заголовка по ключу и началу строки / целой строке
	public static void getTitleByKeyAndStart(
			String _key
			, String _startString
			, List<Title> _titles
			) {
		
		List<Title> titles =
				ofy().load()
				.type(Title.class)
				.filter("key", _key)
				.filter("content >=", _startString)
				.filter("content <", _startString + "\uFFFD")
				.list();
		
		if(titles.size() > 0) {
			
			_titles.addAll(titles);
		}
	}

	public static void createTitle(Title _title) {

		ofy().save().entity(_title).now();
	}
}
