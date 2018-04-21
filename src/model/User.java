package model;

import javafx.beans.property.SimpleStringProperty;

public class User {

	private int id;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	private int lobbyid;
	private boolean ready = false;
	private String role;
	private int alive;
	
	public User() {
		id = -1;
		username = new SimpleStringProperty();
		password = new SimpleStringProperty();
	}
	
	public User(int id, String username, String password){
		this();
		setId(id);
		setUsername(username);
		setPassword(password);
		alive = 1;
	}
	
	public int getLobbyId() {
		return this.lobbyid;
	}
	
	public void setLobbyId(int id) {
		this.lobbyid = id;
	}
	
	public String toString() {
		return username.get();
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username.set(username);
	}
	
	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public boolean getReady() {
		return this.ready;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
