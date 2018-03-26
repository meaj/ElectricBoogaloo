package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Message {

	private int id;
	private SimpleIntegerProperty lobbyid;
	private SimpleStringProperty message;
	private SimpleStringProperty senduser;
	
	public Message() {
		id = -1;
		lobbyid = new SimpleIntegerProperty();
		message = new SimpleStringProperty();
		senduser = new SimpleStringProperty();
	}
	
	public Message(int id, int lobbyid, String message, String senduser){
		this();
		setId(id);
		setLobbyId(lobbyid);
		setMessage(message);
		setSendUser(senduser);
	}
	
	public String toString(){
		return senduser.get() +" : "+ message.get();
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
	
	public String getSendUser() {
		return senduser.get();
	}

	public void setSendUser(String senduserid) {
		this.senduser.set(senduserid);
	}
}
