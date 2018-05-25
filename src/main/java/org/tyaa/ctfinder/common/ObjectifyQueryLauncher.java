package org.tyaa.ctfinder.common;

import java.io.PrintWriter;

import org.tyaa.ctfinder.api.interfaces.IObjectifyFun;
import org.tyaa.ctfinder.api.interfaces.IObjectifyFun2;
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
						RespData result = new RespData(ex.getMessage());
                        String resultJsonString = _gson.toJson(result);
                        _out.print(resultJsonString);
					}
    		    }
    		});
    	} catch (Exception ex) {
    		RespData result = new RespData(ex.getMessage());
            String resultJsonString = _gson.toJson(result);
            _out.print(resultJsonString);
    	}
	}
	
	public static <T1, T2> void objectifyRun2(T1 _object1, T2 _object2, IObjectifyFun2<T1, T2> _function, PrintWriter _out, Gson _gson) {
		try {
    		ObjectifyService.run(new VoidWork() {
    		    public void vrun() {
    		    	try {
    		    		_function.doWork(_object1, _object2);
					} catch (Exception ex) {
						RespData result = new RespData(ex.getMessage());
                        String resultJsonString = _gson.toJson(result);
                        _out.print(resultJsonString);
					}
    		    }
    		});
    	} catch (Exception ex) {
    		RespData result = new RespData(ex.getMessage());
            String resultJsonString = _gson.toJson(result);
            _out.print(resultJsonString);
    	}
	}
}
