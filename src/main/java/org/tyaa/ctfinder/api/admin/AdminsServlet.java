package org.tyaa.ctfinder.api.admin;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Servlet implementation class AdminsServlet
 */
@WebServlet("/AdminsServlet")
public class AdminsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private UserService mUserService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		mUserService = UserServiceFactory.getUserService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().print(mUserService.toString());
		if(mUserService.isUserLoggedIn()) {
			
			
			if(request.getParameterMap().containsKey("logout")) {
				
				response.sendRedirect(mUserService.createLogoutURL(request.getRequestURI()));
			} else {
				
				if(mUserService.isUserAdmin()) {
					
					response.getWriter().print("admin");
				} else {
					
					response.getWriter().print("user");
				}
			}
			
			
			
			
		} else {
			response.sendRedirect(mUserService.createLoginURL(request.getRequestURI()));
			
		}
		/*try {
			response.getWriter().print(mUserService.getCurrentUser());
		} catch(Exception ex) {response.getWriter().print(ex.getMessage());}*/
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
