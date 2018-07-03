package org.tyaa.ctfinder.api.publ;

import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun;
import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun2;
import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun3;

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
import org.tyaa.ctfinder.controller.DescriptionDAO;
import org.tyaa.ctfinder.controller.LanguageDAO;
import org.tyaa.ctfinder.controller.OfferDAO;
import org.tyaa.ctfinder.controller.StateDAO;
import org.tyaa.ctfinder.controller.Static_titleDAO;
import org.tyaa.ctfinder.controller.TitleDAO;
import org.tyaa.ctfinder.entity.City;
import org.tyaa.ctfinder.entity.Country;
import org.tyaa.ctfinder.entity.Description;
import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Offer;
import org.tyaa.ctfinder.entity.State;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.Title;
import org.tyaa.ctfinder.entity.User;
import org.tyaa.ctfinder.model.ContinuData;
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
								
								//
								try {
									//1. Идентификатор типа задания
									Long offerTypeId = Long.getLong(req.getParameter("offer_type_id"));
									
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
									
									//3. 
									//Получаем из параметра запроса содержимое заголовка предложения
									String title =
											req.getParameter("title");
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
									
									//4. 
									//Выполняем то же самое для основного содержания предложения
									String description =
											req.getParameter("content");
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
									Date startDate = null;
									try {
										startDate = format.parse(req.getParameter("start-date"));
									}
									catch(Exception ex){}
									
									//11
									Date finishDate = null;
									try {
										finishDate = format.parse(req.getParameter("finish-date"));
									} catch(Exception ex){}
									
									//12
									Date startedAt = null;
									
									//13
									Date completedAt = null;
									
									//14
									Date createdAt = new Date();
									
									//15
									Date updatedAt = new Date();
									
									
									
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
								} catch (Exception ex) {
									//
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
									//ex.printStackTrace();
								}
									
								break;
								
								//Тестовый ответ клиенту: список значений принятых параметров
								/*try {
									ArrayList<String> al1 = new ArrayList<>();
									
									al1.add(createdState.getTitle_key().toString());//0
									al1.add(createdState.getId().toString());//1
									al1.add(newOfferTitle.getKey());//2
									al1.add(newOfferDescription.getKey());//3
									al1.add(((Long)session.getAttribute(
											SessionAttributes.userId)).toString()
										);//4
									al1.add(countryId.toString());//5
									al1.add(cityId.toString());//6
									al1.add((
											
											(req.getParameter("c-count") != "")
											? Integer.getInteger(req.getParameter("c-count"))
											: OfferServlet.unbounded
										).toString());//7
									//al1.add(req.getParameter("c-count"));//7
									
									
									
									al1.add(
											(new Blob(req.getParameter("image").getBytes()))
												.toString()
										);//8
									
									Date startDate = null;
									try {
										startDate = format.parse(req.getParameter("start-date"));
									}
									catch(Exception ex){
										
									} finally {
										String result = (startDate != null)?startDate.toString():"null";
										al1.add(result);
									}
									
									Date finishDate = null;
									try {
										finishDate = format.parse(req.getParameter("finish-date"));
									} catch(Exception ex){
										
									} finally {
										String result = (finishDate != null)?finishDate.toString():"null";
										al1.add(result);
									}
									
									al1.add(((req.getParameter("start-date") != null)
											? format.parse(req.getParameter("start-date"))
											: null).toString());//9
									al1.add(((req.getParameter("finish-date") != null)
											? format.parse(req.getParameter("finish-date"))
											: null).toString());//10
									
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
								}*/
							}
							case HttpReqParams.get : {
								
								try {
									List<Offer> offers = new ArrayList<>();
									Integer limit =
											Integer.parseInt(req.getParameter(HttpReqParams.limit));
									String[] cursorStr =
											(req.getParameterMap().keySet().contains(HttpReqParams.cursor))
											? new String[] {req.getParameter(HttpReqParams.cursor)}
											: new String[] {null};
									
									objectifyRun3(
											offers
											, limit
											, cursorStr
											, OfferDAO::getOffersRange
											, out
											, gson
										);
									
									List al = new ArrayList<>();
									//al.add(offers);
									String nextCursorString = (cursorStr[0] != null) ? cursorStr[0] : "end";
									al.add(new ContinuData(offers, nextCursorString));
									RespData rd = new RespData(al);
									String successJson = gson.toJson(rd);
									out.print(successJson);
								} catch (Exception ex) {
									
									/*String errorTrace = "";
									for(StackTraceElement el: ex.getStackTrace()) {
										errorTrace += el.toString();
									}
									RespData rd = new RespData(errorTrace);*/
									
									RespData rd = new RespData(ex.getMessage());
									String errorJson = gson.toJson(rd);
									out.print(errorJson);
									ex.printStackTrace();
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
			//ex.printStackTrace();
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
