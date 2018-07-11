package org.tyaa.ctfinder.common;

import java.io.PrintWriter;

import org.tyaa.ctfinder.api.interfaces.IObjectifyFun;
import org.tyaa.ctfinder.api.interfaces.IObjectifyFun2;
import org.tyaa.ctfinder.api.interfaces.IObjectifyFun3;
import org.tyaa.ctfinder.model.RespData;

import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;

public class ObjectifyQueryLauncher {
	
	public static <T> void objectifyRun(T _object, IObjectifyFun<T> _function, PrintWriter _out, Gson _gson) {
		try {
    		ObjectifyService.run(new VoidWork() {
    		    public void vrun() {
    		    	try {
    		    		_function.doWork(_object);
					} catch (Exception ex) {
						
                        printException(ex, _out, _gson);
					}
    		    }
    		});
    	} catch (Exception ex) {
    		
    		printException(ex, _out, _gson);
    	}
	}
	
	public static <T1, T2> void objectifyRun2(T1 _object1, T2 _object2, IObjectifyFun2<T1, T2> _function, PrintWriter _out, Gson _gson) {
		try {
    		ObjectifyService.run(new VoidWork() {
    		    public void vrun() {
    		    	try {
    		    		_function.doWork(_object1, _object2);
					} catch (Exception ex) {
						
						printException(ex, _out, _gson);
					}
    		    }
    		});
    	} catch (Exception ex) {
    		
    		printException(ex, _out, _gson);
    	}
	}
	
	public static <T1, T2, T3> void objectifyRun3(
		T1 _object1
		, T2 _object2
		, T3 _object3
		, IObjectifyFun3<T1, T2, T3> _function
		, PrintWriter _out
		, Gson _gson
	) {
		try {
    		ObjectifyService.run(new VoidWork() {
    		    public void vrun() {
    		    	try {
    		    		_function.doWork(_object1, _object2, _object3);
					} catch (Exception ex) {
						
						printException(ex, _out, _gson);
					}
    		    }
    		});
    	} catch (Exception ex) {
    		/*RespData result = new RespData(ex.getMessage());
            String resultJsonString = _gson.toJson(result);
            _out.print(resultJsonString);*/
            printException(ex, _out, _gson);
    	}
	}
	
	public static void printException(Exception _ex, PrintWriter _out, Gson _gson) {
		
		RespData rd = new RespData("exception");
		if(_ex.getMessage() == null) {
			
			String errorTrace = "";
			for(StackTraceElement el: _ex.getStackTrace()) {
				errorTrace += el.toString();
			}
			if(errorTrace.equals("")) {
				//printException(_ex, _out, _gson);
				
			} else {
				rd = new RespData(errorTrace);
			}
		} else {
		
			rd = new RespData(_ex.getMessage());
			//rd = new RespData("unknown_exception");
		}
		String errorJson = _gson.toJson(rd);
		_out.print(errorJson);
	}
}
