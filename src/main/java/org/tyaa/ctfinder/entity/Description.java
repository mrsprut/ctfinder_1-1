package org.tyaa.ctfinder.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Description extends Static_description {
	
	public Description() {
		super();
	}

	public Description(String key, Long lang_id, String content) {
		super(key, lang_id, content);
	}
}
