package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.tyaa.ctfinder.entity.Country;

public class CountryDAO {
	
	public static void getCountry(String _id, Country _сountry) {
		
		Country сountry =
				ofy().load().type(Country.class).id(Long.valueOf(_id)).now();
		
		if(сountry != null) {
			
			_сountry.setId(сountry.getId());
			_сountry.setTitle_key(сountry.getTitle_key());
		}
	}
	
	//Получение всех Countries в виде списка
	public static void getAllCountries(List<Country> _CountryList) {
			
		_CountryList.addAll(ofy().load().type(Country.class).list());
	}
	
	public static void getCountryByTitleKey(String _titleKey, Country _сountry) {
		
		Country сountry =
				ofy().load()
				.type(Country.class)
				.filter("title_key", _titleKey)
				.first()
				.now();
		
		if(сountry != null) {
			
			_сountry.setId(сountry.getId());
			_сountry.setTitle_key(сountry.getTitle_key());
		}
	}

	public static void createCountry(Country _country) {

		ofy().save().entity(_country).now();
	}
}
