package org.tyaa.ctfinder.api.publ;

import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun;
import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tyaa.ctfinder.common.ErrorStrings;
import org.tyaa.ctfinder.common.HttpReqParams;
import org.tyaa.ctfinder.common.HttpRespWords;
import org.tyaa.ctfinder.common.ObjectifyQueryLauncher;
import org.tyaa.ctfinder.common.SessionAttributes;
import org.tyaa.ctfinder.controller.SubscriptionDAO;
import org.tyaa.ctfinder.entity.Subscription;
import org.tyaa.ctfinder.model.RespData;

import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;

/**
 * Servlet implementation class TasksServlet
 */
@WebServlet("/subscription")
public class SubscriptionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	static {
		
		ObjectifyService.register(Subscription.class);
	}	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscriptionServlet() {
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
	    Gson gson = new Gson();
	    		//new GsonBuilder()
	    		//.setDateFormat("yyyy-MM-dd").create();
	    
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
									
								try {
									Long authorId =
											Long.parseLong(req.getParameter(HttpReqParams.authorId));
									Long subscriberId =
											(Long)session.getAttribute(
												SessionAttributes.userId
											);
									
									Subscription subscription =
											new Subscription(authorId, subscriberId);
									
									objectifyRun(
											subscription
											, SubscriptionDAO::createSubscription
											, out
											, gson);
									
									ArrayList<String> al = new ArrayList<>();
									al.add(HttpRespWords.created);
									RespData rd = new RespData(al);
									String successJson = gson.toJson(rd);
									out.print(successJson);
								} catch (Exception ex) {
									
									ObjectifyQueryLauncher.printException(ex, out, gson);
								}
								break;
							}
							case HttpReqParams.delete : {
								
								Long authorId =
										Long.parseLong(req.getParameter(HttpReqParams.authorId));
								
								//Если запрос пришел со страницы сайта во время работы с ним -
								//берем идентификатор пользователя из сеанса,
								//если запрос пришел со страницы отписки - берем из параметра запроса
								Long subscriberId = null;
								if(req.getParameterMap().keySet().contains(HttpReqParams.subscriberId)) {
									
									subscriberId =
											Long.parseLong(
												req.getParameter(HttpReqParams.subscriberId)
											);
								} else {
									
									subscriberId =
											(Long)session.getAttribute(
												SessionAttributes.userId
											);
								}
								
								//Получение объекта подписки из БД
								Subscription subscription =
										new Subscription();
								objectifyRun3(
										authorId
										, subscriberId
										, subscription
										, (_object1, _object2, _object3) -> {
											try {
												SubscriptionDAO.findByAuthorAndSubscriber(_object1, _object2, _object3);
											} catch (IllegalArgumentException | IllegalAccessException ex) {
												ObjectifyQueryLauncher.printException(ex, out, gson);
											} 
										}
										, out
										, gson
									);
								//Удаление объекта подписки из БД
								objectifyRun(
										subscription.getId()
										, SubscriptionDAO::removeSubscription
										, out
										, gson
									);
								
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
