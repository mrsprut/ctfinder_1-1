package org.tyaa.ctfinder.api;

import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tyaa.ctfinder.controller.CountryDAO;
import org.tyaa.ctfinder.controller.LanguageDAO;
import org.tyaa.ctfinder.controller.Offer_typeDAO;
import org.tyaa.ctfinder.controller.Static_descriprionDAO;
import org.tyaa.ctfinder.controller.Static_titleDAO;
import org.tyaa.ctfinder.entity.Country;
import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Offer_type;
import org.tyaa.ctfinder.entity.Static_description;
import org.tyaa.ctfinder.entity.Static_title;
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
		ObjectifyService.register(Static_title.class);
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
			/*Language englishLanguage = new Language();
			ObjectifyService.run(new VoidWork() {
				public void vrun() {
					try {
						LanguageDAO.getLangByCode("en", englishLanguage);
					} catch (Exception ex) {

						String errorJson = gson.toJson(ex.getMessage());
						out.print(errorJson);
					}
				}
			});*/
			
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
		} catch(Exception ex) {
			
			ex.printStackTrace();
		}
	}
}
