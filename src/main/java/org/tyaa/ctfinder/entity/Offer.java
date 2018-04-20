package org.tyaa.ctfinder.entity;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Offer {
	@Id
	private Long id;
	@Index
	@Expose
	//Идентификатор типа задания, например: техника, ИТ, помощь, природа 
	private int offer_type_id;
	@Index
	@Expose
	//Идентификатор состояния задания, например: создано, выполняется, завершено, отменено 
	private int state_id;
	@Index
	@Expose
	//Ключ заголовка
	private String title_key;
	@Expose
	//Ключ описания
	private String description_key;
	@Index
	@Expose
	//ИД пользователя
	private int user_id;
	@Index
	@Expose
	//ИД страны
	private int country_id;
	@Index
	@Expose
	//ИД города
	private int city_id;
	@Index
	@Expose
	//Дата желаемого старта
	private Date start_date;
	@Index
	@Expose
	//Дата желаемого окончания
	private Date finish_date;
	@Index
	@Expose
	//Дата реального старта
	private Date started_at;
	@Index
	@Expose
	//Дата реального окончания
	private Date completed_at;
	@Index
	@Expose
	//Дата создания записи
	private Date created_at;
	@Index
	@Expose
	//Дата последнего обновления записи
	private Date updated_at;
	
	public Offer() {
		super();
	}

	public Offer(int offer_type_id, String title_key, String description_key, int user_id, int country_id, int city_id,
			Date start_date, Date finish_date) {
		super();
		this.offer_type_id = offer_type_id;
		this.title_key = title_key;
		this.description_key = description_key;
		this.user_id = user_id;
		this.country_id = country_id;
		this.city_id = city_id;
		this.start_date = start_date;
		this.finish_date = finish_date;
	}

	/**
	 * @return the offer_type_id
	 */
	public int getOffer_type_id() {
		return offer_type_id;
	}

	/**
	 * @param offer_type_id the offer_type_id to set
	 */
	public void setOffer_type_id(int offer_type_id) {
		this.offer_type_id = offer_type_id;
	}

	/**
	 * @return the state_id
	 */
	public int getState_id() {
		return state_id;
	}

	/**
	 * @param state_id the state_id to set
	 */
	public void setState_id(int state_id) {
		this.state_id = state_id;
	}

	/**
	 * @return the title_key
	 */
	public String getTitle_key() {
		return title_key;
	}

	/**
	 * @param title_key the title_key to set
	 */
	public void setTitle_key(String title_key) {
		this.title_key = title_key;
	}

	/**
	 * @return the description_key
	 */
	public String getDescription_key() {
		return description_key;
	}

	/**
	 * @param description_key the description_key to set
	 */
	public void setDescription_key(String description_key) {
		this.description_key = description_key;
	}

	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the country_id
	 */
	public int getCountry_id() {
		return country_id;
	}

	/**
	 * @param country_id the country_id to set
	 */
	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	/**
	 * @return the city_id
	 */
	public int getCity_id() {
		return city_id;
	}

	/**
	 * @param city_id the city_id to set
	 */
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	/**
	 * @return the start_date
	 */
	public Date getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date the start_date to set
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	/**
	 * @return the finish_date
	 */
	public Date getFinish_date() {
		return finish_date;
	}

	/**
	 * @param finish_date the finish_date to set
	 */
	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
	}

	/**
	 * @return the started_at
	 */
	public Date getStarted_at() {
		return started_at;
	}

	/**
	 * @param started_at the started_at to set
	 */
	public void setStarted_at(Date started_at) {
		this.started_at = started_at;
	}

	/**
	 * @return the completed_at
	 */
	public Date getCompleted_at() {
		return completed_at;
	}

	/**
	 * @param completed_at the completed_at to set
	 */
	public void setCompleted_at(Date completed_at) {
		this.completed_at = completed_at;
	}

	/**
	 * @return the created_at
	 */
	public Date getCreated_at() {
		return created_at;
	}

	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	/**
	 * @return the updated_at
	 */
	public Date getUpdated_at() {
		return updated_at;
	}

	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
}
