package com.models;

public class InboxModel {

	private String id;
	private String fromId;
	private String toId;
	private String subject;
	private String dateTime;
	private String body;
	private String readUnread;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getReadUnread() {
		return readUnread;
	}

	public void setReadUnread(String readUnread) {
		this.readUnread = readUnread;
	}

}
