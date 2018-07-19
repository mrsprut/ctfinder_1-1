package org.tyaa.ctfinder.api.publ;

import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun;
import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun2;
import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun3;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tyaa.ctfinder.common.DateTransform;
import org.tyaa.ctfinder.common.ErrorStrings;
import org.tyaa.ctfinder.common.HttpReqParams;
import org.tyaa.ctfinder.common.HttpRespWords;
import org.tyaa.ctfinder.common.KeyGen;
import org.tyaa.ctfinder.common.Mailer;
import org.tyaa.ctfinder.common.ObjectifyQueryLauncher;
import org.tyaa.ctfinder.common.SessionAttributes;
import org.tyaa.ctfinder.controller.CityDAO;
import org.tyaa.ctfinder.controller.CountryDAO;
import org.tyaa.ctfinder.controller.DescriptionDAO;
import org.tyaa.ctfinder.controller.LanguageDAO;
import org.tyaa.ctfinder.controller.OfferDAO;
import org.tyaa.ctfinder.controller.Offer_typeDAO;
import org.tyaa.ctfinder.controller.StateDAO;
import org.tyaa.ctfinder.controller.Static_descriprionDAO;
import org.tyaa.ctfinder.controller.Static_titleDAO;
import org.tyaa.ctfinder.controller.TitleDAO;
import org.tyaa.ctfinder.controller.UserDAO;
import org.tyaa.ctfinder.entity.City;
import org.tyaa.ctfinder.entity.Country;
import org.tyaa.ctfinder.entity.Description;
import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Offer;
import org.tyaa.ctfinder.entity.Offer_type;
import org.tyaa.ctfinder.entity.State;
import org.tyaa.ctfinder.entity.Static_description;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.Title;
import org.tyaa.ctfinder.entity.User;
import org.tyaa.ctfinder.filter.OfferFilter;
import org.tyaa.ctfinder.model.ContinuData;
import org.tyaa.ctfinder.model.OfferGridItem;
import org.tyaa.ctfinder.model.OfferGridItemDetails;
import org.tyaa.ctfinder.model.OfferTableRow;
import org.tyaa.ctfinder.model.OfferTableRowEdit;
import org.tyaa.ctfinder.model.RespData;
import org.tyaa.ctfinder.projection.OfferProjections;

import com.google.appengine.api.datastore.Blob;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.ObjectifyService;

/**
 * Servlet implementation class TasksServlet
 */
