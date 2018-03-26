package model;

public class Role {
	private String role;
	private int lobbyId;
	private int userId;
	
	public Role(String rolename, int lobby) {
		role = rolename;
		lobbyId = lobby;
	}
	
	//Accessors
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public int getLobby() {
		return lobbyId;
	}
	
	public void setLobby(int lobbyid) {
		this.lobbyId = lobbyid;
	}
	
	public int getUser() {
		return userId;
	}
	
	public void setUser(int user) {
		this.userId = user;
	}
}
