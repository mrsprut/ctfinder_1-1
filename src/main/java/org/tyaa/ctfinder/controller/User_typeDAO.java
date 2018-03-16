package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.User_type;

public class User_typeDAO {
	
	public static void getUser_type(String _id, User_type _user_type) {
		
		User_type user_type =
				ofy().load().type(User_type.class).id(Long.valueOf(_id)).now();
		
		if(user_type != null) {
			
			_user_type.setId(user_type.getId());
			_user_type.setTitle_key(user_type.getTitle_key());
		}
	}

	public static void createUser_type(User_type _user_type) {

		ofy().save().entity(_user_type).now();
	}
}
