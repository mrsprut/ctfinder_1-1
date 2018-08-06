package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Iterator;
import java.util.List;

import org.tyaa.ctfinder.common.CopyHelper;
import org.tyaa.ctfinder.entity.Description;
import org.tyaa.ctfinder.entity.Title;

public class DescriptionDAO {
	
	public static void getDescription(String _id, Description _description) {
		
		Description description =
				ofy().load().type(Description.class).id(Long.valueOf(_id)).now();
		
		if(description != null) {
			
			_description.setId(description.getId());
			_description.setKey(description.getKey());
			_description.setLang_id(description.getLang_id());
			_description.setContent(description.getContent());
		}
	}
	
	public static void getDescriptionByKey(
			String _key
			, Description _description
			) {
		
		Description description =
				ofy().load()
				.type(Description.class)
				.filter("key", _key)
				.first()
				.now();
		
		if(description != null) {
			
			_description.setId(description.getId());
			_description.setKey(description.getKey());
			_description.setLang_id(description.getLang_id());
			_description.setContent(description.getContent());
		}
	}
	
	public static void getDescriptionsByKey(
			String _key
			, List<Description> _descriptions
			) {
		
		List<Description> descriptions =
				ofy().load()
				.type(Description.class)
				.filter("key", _key)
				.list();
		
		if(descriptions != null) {
			
			_descriptions.addAll(descriptions);
		}
	}
	
	public static void getDescriptionByKeyAndLang(
			String _key
			, Long _langId
			, Description _description
			) {
		
		Description description =
				ofy().load()
				.type(Description.class)
				.filter("key", _key)
				.filter("lang_id", _langId)
				.first()
				.now();
		
		if(description != null) {
			
			_description.setId(description.getId());
			_description.setKey(description.getKey());
			_description.setLang_id(description.getLang_id());
			_description.setContent(description.getContent());
		}
	}

	public static void createDescriprion(Description _description) {

		ofy().save().entity(_description).now();
	}
	
	public static void delete(Description _description) {

		ofy().delete().entity(_description).now();
	}
}
