package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Country {
@Id
private int id;
private int title_id;
public Country(int id, int title_id) {
	super();
	this.id = id;
	this.title_id = title_id;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getTitle_id() {
	return title_id;
}
public void setTitle_id(int title_id) {
	this.title_id = title_id;
}

}
