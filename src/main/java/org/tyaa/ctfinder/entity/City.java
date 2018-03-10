package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class City {
@Id
private int id;
private int country_id;
private int title_id;
public City(int id, int country_id, int title_id) {
	super();
	this.id = id;
	this.country_id = country_id;
	this.title_id = title_id;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getCountry_id() {
	return country_id;
}
public void setCountry_id(int country_id) {
	this.country_id = country_id;
}
public int getTitle_id() {
	return title_id;
}
public void setTitle_id(int title_id) {
	this.title_id = title_id;
}

}
