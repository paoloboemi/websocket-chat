package com.rubinochat.bean;
import java.io.Serializable;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String type;
	
	private String message;
	
	private String username;
	
	private String date;
	
	public Message() {
		super();
	}

	public Message(String type, String message, String username, String date) {
		super();
		this.type = type;
		this.message = message;
		this.username = username;
		this.date = date;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [type=");
		builder.append(type);
		builder.append(", message=");
		builder.append(message);
		builder.append(", username=");
		builder.append(username);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}

}
