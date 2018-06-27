package org.tyaa.ctfinder.api.publ;

import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun;
import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun2;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tyaa.ctfinder.common.ErrorStrings;
import org.tyaa.ctfinder.common.HttpReqParams;
import org.tyaa.ctfinder.common.HttpRespWords;
import org.tyaa.ctfinder.common.KeyGen;
import org.tyaa.ctfinder.common.SessionAttributes;
import org.tyaa.ctfinder.controller.CityDAO;
import org.tyaa.ctfinder.controller.CountryDAO;
import org.tyaa.ctfinder.controller.LanguageDAO;
import org.tyaa.ctfinder.controller.OfferDAO;
import org.tyaa.ctfinder.controller.StateDAO;
import org.tyaa.ctfinder.controller.Static_titleDAO;
import org.tyaa.ctfinder.entity.City;
import org.tyaa.ctfinder.entity.Country;
import org.tyaa.ctfinder.entity.Description;
import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Offer;
import org.tyaa.ctfinder.entity.State;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.Title;
import org.tyaa.ctfinder.entity.User;
import org.tyaa.ctfinder.model.RespData;

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
						
						
						
						switch(actionString) {
						
							case HttpReqParams.create : {
								
								
								
								//Готовим формат для парсинга дат из строк
								DateFormat format =
									new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
								
								//Получаем из БД объект английского языка
								Language englishLanguage = new Language();
								objectifyRun2(
										"en"
										, englishLanguage
										, LanguageDAO::getLangByCode
										, out
										, gson);
								//Получаем из параметра запроса содержимое заголовка предложения
								String title =
										req.getParameter("title");
								//Создаем и заполняем сущность заголовка предложения
								Title newOfferTitle =
									new Title(
											KeyGen.text2KeyText(title) + "_offer_title"
											, englishLanguage.getId()
											, title);
								//Выполняем то же самое для основного содержания предложения
								String description =
										req.getParameter("content");
								Description newOfferDescription =
									new Description(
											KeyGen.text2KeyText(description) + "_offer_description"
											, englishLanguage.getId()
											, description);
								
								//Получаем из БД объект сотояния "создано"
								State createdState = new State();
								objectifyRun2(
										"created_state_static_title"
										, createdState
										, StateDAO::getStateByTitleKey
										, out
										, gson
									);
								
								//
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
								
								Long countryId =
									filteredCountryList.get(0).getId();
								
								//
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
								Long cityId =
										filteredCountryCitiesList.get(0).getId();
								
								
								//Тестовый ответ клиенту: список значений принятых параметров
								try {
									ArrayList<String> al1 = new ArrayList<>();
									
									al1.add(createdState.getTitle_key().toString());
									al1.add(createdState.getId().toString());
									al1.add(newOfferTitle.getKey());
									al1.add(newOfferDescription.getKey());
									al1.add(((Long)session.getAttribute(SessionAttributes.userId)).toString());
									al1.add(countryId.toString());
									al1.add(cityId.toString());
									al1.add(String.valueOf(
											
											(req.getParameter("c-count") != "")
											? Integer.getInteger(req.getParameter("collaborators_count"))
											: OfferServlet.unbounded
										));
									al1.add((new Blob(req.getParameter("image").getBytes())).toString());
									al1.add(((req.getParameter("start-date") != "")
											? format.parse(req.getParameter("start-date"))
											: null).toString());
									al1.add(((req.getParameter("finish-date") != "")
											? format.parse(req.getParameter("finish-date"))
											: null).toString());
									
									RespData rd1 = new RespData(al1);
									String successJson1 = gson.toJson(rd1);
									out.print(successJson1);
								} catch (Exception ex) {
									// TODO Auto-generated catch block
									//try (PrintWriter out = resp.getWriter()) {
										
										String errorTrace = "";
										for(StackTraceElement el: ex.getStackTrace()) {
											errorTrace += el.toString();
										}
										RespData rd = new RespData(errorTrace);
										
										//RespData rd = new RespData(ex.getMessage());
										String errorJson = gson.toJson(rd);
										out.print(errorJson);
									//}
									ex.printStackTrace();
								}
								
								/*Offer offer =
									new Offer(
											//offer type id
											Long.getLong(req.getParameter("offer_type_id"))
											//created state id from db
											, createdState.getId()
											//offer title
											, newOfferTitle.getKey()
											//offer description
											, newOfferDescription.getKey()
											//current user id
											, (Long)session.getAttribute(SessionAttributes.userId)
											//country_id
											, Long.getLong(req.getParameter("country_id"))
											//city_id
											, Long.getLong(req.getParameter("city_id"))
											//count of collaborators
											, (req.getParameter("collaborators_count") != "")
												? Integer.getInteger(req.getParameter("collaborators_count"))
												: OfferServlet.unbounded
											// image base64 string to blob
											, new Blob(req.getParameter("image").getBytes())
											//desired start date
											, (req.getParameter("start_date") != "")
												? format.parse(req.getParameter("start_date"))
												: null
											//desired finish date
											, (req.getParameter("finish_date") != "")
												? format.parse(req.getParameter("finish_date"))
												: null
											//started_at = null
											, null
											//completed_at = null
											, null
											//created_at = now date
											, new Date()
											//updated_at = now date
											, new Date()
										);*/
								
								/*objectifyRun(
										offer
										, OfferDAO::createOffer
										, out
										, gson
									);*/
								
								/*ArrayList<String> al = new ArrayList<>();
								al.add(HttpRespWords.created);
								RespData rd = new RespData(al);
								String successJson = gson.toJson(rd);
								out.print(successJson);*/
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
					
					/*String errorTrace = "";
					for(StackTraceElement el: ex.getStackTrace()) {
						errorTrace += el.toString();
					}
					RespData rd = new RespData(errorTrace);*/
					
					RespData rd = new RespData(ex.getMessage());
					String errorJson = gson.toJson(rd);
					out.print(errorJson);
				}
				ex.printStackTrace();
			}
	    } catch (Exception ex) {
			// TODO Auto-generated catch block
			try (PrintWriter out = resp.getWriter()) {
				
				/*String errorTrace = "";
				for(StackTraceElement el: ex.getStackTrace()) {
					errorTrace += el.toString();
				}
				RespData rd = new RespData(errorTrace);*/
				
				RespData rd = new RespData(ex.getMessage());
				String errorJson = gson.toJson(rd);
				out.print(errorJson);
			}
			ex.printStackTrace();
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
