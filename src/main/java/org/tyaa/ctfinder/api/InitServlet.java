package org.tyaa.ctfinder.api;

import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun;
import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tyaa.ctfinder.controller.CityDAO;
import org.tyaa.ctfinder.controller.CountryDAO;
import org.tyaa.ctfinder.controller.LanguageDAO;
import org.tyaa.ctfinder.controller.Offer_typeDAO;
import org.tyaa.ctfinder.controller.StateDAO;
import org.tyaa.ctfinder.controller.Static_descriprionDAO;
import org.tyaa.ctfinder.controller.Static_titleDAO;
import org.tyaa.ctfinder.entity.City;
import org.tyaa.ctfinder.entity.Country;
import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Offer_type;
import org.tyaa.ctfinder.entity.State;
import org.tyaa.ctfinder.entity.Static_description;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.Title;
import org.tyaa.ctfinder.entity.User_type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;

/**
 * Servlet implementation class InitServlet
 */
@WebServlet("/init")
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	static {
		
		//Entities registration
		ObjectifyService.register(Language.class);
		ObjectifyService.register(Static_title.class);
		ObjectifyService.register(User_type.class);
		ObjectifyService.register(Static_description.class);
		ObjectifyService.register(Offer_type.class);
		ObjectifyService.register(Country.class);
		ObjectifyService.register(City.class);
		ObjectifyService.register(Title.class);
		ObjectifyService.register(State.class);
	}
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");

	    //Преобразователь из объектов Java в строки JSON
	    Gson gson =// new Gson();
	    		new GsonBuilder()
	    		.setDateFormat("yyyy-MM-dd").create();
	    
	    //Открываем выходной поток - с сервера к клиенту
		try (PrintWriter out = resp.getWriter()) {
			
			/* Migration 1 */
			
			//Создаем в БД запись для английского языка
			
			/*Language englishLanguage = new Language();
			englishLanguage.setCode("en");
			
			ObjectifyService.run(new VoidWork() {
				public void vrun() {
					try {
						LanguageDAO.createLang(englishLanguage);
					} catch (Exception ex) {

						String errorJson = gson.toJson(ex.getMessage());
						out.print(errorJson);
					}
				}
			});
			
			if(englishLanguage.getId() != null) {
				
				//Создаем в БД запись для заголовка для английского языка
				
				Static_title englishTitle = new Static_title();
				englishTitle.setLang_id(englishLanguage.getId());
				englishTitle.setKey("english_lang_title");
				englishTitle.setContent("english");
				
				ObjectifyService.run(new VoidWork() {
					public void vrun() {
						try {
							Static_titleDAO.createStatic_title(englishTitle);
						} catch (Exception ex) {

							String errorJson = gson.toJson(ex.getMessage());
							out.print(errorJson);
						}
					}
				});
				
				if(englishTitle.getId() != null) {
					
					//update englishLanguage
					englishLanguage.setTitle_key(englishTitle.getKey());
					ObjectifyService.run(new VoidWork() {
						public void vrun() {
							try {
								LanguageDAO.updateLang(englishLanguage);
							} catch (Exception ex) {

								String errorJson = gson.toJson(ex.getMessage());
								out.print(errorJson);
							}
						}
					});
					
					//create title 'user' for the future usertype
					
					Static_title userTitle = new Static_title();
					userTitle.setLang_id(englishLanguage.getId());
					userTitle.setKey("user_user_type_title");
					userTitle.setContent("user");
					
					ObjectifyService.run(new VoidWork() {
						public void vrun() {
							try {
								Static_titleDAO.createStatic_title(userTitle);
							} catch (Exception ex) {

								String errorJson = gson.toJson(ex.getMessage());
								out.print(errorJson);
							}
						}
					});
					
					//create usertype 'user'
					User_type user_user_type = new User_type();
					user_user_type.setTitle_key(userTitle.getKey());
					ObjectifyService.run(new VoidWork() {
						public void vrun() {
							try {
								User_typeDAO.createUser_type(user_user_type);
							} catch (Exception ex) {

								String errorJson = gson.toJson(ex.getMessage());
								out.print(errorJson);
							}
						}
					});
				}
			}*/
			
			/* Migration 2 */
			
			//Создаем в БД записи для описаний типов предложений
			
			//Получаем из БД объект английского языка
			Language englishLanguage = new Language();
			ObjectifyService.run(new VoidWork() {
				public void vrun() {
					try {
						LanguageDAO.getLangByCode("en", englishLanguage);
					} catch (Exception ex) {

						String errorJson = gson.toJson(ex.getMessage());
						out.print(errorJson);
					}
				}
			});
			
			/*Static_description volunteerAssistanceEnSd = new Static_description();
			//TODO key generator
			volunteerAssistanceEnSd.setKey("volunteer_assistance_offer_type");
			volunteerAssistanceEnSd.setLang_id(englishLanguage.getId());
			volunteerAssistanceEnSd.setContent("volunteer assistance");
			
			ObjectifyService.run(new VoidWork() {
				public void vrun() {
					try {
						Static_descriprionDAO.createStatic_descriprion(volunteerAssistanceEnSd);
					} catch (Exception ex) {

						String errorJson = gson.toJson(ex.getMessage());
						out.print(errorJson);
					}
				}
			});
			
			Offer_type volunteerAssistanceOt = new Offer_type();
			volunteerAssistanceOt.setDescription_key(volunteerAssistanceEnSd.getKey());
			
			ObjectifyService.run(new VoidWork() {
				public void vrun() {
					try {
						Offer_typeDAO.createOffer_type(volunteerAssistanceOt);
					} catch (Exception ex) {

						String errorJson = gson.toJson(ex.getMessage());
						out.print(errorJson);
					}
				}
			});*/
			
			/*Static_description  graphicDesignEnSd = new Static_description();
			graphicDesignEnSd.setKey("graphic_design_offer_type");
			graphicDesignEnSd.setLang_id(englishLanguage.getId());
			graphicDesignEnSd.setContent("graphic design");
			
			ObjectifyService.run(new VoidWork() {
				public void vrun() {
					try {
						Static_descriprionDAO.createStatic_descriprion(graphicDesignEnSd);
					} catch (Exception ex) {

						String errorJson = gson.toJson(ex.getMessage());
						out.print(errorJson);
					}
				}
			});
			
			Offer_type graphicDesignOt = new Offer_type();
			graphicDesignOt.setDescription_key(graphicDesignEnSd.getKey());
			
			ObjectifyService.run(new VoidWork() {
				public void vrun() {
					try {
						Offer_typeDAO.createOffer_type(graphicDesignOt);
					} catch (Exception ex) {

						String errorJson = gson.toJson(ex.getMessage());
						out.print(errorJson);
					}
				}
			});*/
			
			/* Migration 3 */
			
			//Ukraine country en
			//TODO key generator
			/*Static_title ukraineCountrySt =
					new Static_title(
							"ukraine_country"
							, englishLanguage.getId()
							, "Ukraine");		
			objectifyRun(
					ukraineCountrySt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			Country ukraineCountry =
					new Country(ukraineCountrySt.getKey());
			
			objectifyRun(
					ukraineCountry
					, CountryDAO::createCountry
					, out
					, gson
				);
			
			//Russia country en
			Static_title russiaCountrySt =
					new Static_title(
							"russia_country"
							, englishLanguage.getId()
							, "Russia");		
			objectifyRun(
					russiaCountrySt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			Country russiaCountry =
					new Country(russiaCountrySt.getKey());
			
			objectifyRun(
					russiaCountry
					, CountryDAO::createCountry
					, out
					, gson
				);*/
			
			
			/* Migration 4 */
			
			// Create Kiev n Mariupol cities
			
			/*Country ukraineCountry =
					new Country();
			
			objectifyRun2(
					"ukraine_country"
					, ukraineCountry
					, CountryDAO::getCountryByTitleKey
					, out
					, gson
				);
			
			Static_title kievCitySt =
					new Static_title(
							"kiev_city"
							, englishLanguage.getId()
							, "Kiev");		
			objectifyRun(
					kievCitySt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			City kievCity =
					new City(ukraineCountry.getId(), kievCitySt.getKey());
			objectifyRun(
					kievCity
					, CityDAO::createCity
					, out
					, gson
				);
			
			Static_title mariupolCitySt =
					new Static_title(
							"mariupol_city"
							, englishLanguage.getId()
							, "Mariupol");		
			objectifyRun(
					mariupolCitySt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			City mariupolCity =
					new City(ukraineCountry.getId(), mariupolCitySt.getKey());
			objectifyRun(
					mariupolCity
					, CityDAO::createCity
					, out
					, gson
				);
			
			// Create Moscow n Petersburg cities
			
			Country russiaCountry =
					new Country();
			
			objectifyRun2(
					"russia_country"
					, russiaCountry
					, CountryDAO::getCountryByTitleKey
					, out
					, gson
				);
			
			Static_title moscowCitySt =
					new Static_title(
							"moscow_city"
							, englishLanguage.getId()
							, "Moscow");		
			objectifyRun(
					moscowCitySt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			City moscowCity =
					new City(russiaCountry.getId(), moscowCitySt.getKey());
			objectifyRun(
					moscowCity
					, CityDAO::createCity
					, out
					, gson
				);
			
			Static_title petersburgCitySt =
					new Static_title(
							"petersburg_city"
							, englishLanguage.getId()
							, "Petersburg");		
			objectifyRun(
					petersburgCitySt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			City petersburgCity =
					new City(russiaCountry.getId(), petersburgCitySt.getKey());
			objectifyRun(
					petersburgCity
					, CityDAO::createCity
					, out
					, gson
				);*/
			
			
			/* Migration 5 */
			
			/*//Create state options
			
			//created
			Static_title createdStateSt =
					new Static_title(
							"created_state_static_title"
							, englishLanguage.getId()
							, "created");		
			objectifyRun(
					createdStateSt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			State createdState = new State(createdStateSt.getKey());
			objectifyRun(
					createdState
					, StateDAO::createState
					, out
					, gson
				);
			
			//in progress
			Static_title inProgressStateSt =
					new Static_title(
							"in_progress_state_static_title"
							, englishLanguage.getId()
							, "in_progress");		
			objectifyRun(
					inProgressStateSt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			State inProgressState = new State(inProgressStateSt.getKey());
			objectifyRun(
					inProgressState
					, StateDAO::createState
					, out
					, gson
				);
			
			//suspended
			Static_title suspendedStateSt =
					new Static_title(
							"suspended_state_static_title"
							, englishLanguage.getId()
							, "suspended");		
			objectifyRun(
					suspendedStateSt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			State suspendedState = new State(suspendedStateSt.getKey());
			objectifyRun(
					suspendedState
					, StateDAO::createState
					, out
					, gson
				);
			
			//cancelled
			Static_title cancelledStateSt =
					new Static_title(
							"cancelled_state_static_title"
							, englishLanguage.getId()
							, "cancelled");		
			objectifyRun(
					cancelledStateSt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			State cancelledState = new State(cancelledStateSt.getKey());
			objectifyRun(
					cancelledState
					, StateDAO::createState
					, out
					, gson
				);
			
			//fulfilled
			Static_title fulfilledStateSt =
					new Static_title(
							"fulfilled_state_static_title"
							, englishLanguage.getId()
							, "fulfilled");		
			objectifyRun(
					fulfilledStateSt
					, Static_titleDAO::createStatic_title
					, out
					, gson
				);
			
			State fulfilledState = new State(fulfilledStateSt.getKey());
			objectifyRun(
					fulfilledState
					, StateDAO::createState
					, out
					, gson
				);*/
			
			
			/* Миграция 6 */
			
			//Создаем в БД запись для Russian языка
			
			Language russianLanguage = new Language();
			russianLanguage.setCode("ru");
			
			objectifyRun(
					russianLanguage
					, LanguageDAO::createLang
					, out
					, gson
				);
			
			if(russianLanguage.getId() != null) {
				
				//Создаем в БД запись для русского заголовка для русского языка
				Static_title russianTitle = new Static_title();
				russianTitle.setLang_id(russianLanguage.getId());
				russianTitle.setKey("russian_lang_title");
				russianTitle.setContent("русский");
				objectifyRun(
						russianTitle
						, Static_titleDAO::createStatic_title
						, out
						, gson
					);
				
				//Создаем в БД запись для заголовка для Russian языка
				Static_title englishTitle = new Static_title();
				englishTitle.setLang_id(englishLanguage.getId());
				englishTitle.setKey("russian_lang_title");
				englishTitle.setContent("russian");
				objectifyRun(
						englishTitle
						, Static_titleDAO::createStatic_title
						, out
						, gson
					);
				
				if(englishTitle.getId() != null) {
					
					//update russianLanguage
					russianLanguage.setTitle_key(englishTitle.getKey());
					objectifyRun(
							russianLanguage
							, LanguageDAO::updateLang
							, out
							, gson
						);
				}
				
				//Создаем в БД запись для русского заголовка для английского языка
				Static_title englishRussianTitle = new Static_title();
				englishRussianTitle.setLang_id(russianLanguage.getId());
				englishRussianTitle.setKey("english_lang_title");
				englishRussianTitle.setContent("английский");
				objectifyRun(
						englishRussianTitle
						, Static_titleDAO::createStatic_title
						, out
						, gson
					);
			}
			
		} catch(Exception ex) {
			
			ex.printStackTrace();
		}
	}
}
