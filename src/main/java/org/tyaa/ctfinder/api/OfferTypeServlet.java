package org.tyaa.ctfinder.api;

import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun;
import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
import org.tyaa.ctfinder.common.LocalizeHelper;
import org.tyaa.ctfinder.common.SessionAttributes;
import org.tyaa.ctfinder.controller.Offer_typeDAO;
import org.tyaa.ctfinder.controller.Static_descriprionDAO;
import org.tyaa.ctfinder.entity.Country;
import org.tyaa.ctfinder.entity.Offer_type;
import org.tyaa.ctfinder.entity.Static_description;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.model.OfferTypeItem;
import org.tyaa.ctfinder.model.RespData;

import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;

/**
 * Servlet implementation class TasksServlet
 */
@WebServlet("/offertype")
public class OfferTypeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Gson gson;
	
	static {
		
		ObjectifyService.register(Offer_type.class);
		ObjectifyService.register(Static_description.class);
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OfferTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		//Преобразователь из объектов Java в строки JSON
	    gson = new Gson();
    		//new GsonBuilder()
    		//.setDateFormat("yyyy-MM-dd").create();
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    
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
								
								ArrayList<String> al = new ArrayList<>();
								al.add(HttpRespWords.created);
								RespData rd = new RespData(al);
								String successJson = gson.toJson(rd);
								out.print(successJson);
								break;
							}
							case HttpReqParams.getAll : {
								
								//Получаем из сессии значение текущего языка
						    	//(по умолчанию выдает английский язык)
						    	Long currentLanguageId =
										(Long)session.getAttribute(
											SessionAttributes.languageId
										);
								
								List<Offer_type> offerTypeList = new ArrayList<>();
								objectifyRun(
										offerTypeList
										, Offer_typeDAO::getAllOfferTypes
										, out
										, gson
									);
								//TODO get description by key n lang
								List<OfferTypeItem> offerTypeItemList =
									offerTypeList.stream()
										.map(
											ot -> {
												Static_description st =
													LocalizeHelper.getLocalizedSDescriptionObject(
														((Offer_type)ot).getDescription_key()
														, currentLanguageId
														, out
														, gson
													);
												
												/*objectifyRun2(
														((Offer_type)ot).getDescription_key()
														, st
														, Static_descriprionDAO::getStaticDescriptionByKey
														, out
														, gson);*/
												return new OfferTypeItem(
														((Offer_type)ot).getId()
														, st.getContent()
													);
											})
										.collect(Collectors.toList());
								
								/*
								
								objectifyRun2(st, (_object1, _object2) -> {
													try {
														Static_descriprionDAO.getStaticDescriptionByKey(
																((Offer_type)ot).getDescription_key()
																, _object
															);
													} catch (Exception e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												}, out);
												return new OfferTypeItem(
														((Offer_type)ot).getId()
														, st.getContent()
													);
								
								*/
								
		                    	
		                    	/*try {
		                    		
		                    		ObjectifyService.run(new VoidWork() {
		                    		    public void vrun() {
		                    		    	try {
		                    		    		Offer_typeDAO.getAllOfferTypes(offerTypeList);
		    								} catch (Exception ex) {
		    									
		    									RespData result = new RespData(ex.getMessage());
		    		                            String resultJsonString = gson.toJson(result);
		    		                            out.print(resultJsonString);
		    								}
		                    		    }
		                    		});
		                    		
		                    		
		                    	} catch (Exception ex) {
		                            
		                    		RespData result = new RespData(ex.getMessage());
		                            String resultJsonString = gson.toJson(result);
		                            out.print(resultJsonString);
		                    	}*/
		                    	RespData result = new RespData(offerTypeItemList);
		                        String resultJsonString = gson.toJson(result);
		                        out.print(resultJsonString);
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
					
					RespData rd = new RespData("error");
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
				
				RespData rd = new RespData("nosession");
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
