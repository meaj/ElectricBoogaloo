package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Lobby {
	private int id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty maxPlayers;
	
	public Lobby() {
		id = 0;
		name = new SimpleStringProperty();
		maxPlayers = new SimpleIntegerProperty();
	}
	
	public Lobby(String name, int maxPlayers) {
		this();
		setName(name);
		setMaxPlayers(maxPlayers);
	}
	
	//Accessors
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers.get();
	}
	
	public void setMaxPlayers(int max) {
		this.maxPlayers.set(max);
	}
}