package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Title extends Static_title {
	
	public Title() {
	}
	
	public Title(String key, Long lang_id, String content) {
		super(key, lang_id, content);
	}
}
