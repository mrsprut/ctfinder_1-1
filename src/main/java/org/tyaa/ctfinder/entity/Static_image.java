package org.tyaa.ctfinder.entity;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Static_image {
@Id
private int id;
private String key;
private int lang_id;
private Blob image;
public Static_image(int id, String key, int lang_id, Blob image) {
	super();
	this.id = id;
	this.key = key;
	this.lang_id = lang_id;
	this.image = image;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getKey() {
	return key;
}
public void setKey(String key) {
	this.key = key;
}
public int getLang_id() {
	return lang_id;
}
public void setLang_id(int lang_id) {
	this.lang_id = lang_id;
}
public Blob getImage() {
	return image;
}
public void setImage(Blob image) {
	this.image = image;
}

}
