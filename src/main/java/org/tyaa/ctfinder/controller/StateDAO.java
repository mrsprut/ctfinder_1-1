package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.tyaa.ctfinder.entity.City;
import org.tyaa.ctfinder.entity.State;

public class StateDAO {
	
	public static void getState(String _id, State _state) {
		
		State state =
				ofy().load().type(State.class).id(Long.valueOf(_id)).now();
		
		if(state != null) {
			
			_state.setId(state.getId());
			_state.setTitle_key(state.getTitle_key());
		}
	}
	
	public static void getState(Long _id, State _state) {
		
		State state =
				ofy().load().type(State.class).id(_id).now();
		
		if(state != null) {
			
			_state.setId(state.getId());
			_state.setTitle_key(state.getTitle_key());
		}
	}
	
	public static void getStateByTitleKey(String _titleKey, State _state) {
		
		State state =
				ofy().load()
				.type(State.class)
				.filter("title_key", _titleKey)
				.first()
				.now();
		
		if(state != null) {
			
			_state.setId(state.getId());
			_state.setTitle_key(state.getTitle_key());
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
