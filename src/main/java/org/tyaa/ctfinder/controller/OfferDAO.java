package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.tyaa.ctfinder.entity.Description;
import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Offer;
import org.tyaa.ctfinder.entity.Offer_type;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.User_type;
import org.tyaa.ctfinder.filter.OfferFilter;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

public class OfferDAO {
	
	//private OfferFilter filter = new OfferFilter();
	public static enum Params {
		
		OfferList
		, Limit
		, CursorStringArray
		, UserId
		, Filter
		, InMemory
	}
	
	public static void getOffer(String _id, Offer _offer) {
		
		Offer offer =
				ofy().load().type(Offer.class).id(Long.valueOf(_id)).now();
		
		if(offer != null) {
			
			//TODO improve using reflection
			_offer.setId(offer.getId());
			_offer.setOffer_type_id(offer.getOffer_type_id());
			_offer.setState_id(offer.getState_id());
			_offer.setTitle_key(offer.getTitle_key());
			_offer.setDescription_key(offer.getDescription_key());
			_offer.setUser_id(offer.getUser_id());
			_offer.setCountry_id(offer.getCountry_id());
			_offer.setCity_id(offer.getCity_id());
			_offer.setCollaborators_count(offer.getCollaborators_count());
			_offer.setImage(offer.getImage());
			_offer.setStart_date(offer.getStart_date());
			_offer.setFinish_date(offer.getFinish_date());
			_offer.setUrgency_in_days(offer.getUrgency_in_days());
			_offer.setStarted_at(offer.getStarted_at());
			_offer.setCompleted_at(offer.getCompleted_at());
			_offer.setCreated_at(offer.getCreated_at());
			_offer.setUpdated_at(offer.getUpdated_at());
		}
	}
	
	//Получение всех в виде списка
	public static void getAllOffers(List<Offer> _offerList) {
			
		_offerList.addAll(ofy().load().type(Offer.class).list());
	}
	
	//Получение в виде списка
	public static void getOffersRange(Map<Params, Object> _paramsMap) {
			
		List<Offer> _offerList =
				(List<Offer>) _paramsMap.get(Params.OfferList);
		_offerList.clear();
		ofy().clear();
		Query<Offer> query =
				ofy().load().type(Offer.class);
		
		if(_paramsMap.get(Params.InMemory) != null && (boolean) _paramsMap.get(Params.InMemory)) {
			
			Long userId = (Long) _paramsMap.get(Params.UserId);
			query = query.filter("user_id", userId);
			_offerList.addAll(query.list());
		} else {
			
			Integer _limit = (Integer) _paramsMap.get(Params.Limit);
			String[] _cursorStr = (String[]) _paramsMap.get(Params.CursorStringArray);
			OfferFilter offerFilter = (OfferFilter) _paramsMap.get(Params.Filter);
			
			/*Iterable<Key<Offer>> userOffersKeys =
					ofy().load().type(Offer.class).keys();*/
			
			//.order("-created_at");
			
			//Filter by ...
			
			if(offerFilter.createdDateFrom != null) {
				
				query = query.filter("created_at >=", offerFilter.createdDateFrom);
			}
			
			if(offerFilter.createdDateTo != null) {
				
				query = query.filter("created_at <=", offerFilter.createdDateTo);
			}
			
			if(offerFilter.titleKey != null) {
				
				query = query.filter("title_key", offerFilter.titleKey);
			}
			
			//Order by ...
			
			if(offerFilter.orderByCreated == OfferFilter.Order.Asc) {
				
				query = query.order("created_at");
			} else if(offerFilter.orderByCreated == OfferFilter.Order.Desc) {
				
				query = query.order("-created_at");
			}
			
			if(offerFilter.orderByUrgency == OfferFilter.Order.Asc) {
				
				query = query.order("-created_at");
				query = query.order("urgency_in_days");
			} else if(offerFilter.orderByUrgency == OfferFilter.Order.Desc) {
				
				query = query.order("-created_at");
				query = query.order("-urgency_in_days");
			}
			
			if(offerFilter.orderByStart == OfferFilter.Order.Asc) {
				
				query = query.order("-created_at");
				query = query.order("start_date");
			} else if(offerFilter.orderByStart == OfferFilter.Order.Desc) {
				
				query = query.order("-created_at");
				query = query.order("-start_date");
			}
			
			if(offerFilter.orderByFinish == OfferFilter.Order.Asc) {
				
				query = query.order("-created_at");
				query = query.order("finish_date");
			} else if(offerFilter.orderByFinish == OfferFilter.Order.Desc) {
				
				query = query.order("-created_at");
				query = query.order("-finish_date");
			}
			
			//query = query.filter("user_id", userId);
			
			if(offerFilter.projection != null) {
			
				query = query.project(offerFilter.projection);
			}
			
			query = query.limit(_limit);
			
			if (_cursorStr[0] != null) {
				
		        query = query.startAt(Cursor.fromWebSafeString(_cursorStr[0]));
			}
			
			boolean continu = false;
		    QueryResultIterator<Offer> iterator = query.iterator();
		    while (iterator.hasNext()) {
		    	
		    	Offer offer = iterator.next();
		    	_offerList.add(offer);
		    	continu = true;
		    }
		    
		    if (continu) {
		    	
		        Cursor cursor = iterator.getCursor();
		        _cursorStr[0] = cursor.toWebSafeString();
		    } else {
		    	
		    	_cursorStr[0] = null;
		    }
		}
	}
	
