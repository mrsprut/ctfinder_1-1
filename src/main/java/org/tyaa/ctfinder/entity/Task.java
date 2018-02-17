package org.tyaa.ctfinder.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Task {
	
	@Id
	private Long id;
	
	private String customer;

    private String text;

    private Date date;
    
    private Long stateId;
    
    public Task() {
		super();
	}

	public Task(Long id, String customer, String text, Date date, Long stateId) {
		super();
		this.id = id;
		this.customer = customer;
		this.text = text;
		this.date = date;
		this.stateId = stateId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task task = (Task) object;
        if ((this.id == null && task.id != null) || (this.id != null && !this.id.equals(task.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.tyaa.ctfinder.entity.Task[ id=" + id + " ]";
    }
}
