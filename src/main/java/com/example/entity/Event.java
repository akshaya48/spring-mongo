package com.example.entity;

import java.util.Date;

import com.example.util.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {
	
	@JsonIgnore
	private int eventId;
	
	private String eventName;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date eventDate;
	
	
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Event(String eventName, Date eventDate) {
		super();
		this.eventName = eventName;
		this.eventDate = eventDate;
	}
	
	
}
