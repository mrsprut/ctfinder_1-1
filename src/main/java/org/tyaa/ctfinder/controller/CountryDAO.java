package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.tyaa.ctfinder.entity.Country;

public class CountryDAO {
	
	public static void getCountry(String _id, Country _Country) {
		
		Country Country =
				ofy().load().type(Country.class).id(Long.valueOf(_id)).now();
		
		if(Country != null) {
			
			_Country.setId(Country.getId());
			_Country.setTitle_key(Country.getTitle_key());
		}
	}
	
	//Получение всех Countries в виде списка
	public static void getAllCountries(List<Country> _CountryList) {
			
		_CountryList.addAll(ofy().load().type(Country.class).list());
	}
	
	//1. "substring"
	//2. allCountries -> allCountryTitles
	//3. allCountryTitles where (lang && content.contains("substring"))

	public static void createCountry(Country _Country) {

		ofy().save().entity(_Country).now();
	}
}
