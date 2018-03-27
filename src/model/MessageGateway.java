package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MessageGateway {
private Connection conn;
	
	public MessageGateway(Connection con) {
		this.conn = con;
	}
	public void insert(Message message) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO Chatlog (lobbyid, message, senduser)"
										+ "VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			st.setInt(1, message.getLobbyId());
			st.setString(2, message.getMessage());
			st.setString(3, message.getSendUser());
			st.execute();
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				message.setId(rs.getInt(1));
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
	
	public ObservableList<Message> getMessagesForLobby(int lobbyId) throws SQLException {
		ObservableList<Message> messages = FXCollections.observableArrayList();
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("SELECT * from Chatlog WHERE lobbyid = ? order by id");
			st.setInt(1, lobbyId);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Message message = new Message(-1, rs.getInt("lobbyid")
										, rs.getString("message")
										, rs.getString("senduser"));
				message.setId(rs.getInt("id"));
				messages.add(message);
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
		return messages;
	}
	
	public void deleteMessagesForLobby(int lobbyId) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM Chatlog WHERE lobbyid = ?");
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

}
