package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.tyaa.ctfinder.entity.State;

public class StateDAO {
	
	public static void getState(String _id, State _State) {
		
		State State =
				ofy().load().type(State.class).id(Long.valueOf(_id)).now();
		
		if(State != null) {
			
			_State.setId(State.getId());
			_State.setTitle_key(State.getTitle_key());
		}
	}
	
	//Получение всех States в виде списка
	public static void getAllStates(List<State> _stateList) {
			
		_stateList.addAll(ofy().load().type(State.class).list());
	}

	public static void createState(State _State) {

		ofy().save().entity(_State).now();
	}
}
