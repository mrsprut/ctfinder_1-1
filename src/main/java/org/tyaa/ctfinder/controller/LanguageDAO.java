package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.State;

public class LanguageDAO {
	
	public static void getLang(String _id, Language _language) {
		
		getLang(Long.valueOf(_id), _language);
	}
	
	public static void getLang(Long _id, Language _language) {
		
		Language language =
				ofy().load().type(Language.class).id(Long.valueOf(_id)).now();
		
		if(language != null) {
			
			_language.setId(language.getId());
			_language.setCode(language.getCode());
			_language.setTitle_key(language.getTitle_key());
		}
	}
	
	public static void getLangByCode(String _code, Language _language) {
		
		Language language =
				ofy().load()
				.type(Language.class)
				.filter("code", _code)
				.first()
				.now();
		
		if(language != null) {
			
			_language.setId(language.getId());
			_language.setCode(language.getCode());
			_language.setTitle_key(language.getTitle_key());
		}
	}
	
	public static void getAll(List<Language> _languageList) {
		
		_languageList.addAll(ofy().load().type(Language.class).list());
	}

	public static void createLang(Language _language) {

		ofy().save().entity(_language).now();
	}
	
	public static void updateLang(Language _language) {

		ofy().save().entity(_language).now();
	}
}
