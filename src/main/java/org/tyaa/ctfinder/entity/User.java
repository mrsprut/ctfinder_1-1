package org.tyaa.ctfinder.entity;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {
@Id
private int id;
private String google_id;
private String name;
private String email;
private Blob picture;
private int user_type_id;
public User(int id, String google_id, String name, String email, Blob picture, int user_type_id) {
	super();
	this.id = id;
	this.google_id = google_id;
	this.name = name;
	this.email = email;
	this.picture = picture;
	this.user_type_id = user_type_id;
}
public User() {
	// TODO Auto-generated constructor stub
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getGoogle_id() {
	return google_id;
}
public void setGoogle_id(String google_id) {
	this.google_id = google_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public Blob getPicture() {
	return picture;
}
public void setPicture(Blob picture) {
	this.picture = picture;
}
public int getUser_type_id() {
	return user_type_id;
}
public void setUser_type_id(int user_type_id) {
	this.user_type_id = user_type_id;
}

}
