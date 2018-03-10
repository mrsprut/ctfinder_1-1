package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Intention {
@Id
private int id;
private int user_id;
private String title_key;
private String description_key;
private int state_id;
private int type_id;
private int country_id;
private int city_id;
public Intention(int id, int user_id, String title_key, String description_key, int state_id, int type_id,
		int country_id, int city_id) {
	super();
	this.id = id;
	this.user_id = user_id;
	this.title_key = title_key;
	this.description_key = description_key;
	this.state_id = state_id;
	this.type_id = type_id;
	this.country_id = country_id;
	this.city_id = city_id;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public String getTitle_key() {
	return title_key;
}
public void setTitle_key(String title_key) {
	this.title_key = title_key;
}
public String getDescription_key() {
	return description_key;
}
public void setDescription_key(String description_key) {
	this.description_key = description_key;
}
public int getState_id() {
	return state_id;
}
public void setState_id(int state_id) {
	this.state_id = state_id;
}
public int getType_id() {
	return type_id;
}
public void setType_id(int type_id) {
	this.type_id = type_id;
}
public int getCountry_id() {
	return country_id;
}
public void setCountry_id(int country_id) {
	this.country_id = country_id;
}
public int getCity_id() {
	return city_id;
}
public void setCity_id(int city_id) {
	this.city_id = city_id;
}

}
