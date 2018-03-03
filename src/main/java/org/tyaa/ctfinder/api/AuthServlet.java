package org.tyaa.ctfinder.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.repackaged.com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String CLIENT_ID =
			"662695077745-6ucmgmh2qtf9ltav8thhnot7i671ktgs.apps.googleusercontent.com";
	private static final JacksonFactory jacksonFactory = new JacksonFactory();
	
	private GoogleIdTokenVerifier verifier;
       
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
			    .setAudience(Collections.singletonList(CLIENT_ID))
			    .build();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("plain/text");
	    response.setCharacterEncoding("UTF-8");
	    
	    try (PrintWriter out = response.getWriter()) {
	    	
	    	if (request.getParameterMap().keySet().contains("idtoken")) {
	    		
	    		String idTokenString = request.getParameter("idtoken");
	    		GoogleIdToken idToken = verifier.verify(idTokenString);
				if (idToken != null) {
					
				  Payload payload = idToken.getPayload();

				  // Print user identifier
				  String userId = payload.getSubject();
				  System.out.println("User ID: " + userId);

				  // Get profile information from payload
				  String email = payload.getEmail();
				  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
				  String name = (String) payload.get("name");
				  String pictureUrl = (String) payload.get("picture");
				  String locale = (String) payload.get("locale");
				  String familyName = (String) payload.get("family_name");
				  String givenName = (String) payload.get("given_name");

				  // Use or store profile information
				  // ...
				  out.print(name);

				} else {
				  System.out.println("Invalid ID token.");
				  out.print("Invalid ID token.");
				}
	    	}
	    } catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
