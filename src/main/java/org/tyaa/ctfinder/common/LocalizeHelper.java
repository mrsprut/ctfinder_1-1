package org.tyaa.ctfinder.common;

import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun2;
import static org.tyaa.ctfinder.common.ObjectifyQueryLauncher.objectifyRun3;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.tyaa.ctfinder.controller.LanguageDAO;
import org.tyaa.ctfinder.controller.Static_descriprionDAO;
import org.tyaa.ctfinder.controller.Static_titleDAO;
import org.tyaa.ctfinder.controller.TitleDAO;
import org.tyaa.ctfinder.entity.Language;
import org.tyaa.ctfinder.entity.Static_description;
import org.tyaa.ctfinder.entity.Static_title;
import org.tyaa.ctfinder.entity.Title;
import org.tyaa.ctfinder.model.DictionaryItem;

import com.google.gson.Gson;

public class LocalizeHelper {

	//Получение локализованного объекта статического заголовка
	//по ключу
	public static Static_title getLoclizedSTitleObject(
		String _staticTitleKey
		, Long _langId
		, PrintWriter _out
		, Gson _gson) {
		
		Static_title staticTitle = new Static_title();
		objectifyRun3(
				_staticTitleKey
				, _langId
				, staticTitle
				, Static_titleDAO::getStaticTitleByKeyAndLang
				, _out
				, _gson
			);
		if(staticTitle.getId() == null) {
		
			Language englishLanguage = new Language();
			objectifyRun2(
					"en"
					, englishLanguage
					, LanguageDAO::getLangByCode
					, _out
					, _gson);
			objectifyRun3(
					_staticTitleKey
					, englishLanguage.getId()
					, staticTitle
					, Static_titleDAO::getStaticTitleByKeyAndLang
					, _out
					, _gson
				);
		}
		return staticTitle;
	}
	
	//Получение текста локализованного статического заголовка
	//по ключу
	public static String getLoclizedSTitle(
			String _staticTitleKey
			, Long _langId
			, PrintWriter _out
			, Gson _gson) {
			
			return getLoclizedSTitleObject(_staticTitleKey, _langId, _out, _gson).getContent();
		}
	
	//Получение локализованного объекта статического заголовка
	//по содержимому
	public static Static_title getLocSTitleObjByContent(
		String _content
		, Long _langId
		, PrintWriter _out
		, Gson _gson) {
		
		Static_title staticTitle = new Static_title();
		objectifyRun3(
				_content
				, _langId
				, staticTitle
				, Static_titleDAO::getStaticTitleByContentAndLang
				, _out
				, _gson
			);
		
		if(staticTitle.getId() == null) {
		
			Language englishLanguage = new Language();
			objectifyRun2(
					"en"
					, englishLanguage
					, LanguageDAO::getLangByCode
					, _out
					, _gson);
			objectifyRun3(
					_content
					, englishLanguage.getId()
					, staticTitle
					, Static_titleDAO::getStaticTitleByContentAndLang
					, _out
					, _gson
				);
		}
		return staticTitle;
	}

	//Преобразование стандартного объекта ресурсов приложения в список моделей
	public static List<DictionaryItem> appResourceToList(ResourceBundle _resourceBundle) {
		
		List<DictionaryItem> dictionaryItems = new ArrayList<>();
		for (String key : Collections.list(_resourceBundle.getKeys())) {
			dictionaryItems.add(new DictionaryItem(key, _resourceBundle.getString(key)));
		}
		return dictionaryItems;
	}
	
	//Получение локализованного объекта статического заголовка
		//по ключу
		public static Title getLoclizedTitleObject(
			String _titleKey
			, Long _langId
			, PrintWriter _out
			, Gson _gson) {
			
			Title title = new Title();
			objectifyRun3(
					_titleKey
					, _langId
					, title
					, TitleDAO::getTitleByKeyAndLang
					, _out
					, _gson
				);
			if(title.getId() == null) {
			
				Language englishLanguage = new Language();
				objectifyRun2(
						"en"
						, englishLanguage
						, LanguageDAO::getLangByCode
						, _out
						, _gson);
				objectifyRun3(
						_titleKey
						, englishLanguage.getId()
						, title
						, TitleDAO::getTitleByKeyAndLang
						, _out
						, _gson
					);
			}
			return title;
		}
		
		//Получение текста локализованного статического заголовка
		//по ключу
		public static String getLoclizedTitle(
				String _titleKey
				, Long _langId
				, PrintWriter _out
				, Gson _gson) {
				
				return getLoclizedTitleObject(_titleKey, _langId, _out, _gson).getContent();
		}
		
		//Получение локализованного объекта статического описания
		//по ключу
		public static Static_description getLocalizedSDescriptionObject(
			String _staticDescrKey
			, Long _langId
			, PrintWriter _out
			, Gson _gson) {
			
			Static_description staticDescription = new Static_description();
			objectifyRun3(
					_staticDescrKey
					, _langId
					, staticDescription
					, Static_descriprionDAO::getStaticDescriptionByKeyAndLang
					, _out
					, _gson
				);
			if(staticDescription.getId() == null) {
			
				Language englishLanguage = new Language();
				objectifyRun2(
						"en"
						, englishLanguage
						, LanguageDAO::getLangByCode
						, _out
						, _gson);
				objectifyRun3(
						_staticDescrKey
						, englishLanguage.getId()
						, staticDescription
						, Static_descriprionDAO::getStaticDescriptionByKeyAndLang
						, _out
						, _gson
					);
			}
			return staticDescription;
		}
}
