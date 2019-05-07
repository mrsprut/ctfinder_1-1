package org.tyaa.ctfinder.api;

import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun;
import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tyaa.ctfinder.common.ErrorStrings;
import org.tyaa.ctfinder.common.HttpReqParams;
import org.tyaa.ctfinder.common.SessionAttributes;
import org.tyaa.ctfinder.controller.CountryDAO;
import org.tyaa.ctfinder.controller.LanguageDAO;
import org.tyaa.ctfinder.controller.UserDAO;
import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.User;
import org.tyaa.ctfinder.entity.User_type;
import org.tyaa.ctfinder.model.RespData;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// private static final String CLIENT_ID = "662695077745-6ucmgmh2qtf9ltav8thhnot7i671ktgs.apps.googleusercontent.com";
	private static final String CLIENT_ID = "551611329284-gl2rot3pi55pu6t1h4utl43qcfm01bdh.apps.googleusercontent.com";
	private static final JacksonFactory jacksonFactory = new JacksonFactory();

	private GoogleIdTokenVerifier verifier;
	
	static {
		
		//Entities registration
		ObjectifyService.register(User.class);
		ObjectifyService.register(Static_title.class);
		ObjectifyService.register(User_type.class);
		ObjectifyService.register(Language.class);
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
				// Specify the CLIENT_ID of the app that accesses the backend:
				.setAudience(Collections.singletonList(CLIENT_ID)).build();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession(true);
		
		Gson gson = //new Gson();
	    		new GsonBuilder()
	    		.excludeFieldsWithoutExposeAnnotation()
	            .setDateFormat("yyyy-MM-dd").create();

		try (PrintWriter out = resp.getWriter()) {

			if (req.getParameterMap().keySet().contains("idtoken")) {

				String idTokenString = req.getParameter("idtoken");
				GoogleIdToken idToken = verifier.verify(idTokenString);
				if (idToken != null) {

					Payload payload = idToken.getPayload();

					// Print user identifier
					String userId = payload.getSubject();
					// System.out.println("User ID: " + userId);

					// Use or store profile information

					//
					User user = new User();
					
					objectifyRun2(
							userId
							, user
							, UserDAO::findUserByGoogleId
							, out
							, gson
						);
					
					/*ObjectifyService.run(new VoidWork() {
						public void vrun() {
							try {
								UserDAO.findUserByGoogleId(userId, user);
							} catch (Exception ex) {

								//RespData rd = new RespData(ex.getMessage());
								RespData rd = new RespData("findUserByGoogleId");
								String errorJson = gson.toJson(rd);
								out.print(errorJson);
							}
						}
					});*/
					
					if(user.getGoogle_id() == null || user.getGoogle_id().equals("")) {
						
						// Get profile information from payload
						String email = payload.getEmail();
						boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
						String name = (String) payload.get("name");
						String pictureUrl = (String) payload.get("picture");
						String locale = (String) payload.get("locale");
						String familyName = (String) payload.get("family_name");
						String givenName = (String) payload.get("given_name");
						
						//TODO use locale
						
						user.setGoogle_id(userId);
						user.setName(name);
						user.setEmail(email);
						user.setPicture(pictureUrl);
												
						ObjectifyService.run(new VoidWork() {
							public void vrun() {
								try {
									UserDAO.createUser(user);
								} catch (Exception ex) {

									RespData rd = new RespData(ex.getMessage());
									//RespData rd = new RespData("createUser");
									//String errorTrace = "";
									//for(StackTraceElement el: ex.getStackTrace()) {
									//	errorTrace += el.toString();
									//}
									//RespData rd = new RespData(errorTrace);
									String errorJson = gson.toJson(rd);
									out.print(errorJson);
								}
							}
						});
					}

					try {
						
						session.setAttribute(SessionAttributes.userId, user.getId());
						ArrayList al = new ArrayList();
						al.add(user);
						RespData rd = new RespData(al);
						String successJson = gson.toJson(rd);
						out.print(successJson);
					}  catch (Exception ex) {

						RespData rd = new RespData(ex.getMessage());
						//RespData rd = new RespData("findUserByGoogleId");
						String errorJson = gson.toJson(rd);
						out.print(errorJson);
					}

				} else {
					//System.out.println("Invalid ID token.");
					RespData rd = new RespData("Invalid ID token.");
					String errorJson = gson.toJson(rd);
					out.print(errorJson);
				}
			} else if(req.getParameterMap().keySet().contains(HttpReqParams.action)) {
				
					
				String actionString = req.getParameter(HttpReqParams.action);
				switch(actionString) {
					
					case HttpReqParams.navigate:{
						
						String pageString = req.getParameter(HttpReqParams.page);
						
						if(SessionAttributes.isSessionAttrSet(session, SessionAttributes.userId)
								|| pageString.equals("home")
								|| pageString.equals("about")) {
							
							
							resp.sendRedirect("pages/" + pageString + ".htm");
						} else {
							resp.setContentType("text/plain");
							/*RespData rd = new RespData(ErrorStrings.noSession);
							String errorJson = gson.toJson(rd);
							out.print(errorJson);*/
							out.print(ErrorStrings.noSession);
						}
					}
				}
				
			} else {
				//LogOut
				session.removeAttribute(SessionAttributes.userId);
				ArrayList al = new ArrayList();
				al.add("logout");
				RespData rd = new RespData(al);
				String successJson = gson.toJson(rd);
				out.print(successJson);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try (PrintWriter out = resp.getWriter()) {
				RespData rd = new RespData("GeneralSecurityException");
				String errorJson = gson.toJson(rd);
				out.print(errorJson);
			}
			e.printStackTrace();
		}
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
	}
}
