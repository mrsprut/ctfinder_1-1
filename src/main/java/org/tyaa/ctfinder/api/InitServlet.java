package org.tyaa.ctfinder.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tyaa.ctfinder.controller.LanguageDAO;
import org.tyaa.ctfinder.controller.Static_titleDAO;
import org.tyaa.ctfinder.controller.User_typeDAO;
import org.tyaa.ctfinder.entity.Language;
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
		//ObjectifyService.register(User.class);
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
			
			//Создаем в БД запись для английского языка
			
			Language englishLanguage = new Language();
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
					
					/*//Создаем в БД запись для заголовка для английского языка
					
					Static_title englishTitle = new Static_title();
					englishTitle.setLang_id(englishLanguage.getId());
					englishTitle.setKey("english");
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
					});*/
					
					
				}
			}
		} catch(Exception ex) {
			
			ex.printStackTrace();
		}
	}

	
}
