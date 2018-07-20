package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.tyaa.ctfinder.common.CopyHelper;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.Subscription;

public class SubscriptionDAO {
	
	public static void getSubscription(String _id, Subscription _subscription) {
		
		getSubscription(Long.valueOf(_id), _subscription);
	}
	
	public static void getSubscription(Long _id, Subscription _subscription) {
		
		//Пытаемся получить из БД объект Пользователь
		//по его ИД
		Subscription subscription =
				ofy().load().type(Subscription.class).id(_id).now();
		
		//Если найден
		if(subscription != null) {
			
			//В выходной объект Пользователь копируем из
			//найденного объекта значения всех полей
			_subscription.setId(subscription.getId());
			_subscription.setAuthor_id(subscription.getAuthor_id());
			_subscription.setSubscriber_id(subscription.getSubscriber_id());
		}
	}
	
	public static void findByAuthorAndSubscriber(
			Long _authorId
			, Long _subscriberId
			, Subscription _subscription
		) throws IllegalArgumentException, IllegalAccessException {
			
		ofy().clear();
		//Пытаемся получить из БД объект
		//по ИД's
		Subscription subscription =
			ofy().load()
			.type(Subscription.class)
			.filter("author_id", _authorId)
			.filter("subscriber_id", _subscriberId)
			.first()
			.now();
		
		//Если найден
		if(subscription != null) {
			
			//В выходной объект копируем из
			//найденного объекта значения всех полей
			CopyHelper.copy(subscription, _subscription);
		}
	}
	
	public static void findListByAuthor(
			Long _authorId
			, List<Subscription> _subscriptions
		) throws IllegalArgumentException, IllegalAccessException {
			
		ofy().clear();
		//Пытаемся получить из БД объект
		//по ИД's
		List<Subscription> subscriptions =
			ofy().load()
			.type(Subscription.class)
			.filter("author_id", _authorId)
			.list();
		
		//Если найден
		if(subscriptions != null) {
			
			//В выходной объект копируем
			_subscriptions.addAll(subscriptions);
		}
	}

	public static void createSubscription(Subscription _subscription) {
		
        ofy().save().entity(_subscription).now();
	}
	
	public static void removeSubscription(Long _subscriptionId) {
		
		ofy().delete().type(Subscription.class).id(_subscriptionId).now();
	}
}
