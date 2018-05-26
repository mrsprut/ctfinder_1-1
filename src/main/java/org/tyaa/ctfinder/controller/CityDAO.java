package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.tyaa.ctfinder.entity.City;

public class CityDAO {
	
	public static void getCity(String _id, City _city) {
		
		City city =
				ofy().load().type(City.class).id(Long.valueOf(_id)).now();
		
		if(city != null) {
			
			_city.setId(city.getId());
			_city.setTitle_key(city.getTitle_key());
		}
	}
	
	//Получение всех Countries в виде списка
	public static void getAllCountries(List<City> _сityList) {
			
		_сityList.addAll(ofy().load().type(City.class).list());
	}
	
	public static void getCityByTitleKey(String _titleKey, City _city) {
		
		City city =
				ofy().load()
				.type(City.class)
				.filter("title_key", _titleKey)
				.first()
				.now();
		
		if(city != null) {
			
			_city.setId(city.getId());
			_city.setTitle_key(city.getTitle_key());
		}
	}

	public static void createCity(City _city) {

		ofy().save().entity(_city).now();
	}
}
