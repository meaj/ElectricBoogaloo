package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LobbyGateway {

private Connection conn;
	
	public LobbyGateway(Connection con) {
		this.conn = con;
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
			try {
				if(st != null) {
				st.close();
				}
			} catch(SQLException e) {
				throw e;
			}
		}
	}
	
	public void delete(Lobby lobby) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM Lobby WHERE id = ?");
			st.setInt(1, lobby.getId());
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
	
}
