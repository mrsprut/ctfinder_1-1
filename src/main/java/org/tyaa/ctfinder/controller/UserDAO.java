package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.User;
import org.tyaa.ctfinder.entity.User_type;

public class UserDAO {
	
	public static void getUser(String _id, User _user) {
		
		//Пытаемся получить из БД объект Пользователь
		//по его ИД
		User user =
				ofy().load().type(User.class).id(Long.valueOf(_id)).now();
		
		//Если найден
		if(user != null) {
			
			//В выходной объект Пользователь копируем из
			//найденного объекта значения всех полей
			_user.setId(user.getId());
			_user.setGoogle_id(user.getGoogle_id());
			_user.setName(user.getName());
			_user.setEmail(user.getEmail());
			_user.setPicture(user.getPicture());
			_user.setUser_type_id(user.getUser_type_id());
		}
	}
	
	public static void findUserByGoogleId(String _id, User _user) {
			
		ofy().clear();
		//Пытаемся получить из БД объект Пользователь
		//по его ИД
		User user =
			ofy().load()
			.type(User.class)
			.filter("google_id", _id)
			.first()
			.now();
		
		//Если найден
		if(user != null) {
			
			//В выходной объект Пользователь копируем из
			//найденного объекта значения всех полей
			_user.setId(user.getId());
			_user.setGoogle_id(user.getGoogle_id());
			_user.setName(user.getName());
			_user.setEmail(user.getEmail());
			_user.setPicture(user.getPicture());
			_user.setUser_type_id(user.getUser_type_id());
		}
	}

	public static void createUser(User _user) {
		
		ofy().clear();
		//Находим в БД локализованный заголовок user
		Static_title staticTitle =
				ofy().load()
				.type(Static_title.class)
				.filter("content", "user")
				.first()
				.now();
		
		if(staticTitle != null) {
			
			//По ключу локализованного заголовка user
			//находим в БД объект ТипПользователя
			User_type user_type =
				ofy().load()
				.type(User_type.class)
				.filter("title_key", staticTitle.getKey())
				.first()
				.now();
	        
			//Устанавливаем тип пользователя user
			_user.setUser_type_id(user_type.getId());
		}
	
        ofy().save().entity(_user).now();
	}
	
	public static void udateUser(User _user) {
		
		/*//Ищем пользователя в БД
		User user = new User();
		//Временно зануляем идентификатор
		Long id = _user.getId();
		_user.setId(null);
		getUser(String.valueOf(id), _user);
		
		//Если найден
		if(_user.getId() != null) {
			
			//В сохраняемый объект Пользователь копируем из
			//внешнего объекта значения всех полей
			user.setGoogle_id(_user.getGoogle_id());
			user.setName(_user.getName());
			user.setEmail(_user.getEmail());
			user.setPicture(_user.getPicture());
			user.setUser_type_id(_user.getUser_type_id());
		}
		
		//Обновляем данные пользователя в БД методом save
		ofy().save().entity(user).now();
		
		//В выходной объект Пользователь возвращаем
		//значение идентификатора
		_user.setId(user.getId());*/
		ofy().save().entity(_user).now();
	}
}
