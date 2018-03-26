package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			st.setInt(3, message.getSendUserId());
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

}
