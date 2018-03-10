package org.tyaa.ctfinder.controller;

import com.googlecode.objectify.*;
import static com.googlecode.objectify.ObjectifyService.ofy;

import org.tyaa.ctfinder.entity.User;

public class UserDAO {
	
	public static void getUser(String _id, User _user) {
		
		User user =
				ofy().load().type(User.class).id(Long.valueOf(_id)).now();
		
		/*_emptyOrder.setId(order.getId());
		_emptyOrder.setCustomer(order.getCustomer());
		_emptyOrder.setText(order.getText());
		_emptyOrder.setDate(order.getDate());
		_emptyOrder.setStateId(order.getStateId());*/
		
		if(user != null) {
			_user.setGoogle_id(user.getGoogle_id());
			_user.setName(user.getName());
			_user.setEmail(user.getEmail());
			_user.setPicture(user.getPicture());
			_user.setUser_type_id(2);
		}
	}

	public static void createUser(User _user) {
		
		/*State state =
        		ofy().load()
        		.type(State.class)
        		.filter("name", "created")
        		.first()
        		.now();*/
        
		
	
        /*Order1 order1 = new Order1();
        order1.setCustomer(_customerName);
        order1.setText(_taskText);
        order1.setDate(new Date());
        order1.setStateId(state.getId());
        ofy().save().entity(order1).now();*/
	}
}
