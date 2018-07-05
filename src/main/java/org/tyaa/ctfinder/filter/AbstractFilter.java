package org.tyaa.ctfinder.filter;

import java.lang.reflect.*;
import com.google.common.base.Defaults;

public abstract class AbstractFilter {

	public static <T> void reset(Class<T> klazz) {
		
		Field[] fields = klazz.getDeclaredFields();
        
        for (Field field : fields) {
        	
        	field.setAccessible(true);
            try {
				field.set(null, Defaults.defaultValue(klazz));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}
