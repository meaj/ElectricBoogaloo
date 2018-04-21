package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utility.ViewManager;

public class UserGateway {

private Connection conn;
	
	public UserGateway(Connection con) {
		this.conn = con;
	}
	
	public int getNumVotesForUser(String username) throws SQLException{
		PreparedStatement st = null;
		int numVotes =0;
		try{
			st = conn.prepareStatement("SELECT numvotes FROM User WHERE username = ?");
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				numVotes = rs.getInt("numvotes");
			}
		} catch(SQLException e){
			e.printStackTrace();
			throw e;
		} finally {
			try {
					if(st != null) {
					st.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		} 
		return numVotes;
	}
	
	public void voteForUser(String username) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE User SET numvotes = numvotes + ? WHERE username = ?");
			st.setInt(1, 1);
			st.setString(2, username);
			st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public void resetUserVotes(String username) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE User SET numvotes = 0 WHERE username = ?");
			st.setString(1, username);
			st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public Boolean findUser(String username) throws SQLException{
		PreparedStatement st = null;
		User user = new User();
		try{
			st = conn.prepareStatement("SELECT username FROM User WHERE username = ?");
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				user = new User(-1, rs.getString("username"), "");
			}
		} catch(SQLException e){
			e.printStackTrace();
			throw e;
		} finally {
			try {
					if(st != null) {
					st.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		} 
		if(user.getUsername() == null){
			return false;
		}else{
			return true;
		}	
	}
	
	public Boolean authenticateUser(User toLogin) throws SQLException{
		PreparedStatement st = null;
		User user = new User();
		try{
			st = conn.prepareStatement("SELECT * FROM User WHERE username = ?");
			st.setString(1, toLogin.getUsername());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				user = new User(rs.getInt("id"), toLogin.getUsername(), rs.getString("password"));
			}
		} catch(SQLException e){
			e.printStackTrace();
			throw e;
		} finally {
			try {
					if(st != null) {
					st.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		if(toLogin.getPassword().equals(user.getPassword())){
			ViewManager.getInstance().setUser(user);
			return true;
		} else{
			return false;
		}
	}
	
	public void insert(User user) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO User (username, password)"
										+ "VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, user.getUsername());
			st.setString(2, user.getPassword());
			st.execute();
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				user.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if(st != null) {
				st.close();
				}
			} catch(SQLException e) {
				throw e;
			}
		}
	}
	
	public void updateUserLobby(User user, int lobbyId) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE User Set lobbyid = ? WHERE id = ?");
			if(lobbyId == -1) {
				st.setNull(1, java.sql.Types.INTEGER);
			} else {
				st.setInt(1, lobbyId);
			}
			st.setInt(2, user.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public void updateUserAlive(User user, int alive) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE User Set alive = ? WHERE id = ?");
			st.setInt(1, alive);
			st.setInt(2, user.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
}
