package org.tyaa.ctfinder.api;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
import org.tyaa.ctfinder.model.RespData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;

/**
 * Servlet implementation class InitServlet
 */
@WebServlet("/debug")
public class DebugServlet extends HttpServlet {

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
	public DebugServlet() {
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
			
			/*RespData rd = new RespData("test");
			String errorJson = gson.toJson(rd);
			out.print(errorJson);*/
			
			/*ArrayList al = new ArrayList();
			al.add("1");
			al.add(2);
			RespData rd = new RespData(al);
			String successJson = gson.toJson(rd);
			out.print(successJson);*/
			
			/*Static_title st = new Static_title();
			st.setKey("testKey");
			st.setId(1000L);
			
			ArrayList al = new ArrayList();
			al.add(st);
			RespData rd = new RespData(al);
			String successJson = gson.toJson(rd);
			out.print(successJson);*/
			
			/*try {
				
				Static_title staticTitle =
						ofy().load()
						.type(Static_title.class)
						.filter("content", "user")
						.first()
						.now();
			} catch(Exception ex) {
				
					RespData rd = new RespData(ex.getMessage());
					String errorJson = gson.toJson(rd);
					out.print(errorJson);
			}*/
			
			Static_title staticTitle =
					ofy().load()
					.type(Static_title.class)
					.filter("content =", "user")
					.first()
					.now();
			
			/*Static_title staticTitle =
					ofy().load()
					.type(Static_title.class)
					.first()
					.now();*/
			
			if(staticTitle != null) {
				
				/*Static_title st = new Static_title();
				st.setKey("testKey");
				st.setId(1000L);
				
				ArrayList al = new ArrayList();
				al.add(st);
				RespData rd = new RespData(al);
				String successJson = gson.toJson(rd);
				out.print(successJson);*/
				
				ArrayList al = new ArrayList();
				/*al.add(staticTitle.getId());
				al.add(staticTitle.getKey());
				al.add(staticTitle.getLang_id());
				al.add(staticTitle.getContent());*/
				al.add(staticTitle);
				
				RespData rd = new RespData(al);
				String successJson = gson.toJson(rd);
				//String successJson = gson.toJson(staticTitle.getContent());
				out.print(successJson);
			} else {
				
				RespData rd = new RespData("staticTitle = null");
				String errorJson = gson.toJson(rd);
				out.print(errorJson);
			}
			
			
		} catch(Exception ex) {
			
			try (PrintWriter out = resp.getWriter()) {
				RespData rd = new RespData(ex.getMessage());
				String errorJson = gson.toJson(rd);
				out.print(errorJson);
			}
		}
	}

	
}
