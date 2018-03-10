package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Language {
@Id
private int id;
private String title_key;
private String code;
public Language(int id, String title_key, String code) {
	super();
	this.id = id;
	this.title_key = title_key;
	this.code = code;
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
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}

}
