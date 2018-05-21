package org.tyaa.ctfinder.controller;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Offer;
import org.tyaa.ctfinder.entity.Offer_type;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.User_type;

public class OfferDAO {
	
	public static void getOffer(String _id, Offer _offer) {
		
		Offer offer =
				ofy().load().type(Offer.class).id(Long.valueOf(_id)).now();
		
		if(offer != null) {
			
			_offer.setId(offer.getId());
			_offer.setOffer_type_id(offer.getOffer_type_id());
			_offer.setState_id(offer.getState_id());
			_offer.setTitle_key(offer.getTitle_key());
			_offer.setDescription_key(offer.getDescription_key());
			_offer.setUser_id(offer.getUser_id());
			_offer.setCountry_id(offer.getCountry_id());
			_offer.setCity_id(offer.getCity_id());
			_offer.setCollaborators_count(offer.getCollaborators_count());
			_offer.setStart_date(offer.getStart_date());
			_offer.setFinish_date(offer.getFinish_date());
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

	public static void createOffer(Offer _offer) {

		ofy().save().entity(_offer).now();
	}
}