	//Получение проекции заголовков для автодополнения
	/*public static void getTitlesAutocomplete(Map<Params, Object> _paramsMap) {
		
		List<Offer> _offerList =
				(List<Offer>) _paramsMap.get(Params.OfferList);
		_offerList.clear();
		ofy().clear();
		Query<Offer> query =
				ofy().load().type(Offer.class);
		
		if(_paramsMap.get(Params.InMemory) != null && (boolean) _paramsMap.get(Params.InMemory)) {
			
			Long userId = (Long) _paramsMap.get(Params.UserId);
			query = query.filter("user_id", userId);
			_offerList.addAll(query.list());
		} else {
			
			Integer _limit = (Integer) _paramsMap.get(Params.Limit);
			String[] _cursorStr = (String[]) _paramsMap.get(Params.CursorStringArray);
			OfferFilter offerFilter = (OfferFilter) _paramsMap.get(Params.Filter);
			
			//.order("-created_at");
			
			if(offerFilter.createdDateFrom != null) {
				
				query = query.filter("created_at >=", offerFilter.createdDateFrom);
			}
			
			if(offerFilter.createdDateTo != null) {
				
				query = query.filter("created_at <=", offerFilter.createdDateTo);
			}
			
			if(offerFilter.orderByCreated == OfferFilter.Order.Asc) {
				
				query = query.order("created_at");
			} else if(offerFilter.orderByCreated == OfferFilter.Order.Desc) {
				
				query = query.order("-created_at");
			}
			
			if(offerFilter.orderByUrgency == OfferFilter.Order.Asc) {
				
				query = query.order("-created_at");
				query = query.order("urgency_in_days");
			} else if(offerFilter.orderByUrgency == OfferFilter.Order.Desc) {
				
				query = query.order("-created_at");
				query = query.order("-urgency_in_days");
			}
			
			if(offerFilter.orderByStart == OfferFilter.Order.Asc) {
				
				query = query.order("-created_at");
				query = query.order("start_date");
			} else if(offerFilter.orderByStart == OfferFilter.Order.Desc) {
				
				query = query.order("-created_at");
				query = query.order("-start_date");
			}
			
			if(offerFilter.orderByFinish == OfferFilter.Order.Asc) {
				
				query = query.order("-created_at");
				query = query.order("finish_date");
			} else if(offerFilter.orderByFinish == OfferFilter.Order.Desc) {
				
				query = query.order("-created_at");
				query = query.order("-finish_date");
			}
			
			//query = query.filter("user_id", userId);
			
			if(offerFilter.projection != null) {
			
				query = query.project(offerFilter.projection);
			}
			
			query = query.limit(_limit);
			
			if (_cursorStr[0] != null) {
				
		        query = query.startAt(Cursor.fromWebSafeString(_cursorStr[0]));
			}
			
			boolean continu = false;
		    QueryResultIterator<Offer> iterator = query.iterator();
		    while (iterator.hasNext()) {
		    	
		    	Offer offer = iterator.next();
		    	_offerList.add(offer);
		    	continu = true;
		    }
		    
		    if (continu) {
		    	
		        Cursor cursor = iterator.getCursor();
		        _cursorStr[0] = cursor.toWebSafeString();
		    } else {
		    	
		    	_cursorStr[0] = null;
		    }
		}
	}*/

	public static void createOffer(Offer _offer) {

		ofy().save().entity(_offer).now();
	}
	
	public static void delete(Offer _offer) {

		ofy().delete().entity(_offer).now();
	}
}
