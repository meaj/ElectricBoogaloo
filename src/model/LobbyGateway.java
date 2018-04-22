package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LobbyGateway {

private Connection conn;
	
	public LobbyGateway(Connection con) {
		this.conn = con;
	}
	
	public ObservableList<User> getUsersByLobbyId(int lobbyId) throws SQLException {
		ObservableList<User> users = FXCollections.observableArrayList();
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM User WHERE lobbyid = ? ORDER BY id");
			st.setInt(1, lobbyId);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
				users.add(user);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
		return users;
	}
	
	public void insertLobby(Lobby lobby) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO Lobby (name, maxplayers)"
										+ "VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			st.setString(1, lobby.getName());
			st.setInt(2, lobby.getMaxPlayers());
			st.execute();
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				lobby.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public void delete(int lobbyId) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM Lobby WHERE id = ?");
			st.setInt(1, lobbyId);
			st.execute();
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
	}
	
	public ObservableList<Lobby> getLobbies() throws SQLException {
		ObservableList<Lobby> lobbies = FXCollections.observableArrayList();
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM Lobby ORDER BY id");
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Lobby lobby = new Lobby(rs.getString("name"), rs.getInt("maxplayers"));
				lobby.setId(rs.getInt("id"));
				lobbies.add(lobby);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
		return lobbies;
	}
	
	public void updateReadyCount(int lobbyId, int value) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Lobby SET readycount = readycount + ? WHERE id = ?");
			st.setInt(1, value);
			st.setInt(2, lobbyId);
			st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public void resetReadyCount(int lobbyId) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Lobby SET readycount = ? WHERE id = ?");
			st.setInt(1, 0);
			st.setInt(2, lobbyId);
			st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public int getReadyCount(int lobbyId) throws SQLException {
		int readyCount = 0;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM Lobby WHERE id = ?");
			st.setInt(1, lobbyId);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				readyCount = rs.getInt("readycount");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
		return readyCount;
	}
	
	public void decrementAliveCount(int lobbyId) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Lobby SET alivecount = alivecount - 1 WHERE id = ?");
			st.setInt(1,lobbyId);
			st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public void setAliveCount(int lobbyId, int alive) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Lobby SET alivecount = ? WHERE id = ?");
			st.setInt(1,alive);
			st.setInt(2, lobbyId);
			st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public int getAliveCount(int lobbyId) throws SQLException {
		int aliveCount = 0;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM Lobby WHERE id = ?");
			st.setInt(1, lobbyId);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				aliveCount = rs.getInt("alivecount");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
		return aliveCount;
	}
	
	public void setFinishedCount(int lobbyId, int finished) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Lobby SET finishedcount = ? WHERE id = ?");
			st.setInt(1,finished);
			st.setInt(2, lobbyId);
			st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public int getFinishedCount(int lobbyId) throws SQLException {
		int finishedCount = 0;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * FROM Lobby WHERE id = ?");
			st.setInt(1, lobbyId);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				finishedCount = rs.getInt("finishedcount");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
		return finishedCount;
	}
	
	public void incrementFinishedCount(int lobbyId) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Lobby SET finishedcount = finishedcount + 1 WHERE id = ?");
			st.setInt(1,lobbyId);
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
