package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Message {

	private int id;
	private SimpleIntegerProperty lobbyid;
	private SimpleStringProperty message;
	private SimpleIntegerProperty senduserid;
	
	public Message() {
		id = -1;
		lobbyid = new SimpleIntegerProperty();
		message = new SimpleStringProperty();
		senduserid = new SimpleIntegerProperty();
	}
	
	public Message(int id, int lobbyid, String message, int senduserid){
		this();
		setId(id);
		setLobbyId(lobbyid);
		setMessage(message);
		setSendUserId(senduserid);
	}
	
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getLobbyId() {
		return lobbyid.get();
	}

	public void setLobbyId(int lobbyid) {
		this.lobbyid.set(lobbyid);
	}
	
	public String getMessage() {
		return message.get();
	}

	public void setMessage(String message) {
		this.message.set(message);
	}
	
	public int getSendUserId() {
		return senduserid.get();
	}

	public void setSendUserId(int senduserid) {
		this.senduserid.set(senduserid);
	}
}