@WebServlet("/offer")
public class OfferServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Integer unbounded = -1;
	
	static {
		
		ObjectifyService.register(Language.class);
		ObjectifyService.register(Title.class);
		ObjectifyService.register(Description.class);
		ObjectifyService.register(Offer.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(State.class);
		ObjectifyService.register(Country.class);
		ObjectifyService.register(City.class);
		ObjectifyService.register(Static_title.class);
		ObjectifyService.register(Static_description.class);
		ObjectifyService.register(Offer_type.class);
	}	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OfferServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    
	    //Преобразователь из объектов Java в строки JSON
	    Gson gson =// new Gson();
	    		new GsonBuilder()
	    		.setDateFormat("yyyy-MM-dd").create();
	    
	    HttpSession session = null;
	    try {
	    	
	    	session = req.getSession(true);
		    
		    //Открываем выходной поток - с сервера к клиенту (браузеру/мобильному приложению)
		    try (PrintWriter out = resp.getWriter()) {
		    	
		    	//Если сессия существует
		    	//и содержит атрибут с UserId,
		    	//то проверяем параметры запроса от клиента
		    	if(SessionAttributes.isSessionAttrSet(session, SessionAttributes.userId)) {
		    		
		    		if (req.getParameterMap().keySet().contains(HttpReqParams.action)) {
	
						String actionString = req.getParameter(HttpReqParams.action);
						
						//Готовим формат для парсинга дат из строк
						/*DateFormat reversedFormat =
							new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);*/
						
						switch(actionString) {
						
							case HttpReqParams.create : {
																
								//Получаем из БД объект английского языка
								Language englishLanguage = new Language();
								objectifyRun2(
										"en"
										, englishLanguage
										, LanguageDAO::getLangByCode
										, out
										, gson);
								
								//
								try {
									//1. Идентификатор типа задания
									Long offerTypeId = Long.parseLong(req.getParameter("offer_type_id"));
									
									//3. 
									//Получаем из параметра запроса содержимое заголовка предложения
									String title =
											req.getParameter("title");
									/*//Создаем и заполняем сущность заголовка предложения
									Title newOfferTitle =
										new Title(
												KeyGen.text2KeyText(title) + "_offer_title"
												, englishLanguage.getId()
												, title);
									objectifyRun(
											newOfferTitle
											, TitleDAO::createTitle
											, out
											, gson
										);
									String offerTitleKey = newOfferTitle.getKey();*/

									//4. 
									//Выполняем то же самое для основного содержания предложения
									String description =
											req.getParameter("content");
									/*Description newOfferDescription =
										new Description(
												KeyGen.text2KeyText(description) + "_offer_description"
												, englishLanguage.getId()
												, description);
									objectifyRun(
											newOfferDescription
											, DescriptionDAO::createDescriprion
											, out
											, gson
										);
									String offerDescriptionKey = newOfferDescription.getKey();*/
									
									//6
									Long countryId = null;
									try {
										List<Country> countryList = new ArrayList<>();
										objectifyRun(
												countryList
												, CountryDAO::getAllCountries
												, out
												, gson
											);
										List<Country> filteredCountryList =
											countryList.stream()
												.filter(
													c -> {
														Static_title st = new Static_title();
														objectifyRun2(
															((Country)c).getTitle_key()
															, st
															//, req.getParameter(HttpReqParams.autocomplete)
															, Static_titleDAO::getStaticTitleByKey
															, out
															, gson
														);
														return st.getContent().equals(req.getParameter("country_name"));
													})
												.collect(Collectors.toList());
										countryId =
											filteredCountryList.get(0).getId();
									} catch(Exception ex) {}
									
									//7
									Long cityId = null;
									try {
										List<City> countryCitiesList = new ArrayList<>();
										objectifyRun2(
												countryId
												, countryCitiesList
												, CityDAO::getCitiesByCountryId
												, out
												, gson
											);
										//
										List<City> filteredCountryCitiesList =
												countryCitiesList.stream()
												.filter(
													c -> {
														Static_title st = new Static_title();
														objectifyRun2(
															((City)c).getTitle_key()
															, st
															, Static_titleDAO::getStaticTitleByKey
															, out
															, gson
														);
														return st.getContent().equals(req.getParameter("city_name"));
													})
												.collect(Collectors.toList());
										//
										cityId =
												filteredCountryCitiesList.get(0).getId();
									} catch(Exception ex) {}
									
									//8
									Integer collaboratorsCount = OfferServlet.unbounded;
									try {
										collaboratorsCount =
												Integer.parseInt(req.getParameter("c-count"));
									}
									catch(Exception ex){}/* finally {
										al1.add(Integer.toString(collaboratorsCount));
									}*/
									
									//9
									Blob image =
											new Blob(req.getParameter("image").getBytes());
									
									//10
									String startDate = null;
									try {
										startDate =
											DateTransform.DirectToReversed(req.getParameter("start-date"));
									}
									catch(Exception ex){}
									
									//11
									String finishDate = null;
									try {
										finishDate =
											DateTransform.DirectToReversed(req.getParameter("finish-date"));
									} catch(Exception ex){}
									
									//Форматтер даты с обратной записью
									DateFormat reversedFormat =
											new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
									
									//16
									String updatedAt = reversedFormat.format(new Date());
									
									//Создание или обновление
									if (req.getParameterMap().keySet().contains(HttpReqParams.id)) {
										//Поиск и обновление записи о предложении в БД
										
										//
										Offer o = new Offer();
										//
										String offerId =
												req.getParameter(HttpReqParams.id);
										
										//Получение объекта из БД
										objectifyRun2(
												offerId
												, o
												, OfferDAO::getOffer
												, out
												, gson
											);
										
										o.setOffer_type_id(offerTypeId);
										o.setCountry_id(countryId);
										o.setCity_id(cityId);
										o.setCollaborators_count(collaboratorsCount);
										
										//Получаем из БД старый объект заголовка
										Title titleEntity = new Title();
										objectifyRun3(
											o.getTitle_key()
											, englishLanguage.getId()
											, titleEntity
											, TitleDAO::getTitleByKeyAndLang
											, out
											, gson
										);
										//Из старого объекта заголовка узнаем старый заголовок предложения
										String oldTitleString = titleEntity.getContent();
										//Если новый заголовок предложения отличается от старого
										if(!oldTitleString.equals(title)) {
											//Записываем в объект заголовка новое содержание
											titleEntity.setContent(title);
											//Обновляем объект заголовка в БД
											objectifyRun(
													titleEntity
													, TitleDAO::createTitle
													, out
													, gson
												);
										}
										
										//Получаем из БД старый объект
										Description descriptionEntity = new Description();
										objectifyRun3(
											o.getDescription_key()
											, englishLanguage.getId()
											, descriptionEntity
											, DescriptionDAO::getDescriptionByKeyAndLang
											, out
											, gson
										);
										String oldDescriptionString = descriptionEntity.getContent();
										if(!oldDescriptionString.equals(description)) {
											//Записываем в объект новое содержание
											descriptionEntity.setContent(description);
											//Обновляем объект в БД
											objectifyRun(
													descriptionEntity
													, DescriptionDAO::createDescriprion
													, out
													, gson
												);
										}
										
										//Если нового значения желаемого старта нет -
										//извлекаем старое
										if(startDate == null) {
											startDate = o.getStart_date();
										}
										//Если нового значения желаемой даты окончания нет -
										//извлекаем старое значение
										if(finishDate == null) {
											finishDate = o.getFinish_date();
										}
										
										//12
										String urgencyInDays = null;
										if(startDate != null && finishDate != null) {
											
											urgencyInDays =
													DateTransform.ReversedToDiff(startDate, finishDate);
										} else if(finishDate != null) {
											
											urgencyInDays =
													DateTransform.ReversedToDiff(o.getCreated_at(), finishDate);
										} else {
											//unbounded
											urgencyInDays = "36500";
										}
										
										o.setStart_date(startDate);
										o.setFinish_date(finishDate);
										o.setUrgency_in_days(urgencyInDays);
										
										if(image.getBytes().length > 0) {
											o.setImage(image);
										}
										
										o.setUpdated_at(updatedAt);
										
										objectifyRun(
												o
												, OfferDAO::createOffer
												, out
												, gson
											);
										
										ArrayList<String> al = new ArrayList<>();
										al.add(HttpRespWords.updated);
										RespData rd = new RespData(al);
										String successJson = gson.toJson(rd);
										out.print(successJson);
									} else {
										//Создание записи о предложении в БД с нуля
										
										//2. Получаем из БД объект сотояния "создано"
										State createdState = new State();
										objectifyRun2(
												"created_state_static_title"
												, createdState
												, StateDAO::getStateByTitleKey
												, out
												, gson
											);
										Long createdStateId = createdState.getId();
										
										//Создаем и заполняем сущность заголовка предложения
										Title newOfferTitle =
											new Title(
													KeyGen.text2KeyText(title) + "_offer_title"
													, englishLanguage.getId()
													, title);
										objectifyRun(
												newOfferTitle
												, TitleDAO::createTitle
												, out
												, gson
											);
										String offerTitleKey = newOfferTitle.getKey();
										
										//
										Description newOfferDescription =
												new Description(
														KeyGen.text2KeyText(description) + "_offer_description"
														, englishLanguage.getId()
														, description);
										objectifyRun(
												newOfferDescription
												, DescriptionDAO::createDescriprion
												, out
												, gson
											);
										String offerDescriptionKey = newOfferDescription.getKey();
										
										//5
										Long userId =
												(Long)session.getAttribute(
														SessionAttributes.userId
													);
										
										//13
										String startedAt = null;
										
										//14
										String completedAt = null;
										
										//15
										String createdAt = reversedFormat.format(new Date());
										
										//12
										String urgencyInDays = null;
										if(startDate != null && finishDate != null) {
											
											urgencyInDays =
													DateTransform.ReversedToDiff(startDate, finishDate);
										} else if(finishDate != null) {
											
											urgencyInDays =
													DateTransform.ReversedToDiff(createdAt, finishDate);
										} else {
											//unbounded
											urgencyInDays = "36500";
										}
										
										Offer offer =
												new Offer(
														//offer type id
														offerTypeId
														//created-state's id from db
														, createdStateId
														//offer title
														, offerTitleKey
														//offer description
														, offerDescriptionKey
														//current user id
														, userId
														//country_id
														, countryId
														//city_id
														, cityId
														//count of collaborators
														, collaboratorsCount
														// image base64 string to blob
														, image
														//desired start date
														, startDate
														//desired finish date
														, finishDate
														//urgency in days
														, urgencyInDays
														//started_at = null
														, startedAt
														//completed_at = null
														, completedAt
														//created_at = now date
														, createdAt
														//updated_at = now date
														, updatedAt
													);
											
											objectifyRun(
													offer
													, OfferDAO::createOffer
													, out
													, gson
												);
											
											ArrayList<String> al = new ArrayList<>();
											al.add(HttpRespWords.created);
											RespData rd = new RespData(al);
											String successJson = gson.toJson(rd);
											out.print(successJson);
									}
								} catch (Exception ex) {
									
									ObjectifyQueryLauncher.printException(ex, out, gson);
								}
								break;
							}
							case HttpReqParams.get : {
								
								try {
									
									//Получаем из БД объект английского языка
									Language englishLanguage = new Language();
									objectifyRun2(
											"en"
											, englishLanguage
											, LanguageDAO::getLangByCode
											, out
											, gson);
									
									//
									Offer o = new Offer();
									//
									String offerId =
											req.getParameter(HttpReqParams.id);
									
									//Задание проекции
									String projectionString  =
											req.getParameter(HttpReqParams.projection);
									
									//Получение объекта из БД
									objectifyRun2(
											offerId
											, o
											, OfferDAO::getOffer
											, out
											, gson
										);
									
									if(projectionString.equals(HttpReqParams.stateProjection)) {
										
										List al = new ArrayList<>();
										al.add(o.getState_id());
										RespData rd = new RespData(al);
										String successJson = gson.toJson(rd);
										out.print(successJson);
										break;
									}
																		
									//Image
									int imageBytesCount = o.getImage().getBytes().length;
									String noimageUriString = "/img/no-image.png";
									String imageBase64 =
											(imageBytesCount > 0)
											? new String(o.getImage().getBytes())
											: noimageUriString;
									
									if(projectionString.equals(HttpReqParams.imageProjection)) {
										
										List al = new ArrayList<>();
										al.add(imageBase64);
										RespData rd = new RespData(al);
										String successJson = gson.toJson(rd);
										out.print(successJson);
									} else {
									
										//Находим объект названия типа предлажения
										//и его реализацию на текущем языке
										String offerTypeDescriptionString = "-";
										if(o.getOffer_type_id() != null) {
											Offer_type offerType = new Offer_type();
											objectifyRun2(
													o.getOffer_type_id()
													, offerType
													, Offer_typeDAO::getOffer_type
													, out
													, gson
												);
											//offerTypeDescriptionString = "+";
											Static_description offerTypeDescription =
													new Static_description();
											objectifyRun3(
												offerType.getDescription_key()
												, englishLanguage.getId()
												, offerTypeDescription
												, Static_descriprionDAO::getStaticDescriptionByKeyAndLang
												, out
												, gson
											);
											if(offerTypeDescription.getContent() != null) {
												offerTypeDescriptionString =
														offerTypeDescription.getContent();
											}
										}
										//Находим заголовок на текущем языке
										String titleString = "-";
										if(o.getTitle_key() != null
												&& !o.getTitle_key().equals("")) {
											Title title = new Title();
											objectifyRun3(
												o.getTitle_key()
												, englishLanguage.getId()
												, title
												, TitleDAO::getTitleByKeyAndLang
												, out
												, gson
											);
											titleString = title.getContent();
										}
										//Находим описание на текущем языке
										String descriptionString = "-";
										if(o.getDescription_key() != null
												&& !o.getDescription_key().equals("")) {
											Description description = new Description();
											objectifyRun3(
												o.getDescription_key()
												, englishLanguage.getId()
												, description
												, DescriptionDAO::getDescriptionByKeyAndLang
												, out
												, gson
											);
											descriptionString = description.getContent();
											/*descriptionString = 
												descriptionString.length() > 25
												? descriptionString.substring(0, 25) + " ..."
												: descriptionString;*/
										}
												
										//Country
										String countryString = "-";
										if(o.getCountry_id() != null) {
											Country offerCountry = new Country();
											objectifyRun2(
													o.getCountry_id()
													, offerCountry
													, CountryDAO::getCountry
													, out
													, gson
												);
											Static_title offerCountrySt = new Static_title();		
											objectifyRun3(
													offerCountry.getTitle_key()
													, englishLanguage.getId()
													, offerCountrySt
													, Static_titleDAO::getStaticTitleByKeyAndLang
													, out
													, gson
												);
											countryString = offerCountrySt.getContent();
										}
										
										//City
										String cityString = "-";
										if(o.getCity_id() != null) {
											City offerCity = new City();
											objectifyRun2(
													o.getCity_id()
													, offerCity
													, CityDAO::getCity
													, out
													, gson
												);
											Static_title offerCitySt = new Static_title();		
											objectifyRun3(
													offerCity.getTitle_key()
													, englishLanguage.getId()
													, offerCitySt
													, Static_titleDAO::getStaticTitleByKeyAndLang
													, out
													, gson
												);
											cityString = offerCitySt.getContent();
										}
										
										switch(projectionString) {
										
											case HttpReqParams.gridItemDetailsProjection:{
														
												
												//Находим объект типа состояния
												//и его реализацию на текущем языке
												String offerStateString = "-";
												if(o.getState_id() != null) {
													State state = new State();
													objectifyRun2(
															o.getState_id()
															, state
															, StateDAO::getState
															, out
															, gson
														);
													/*if(state.getId() != null) {}
													offerStateString =
															state.getId().toString() + " " + state.getTitle_key();*/
													Static_title stateTitle = new Static_title();
													objectifyRun3(
														state.getTitle_key()
														, englishLanguage.getId()
														, stateTitle
														, Static_titleDAO::getStaticTitleByKeyAndLang
														, out
														, gson
													);
													if(stateTitle.getContent() != null) {
														offerStateString = stateTitle.getContent();
													}
												}
												
												//Created at - direct format
												String createdAtString =
														DateTransform.ReversedToDirect(o.getCreated_at());
												
												//Populate the response object
												OfferGridItemDetails offerDetails =
													new OfferGridItemDetails (
														o.getId()
														, offerTypeDescriptionString
														, titleString
														, offerStateString
														, imageBase64
														, descriptionString
														, createdAtString
														
														, countryString
														, cityString);
												
												//
												List al = new ArrayList<>();
												al.add(offerDetails);
												//String nextCursorString = (cursorStr[0] != null) ? cursorStr[0] : "end";
												//al.add(new ContinuData(gridItems, nextCursorString));
												RespData rd = new RespData(al);
												String successJson = gson.toJson(rd);
												out.print(successJson);
												break;
											}
											case HttpReqParams.tableRowEditProjection:{
													
												//
												String collaboratorsCount =
														o.getCollaborators_count().toString();
												
												String desiredStartDateString =
														DateTransform.ReversedToDirect(o.getStart_date());
												
												String desiredFinishDateString =
														DateTransform.ReversedToDirect(o.getFinish_date());
												
												//Находим объект типа состояния
												//и его реализацию на текущем языке
												String offerStateString = "-";
												if(o.getState_id() != null) {
													State state = new State();
													objectifyRun2(
															o.getState_id()
															, state
															, StateDAO::getState
															, out
															, gson
														);
													/*if(state.getId() != null) {}
													offerStateString =
															state.getId().toString() + " " + state.getTitle_key();*/
													Static_title stateTitle = new Static_title();
													objectifyRun3(
														state.getTitle_key()
														, englishLanguage.getId()
														, stateTitle
														, Static_titleDAO::getStaticTitleByKeyAndLang
														, out
														, gson
													);
													if(stateTitle.getContent() != null) {
														offerStateString = stateTitle.getContent();
													}
												}
												
												//Created at - direct format
												String createdAtString =
														DateTransform.ReversedToDirect(o.getCreated_at());
												
												//Populate the response object
												OfferTableRowEdit offerTableRowEdit =
													new OfferTableRowEdit (
														o.getId()
														, offerTypeDescriptionString
														, titleString
														, descriptionString
														
														, collaboratorsCount
														, countryString
														, cityString
														, desiredStartDateString
														, desiredFinishDateString);
												
												//
												List al = new ArrayList<>();
												al.add(offerTableRowEdit);
												//String nextCursorString = (cursorStr[0] != null) ? cursorStr[0] : "end";
												//al.add(new ContinuData(gridItems, nextCursorString));
												RespData rd = new RespData(al);
												String successJson = gson.toJson(rd);
												out.print(successJson);
												break;
											}
										}
									}
								} catch (Exception ex) {
									
									ObjectifyQueryLauncher.printException(ex, out, gson);
								}	
								break;
							}
							case HttpReqParams.getRange : {
								
								try {
									
									//Получаем из БД объект английского языка
									Language englishLanguage = new Language();
									objectifyRun2(
											"en"
											, englishLanguage
											, LanguageDAO::getLangByCode
											, out
											, gson);
									
									//Фильтрация, сортировка и проектция преимущественно
									//средствами Java Stream API
									if(req.getParameterMap().keySet().contains(HttpReqParams.inMemory)) {
										
										List<Offer> offers = new ArrayList<>();
										List<OfferTableRow> offersOut = new ArrayList<>();
										
										Integer limit =
												Integer.parseInt(req.getParameter(HttpReqParams.limit));
										Integer startFrom =
												Integer.parseInt(req.getParameter(HttpReqParams.startFrom));
										
										OfferFilter offerFilter = new OfferFilter();
										
										Long userId =
												(Long)session.getAttribute(
														SessionAttributes.userId
													);
										
										Map<OfferDAO.Params, Object> paramsMap = new HashMap<>();
										paramsMap.put(OfferDAO.Params.OfferList, offers);
										paramsMap.put(OfferDAO.Params.UserId, userId);
										paramsMap.put(OfferDAO.Params.InMemory, true);
										objectifyRun(
												paramsMap
												, OfferDAO::getOffersRange
												, out
												, gson
											);
										
										Stream<Offer> offerStream = offers.stream()
												.sorted(Comparator.comparing(Offer::getCreated_at).reversed()) ;
										
										if(req.getParameterMap().keySet().contains(HttpReqParams.createdDateFrom)) {
											
											offerFilter.createdDateFrom =
													DateTransform.DirectToReversed(
															req.getParameter(HttpReqParams.createdDateFrom)
														);
											offerStream =
												offerStream.filter(
														o ->
															((Offer)o).getCreated_at().compareTo(offerFilter.createdDateFrom)
															 	>= 0
													);
										}
										
										if(req.getParameterMap().keySet().contains(HttpReqParams.createdDateTo)) {
											
											offerFilter.createdDateTo =
													DateTransform.DirectToReversed(
															req.getParameter(HttpReqParams.createdDateTo)
														);
											offerStream =
													offerStream.filter(
															o ->
																((Offer)o).getCreated_at().compareTo(offerFilter.createdDateTo)
																 	<= 0
														);
										}
										
										if(startFrom != null && startFrom != 0) {
											
											offerStream =
													offerStream.skip(startFrom);
										}
										
										if(limit != null && limit != 0) {
											
											offerStream =
													offerStream.limit(limit);
										}
										
										Stream<OfferTableRow> offerTableRowStream =
												offerStream.map(
													
														o -> {
															
															String offerTypeDescriptionString = "-";
															if(o.getOffer_type_id() != null) {
																Offer_type offerType = new Offer_type();
																objectifyRun2(
																		o.getOffer_type_id()
																		, offerType
																		, Offer_typeDAO::getOffer_type
																		, out
																		, gson
																	);
																//offerTypeDescriptionString = "+";
																Static_description offerTypeDescription =
																		new Static_description();
																objectifyRun3(
																	offerType.getDescription_key()
																	, englishLanguage.getId()
																	, offerTypeDescription
																	, Static_descriprionDAO::getStaticDescriptionByKeyAndLang
																	, out
																	, gson
																);
																if(offerTypeDescription.getContent() != null) {
																	offerTypeDescriptionString =
																			offerTypeDescription.getContent();
																}
															}
															
															String titleString = "-";
															if(o.getTitle_key() != null
																	&& !o.getTitle_key().equals("")) {
																Title title = new Title();
																objectifyRun3(
																	o.getTitle_key()
																	, englishLanguage.getId()
																	, title
																	, TitleDAO::getTitleByKeyAndLang
																	, out
																	, gson
																);
																titleString = title.getContent();
															}
															
															String offerStateString = "-";
															if(o.getState_id() != null) {
																State state = new State();
																objectifyRun2(
																		o.getState_id()
																		, state
																		, StateDAO::getState
																		, out
																		, gson
																	);
																/*if(state.getId() != null) {}
																offerStateString =
																		state.getId().toString() + " " + state.getTitle_key();*/
																Static_title stateTitle = new Static_title();
																objectifyRun3(
																	state.getTitle_key()
																	, englishLanguage.getId()
																	, stateTitle
																	, Static_titleDAO::getStaticTitleByKeyAndLang
																	, out
																	, gson
																);
																if(stateTitle.getContent() != null) {
																	offerStateString = stateTitle.getContent();
																}
																
															}
															
															try {
																return new OfferTableRow(
																		o.getId()
																		, offerTypeDescriptionString
																		, titleString
																		, offerStateString
																		, DateTransform.ReversedToDirect(o.getCreated_at()));
															} catch (ParseException ex) {
																
																ObjectifyQueryLauncher.printException(ex, out, gson);
																return new OfferTableRow();
															}
														}
													);
										
										offersOut =
												offerTableRowStream.collect(Collectors.toList());
										
										//List al = new ArrayList<>();
										//al.add(offersOut);
										RespData rd = new RespData(offersOut);
										String successJson = gson.toJson(rd);
										out.print(successJson);
									} else {
										//Фильтрация, сортировка и проектция преимущественно
										//средствами СУБД
										List<Offer> offers = new ArrayList<>();
										//List<Offer> offersOut = new ArrayList<>();
										Integer limit =
												Integer.parseInt(req.getParameter(HttpReqParams.limit));
										String[] cursorStr =
												(req.getParameterMap().keySet().contains(HttpReqParams.cursor))
												? new String[] {req.getParameter(HttpReqParams.cursor)}
												: new String[] {null};
										
										OfferFilter offerFilter = new OfferFilter();
										//OfferFilter.reset(offerFilter, OfferFilter.class);
												
										if(req.getParameterMap().keySet().contains(HttpReqParams.createdDateFrom)) {
											
											offerFilter.createdDateFrom =
													DateTransform.DirectToReversed(req.getParameter(HttpReqParams.createdDateFrom));
										}
										
										if(req.getParameterMap().keySet().contains(HttpReqParams.createdDateTo)) {
											
											offerFilter.createdDateTo =
													DateTransform.DirectToReversed(req.getParameter(HttpReqParams.createdDateTo));
										}
										
										//Order by ...
										if(req.getParameterMap().keySet().contains(HttpReqParams.orderBy)) {
											
											String orderBy = req.getParameter(HttpReqParams.orderBy);
											switch(orderBy) {
											
												case HttpReqParams.sortCreatedAsc : {
													
													offerFilter.orderByCreated = OfferFilter.Order.Asc;
													break;
												}
												case HttpReqParams.sortCreatedDesc : {
													
													offerFilter.orderByCreated = OfferFilter.Order.Desc;
													break;
												}
												case HttpReqParams.sortUrgencyAsc : {
													
													offerFilter.orderByUrgency = OfferFilter.Order.Asc;
													break;
												}
												case HttpReqParams.sortUrgencyDesc : {
													
													offerFilter.orderByUrgency = OfferFilter.Order.Desc;
													break;
												}
												case HttpReqParams.sortStartAsc : {
													
													offerFilter.orderByStart = OfferFilter.Order.Asc;
													break;
												}
												case HttpReqParams.sortStartDesc : {
													
													offerFilter.orderByStart = OfferFilter.Order.Desc;
													break;
												}
												case HttpReqParams.sortFinishAsc : {
													
													offerFilter.orderByFinish = OfferFilter.Order.Asc;
													break;
												}
												case HttpReqParams.sortFinishDesc : {
													
													offerFilter.orderByFinish = OfferFilter.Order.Desc;
													break;
												}
											}
											
											/*offerFilter.createdDateTo =
													DateTransform.DirectToReversed(req.getParameter(HttpReqParams.createdDateTo));*/
										}
										
										Map<OfferDAO.Params, Object> paramsMap = new HashMap<>();
										paramsMap.put(OfferDAO.Params.OfferList, offers);
										paramsMap.put(OfferDAO.Params.Limit, limit);
										paramsMap.put(OfferDAO.Params.CursorStringArray, cursorStr);
										paramsMap.put(OfferDAO.Params.Filter, offerFilter);
										objectifyRun(
												paramsMap
												, OfferDAO::getOffersRange
												, out
												, gson
											);
										
										//Задание проекции, чтобы получить из БД значения только для части
										//полей объекта модели
										/*if(req.getParameterMap().keySet().contains(HttpReqParams.projection)) {
											
											String projectionString  = req.getParameter(HttpReqParams.projection);
											switch(projectionString) {
												case HttpReqParams.tableRowProjection:{
													//offerFilter.projection = OfferProjections.tableRow;
													
												}
											}
										}*/
										
										List<OfferGridItem> gridItems =
												offers.stream()
												.map((o) -> {
													
													//Находим объект названия типа предлажения
													//и его реализацию на текущем языке
													String offerTypeDescriptionString = "-";
													if(o.getOffer_type_id() != null) {
														Offer_type offerType = new Offer_type();
														objectifyRun2(
																o.getOffer_type_id()
																, offerType
																, Offer_typeDAO::getOffer_type
																, out
																, gson
															);
														//offerTypeDescriptionString = "+";
														Static_description offerTypeDescription =
																new Static_description();
														objectifyRun3(
															offerType.getDescription_key()
															, englishLanguage.getId()
															, offerTypeDescription
															, Static_descriprionDAO::getStaticDescriptionByKeyAndLang
															, out
															, gson
														);
														if(offerTypeDescription.getContent() != null) {
															offerTypeDescriptionString =
																	offerTypeDescription.getContent();
														}
													}
													//Находим заголовок на текущем языке
													String titleString = "-";
													if(o.getTitle_key() != null
															&& !o.getTitle_key().equals("")) {
														Title title = new Title();
														objectifyRun3(
															o.getTitle_key()
															, englishLanguage.getId()
															, title
															, TitleDAO::getTitleByKeyAndLang
															, out
															, gson
														);
														titleString = title.getContent();
													}
													//Находим описание на текущем языке
													String descriptionString = "-";
													if(o.getDescription_key() != null
															&& !o.getDescription_key().equals("")) {
														Description description = new Description();
														objectifyRun3(
															o.getDescription_key()
															, englishLanguage.getId()
															, description
															, DescriptionDAO::getDescriptionByKeyAndLang
															, out
															, gson
														);
														descriptionString = description.getContent();
													}
													//Находим объект типа состояния
													//и его реализацию на текущем языке
													String offerStateString = "-";
													if(o.getState_id() != null) {
														State state = new State();
														objectifyRun2(
																o.getState_id()
																, state
																, StateDAO::getState
																, out
																, gson
															);
														/*if(state.getId() != null) {}
														offerStateString =
																state.getId().toString() + " " + state.getTitle_key();*/
														Static_title stateTitle = new Static_title();
														objectifyRun3(
															state.getTitle_key()
															, englishLanguage.getId()
															, stateTitle
															, Static_titleDAO::getStaticTitleByKeyAndLang
															, out
															, gson
														);
														if(stateTitle.getContent() != null) {
															offerStateString = stateTitle.getContent();
														}
														
													}
													
													int imageBytesCount = o.getImage().getBytes().length;
													String noimageUriString = "/img/no-image.png";
													String imageBase64 =
															(imageBytesCount > 0)
															? new String(o.getImage().getBytes())
															: noimageUriString;
													
													try {
														return new OfferGridItem(
																o.getId()
																, offerTypeDescriptionString
																, titleString
																, offerStateString
																, imageBase64
																, descriptionString.length() > 25
																		? descriptionString.substring(0, 25) + " ..."
																		: descriptionString
																, DateTransform.ReversedToDirect(o.getCreated_at())
															);
													} catch (ParseException ex) {
														
														ObjectifyQueryLauncher.printException(ex, out, gson);
														return new OfferGridItem();
													}
												})
												.collect(Collectors.toList());
										
										//
										List al = new ArrayList<>();
										//al.add(offers);
										String nextCursorString = (cursorStr[0] != null) ? cursorStr[0] : "end";
										al.add(new ContinuData(gridItems, nextCursorString));
										RespData rd = new RespData(al);
										String successJson = gson.toJson(rd);
										out.print(successJson);
									}
								} catch (Exception ex) {
									
									ObjectifyQueryLauncher.printException(ex, out, gson);
								}	
								break;
							}
							case HttpReqParams.join : {
								
								try {
									//Получаем из БД объект английского языка
									Language englishLanguage = new Language();
									objectifyRun2(
											"en"
											, englishLanguage
											, LanguageDAO::getLangByCode
											, out
											, gson);
									
									//
									Offer o = new Offer();
									String offerId =
											req.getParameter(HttpReqParams.id);
									objectifyRun2(
											offerId
											, o
											, OfferDAO::getOffer
											, out
											, gson
										);
									//Находим заголовок на текущем языке
									String titleString = "-";
									if(o.getTitle_key() != null
											&& !o.getTitle_key().equals("")) {
										Title title = new Title();
										objectifyRun3(
											o.getTitle_key()
											, englishLanguage.getId()
											, title
											, TitleDAO::getTitleByKeyAndLang
											, out
											, gson
										);
										titleString = title.getContent();
									}
									
									User authorUser = new User();
									objectifyRun2(
											o.getUser_id()
											, authorUser
											, UserDAO::getUser
											, out
											, gson
										);
									
									Long candidateUserId =
										(Long)session.getAttribute(
												SessionAttributes.userId
											);
									User candidateUser = new User();
									objectifyRun2(
											candidateUserId
											, candidateUser
											, UserDAO::getUser
											, out
											, gson
										);
									
									//Помощник по отправке сообщений на электронную почту
									Mailer mailer = new Mailer();
									String messageString =
											"User "
											+ candidateUser.getName()
											+ " wants to join your project "
											+ titleString
											+ "(ID: "
											+ o.getId()
											+ ")"
											+ ". Get in touch with him by email: "
											+ candidateUser.getEmail();
									String subjectString = "Request for join";
									String fromAddressString = "tyaamariupol@gmail.com";
									String fromNameString = "CTFinder";
									String toAddressString = authorUser.getEmail();
									String toNameString = authorUser.getName();
									
									// Отправляем сообщение
									mailer.sendPlainMsg(
											messageString
											, subjectString
											, fromAddressString
											, fromNameString
											, toAddressString
											, toNameString);
									
									List al = new ArrayList<>();
									al.add(HttpRespWords.sent);
									RespData rd = new RespData(al);
									String successJson = gson.toJson(rd);
									out.print(successJson);
								} catch (Exception ex) {
									
									ObjectifyQueryLauncher.printException(ex, out, gson);
								}	
								break;
							}
							case HttpReqParams.update : {
								//Действие только для обновления состояния предложения.
								//Другие параметры обновляются веткой создания предложения.
								try {
									//Получаем из БД объект английского языка
									Language englishLanguage = new Language();
									objectifyRun2(
											"en"
											, englishLanguage
											, LanguageDAO::getLangByCode
											, out
											, gson);
									
									//
									Offer o = new Offer();
									//
									String offerId =
											req.getParameter(HttpReqParams.id);
									
									//
									String newState  =
											req.getParameter(HttpReqParams.newState);
									
									//Получение объекта из БД
									objectifyRun2(
											offerId
											, o
											, OfferDAO::getOffer
											, out
											, gson
										);
									
									o.setState_id(Long.parseLong(newState));
									DateFormat reversedFormat =
											new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
									String updatedAt = reversedFormat.format(new Date());
									o.setUpdated_at(updatedAt);
									
									objectifyRun(
											o
											, OfferDAO::createOffer
											, out
											, gson
										);
									
									ArrayList<String> al = new ArrayList<>();
									al.add(HttpRespWords.updated);
									RespData rd = new RespData(al);
									String successJson = gson.toJson(rd);
									out.print(successJson);
								} catch (Exception ex) {
									
									ObjectifyQueryLauncher.printException(ex, out, gson);
								}
								break;
							}
							case HttpReqParams.delete : {
								
								ArrayList<String> al = new ArrayList<>();
								al.add(HttpRespWords.deleted);
								RespData rd = new RespData(al);
								String successJson = gson.toJson(rd);
								out.print(successJson);
								break;
							}
						}
		    		}
		    	} //иначе передаем клиенту сообщение об ошибке "нет сессии"
		    	else {
		    		
		    		RespData rd = new RespData(ErrorStrings.noSession);
					String errorJson = gson.toJson(rd);
					out.print(errorJson);
		    	}
		    } catch (Exception ex) {
				// TODO Auto-generated catch block
				try (PrintWriter out = resp.getWriter()) {
					
					ObjectifyQueryLauncher.printException(ex, out, gson);
				}
				//ex.printStackTrace();
			}
	    } catch (Exception ex) {
	    	
	    	try (PrintWriter out = resp.getWriter()) {
				
	    		ObjectifyQueryLauncher.printException(ex, out, gson);
			}
		}
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
