package org.tyaa.ctfinder.common;

import java.lang.reflect.Field;

public class CopyHelper {

	public static <T> void copy(T _from, T _to) throws IllegalArgumentException, IllegalAccessException {
		
		Class<T> _clazz = (Class<T>) _from.getClass();
		Field[] fields = _clazz.getDeclaredFields();
        
        for (Field field : fields) {
        	
        	field.setAccessible(true);
        	field.set(_to, field.get(_from));
        }
	}
}
