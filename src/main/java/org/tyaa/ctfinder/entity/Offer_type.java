package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Offer_type {
@Id
private int id;
private String description_key;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getDescription_key() {
	return description_key;
}
public void setDescription_key(String description_key) {
	this.description_key = description_key;
}
public Offer_type(int id, String description_key) {
	super();
	this.id = id;
	this.description_key = description_key;
}

}
