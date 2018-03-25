package model;

import javafx.beans.property.SimpleStringProperty;

public class User {

	private int id;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	
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
}
