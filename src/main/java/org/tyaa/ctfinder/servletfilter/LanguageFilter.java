package org.tyaa.ctfinder.servletfilter;

import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.tyaa.ctfinder.common.ObjectifyQueryLauncher;
import org.tyaa.ctfinder.common.SessionAttributes;
import org.tyaa.ctfinder.controller.LanguageDAO;
import org.tyaa.ctfinder.entity.Language;

import com.google.gson.Gson;

/**
 * Servlet Filter implementation class LanguageFilter
 */
@WebFilter(filterName="LanguageFilter")
public class LanguageFilter implements Filter {

	private static Gson gson = new Gson();
    /**
     * Default constructor. 
     */
    public LanguageFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		/*String oldContentType = response.getContentType();
		String oldCharacterEncoding = response.getCharacterEncoding();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");*/
		
		HttpSession session = ((HttpServletRequest) request).getSession(true);
		if(!SessionAttributes.isSessionAttrSet(session, SessionAttributes.languageId)) {
			
			//Получаем из БД объект английского языка
			Language englishLanguage = new Language();
			objectifyRun2(
					"en"
					, englishLanguage
					, LanguageDAO::getLangByCode
					, null
					, gson);
			session.setAttribute(SessionAttributes.languageId, englishLanguage.getId());
		}
		
		/*try (PrintWriter out = response.getWriter()) {
			
			try {
				HttpSession session = ((HttpServletRequest) request).getSession(true);
				if(!SessionAttributes.isSessionAttrSet(session, SessionAttributes.languageId)) {
					
					//Получаем из БД объект английского языка
					Language englishLanguage = new Language();
					objectifyRun2(
							"en"
							, englishLanguage
							, LanguageDAO::getLangByCode
							, out
							, gson);
					session.setAttribute(SessionAttributes.languageId, englishLanguage.getId());
				}
			} catch (Exception ex) {
				
				ObjectifyQueryLauncher.printException(ex, out, gson);
			}
		}*/
		
		// pass the request along the filter chain
		/*response.setContentType(oldContentType);
		response.setCharacterEncoding(oldCharacterEncoding);*/
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
