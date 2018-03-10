package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Story {
@Id
private int id;
private String title_key;
private String image_key;
public Story(int id, String title_key, String image_key) {
	super();
	this.id = id;
	this.title_key = title_key;
	this.image_key = image_key;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTitle_key() {
	return title_key;
}
public void setTitle_key(String title_key) {
	this.title_key = title_key;
}
public String getImage_key() {
	return image_key;
}
public void setImage_key(String image_key) {
	this.image_key = image_key;
}

}
