package org.tyaa.ctfinder.common;

import javax.servlet.http.HttpSession;

public class SessionAttributes {

	public final static String userId = "user_id";
	public final static String languageId = "language_id";
	
	public static boolean isSessionAttrSet(HttpSession _session, String _attributeString) {
		
		if(_session != null
			&& _session.getAttribute(_attributeString) != null) {
			
			return true;
		} else {
		
			return false;
		}
	}
}
