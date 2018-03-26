package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Lobby {
	private int id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty maxPlayers;
	private int numDetectives;
	private int numVampires;
	private int numVillagers;
	private int numPriests;
	
	public Lobby() {
		id = 0;
		name = new SimpleStringProperty();
		maxPlayers = new SimpleIntegerProperty();
	}
	
	public Lobby(String name, int maxPlayers) {
		this();
		setName(name);
		setMaxPlayers(maxPlayers);
		numDetectives=0;
		numVampires=0;
		numVillagers=0;
		numPriests=0;
	}
	
	public Lobby(String name, int maxPlayers, int nD, int nVamp, int nVil, int nP) {
		this();
		setName(name);
		setMaxPlayers(maxPlayers);
		numDetectives=nD;
		numVampires=nVamp;
		numVillagers=nVil;
		numPriests=nP;
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
	
	public void setNumDetectives(int nD) {
		numDetectives=nD;
	}
	
	public int getNumDetectives() {
		return numDetectives;
	}
	public void setNumVampires(int nV) {
		numVampires=nV;
	}
	
	public int getNumVampires() {
		return numVampires;
	}
	public void setNumVillagers(int nV) {
		numVillagers=nV;
	}
	
	public int getNumVillagers() {
		return numVillagers;
	}
	public void setNumPriest(int nP) {
		numPriests=nP;
	}
	
	public int getNumPriest() {
		return numPriests;
	}
}