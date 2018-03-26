package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleGateway {
	
	private Connection conn;
	
	public RoleGateway(Connection con) {
		this.conn = con;
	}
	
	public void insertRole(Role role) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO Role (lobbyid, rolename)"
										+ "VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			st.setInt(1, role.getLobby());
			st.setString(2, role.getRole());
			st.execute();
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				role.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public void updateRoleForUser(int roleId, int userId) throws SQLException {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Role SET userid = ? WHERE id = ?");
			st.setInt(1, userId);
			st.setInt(2, roleId);
			st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}
	
	public void updateRemoveUser(int userId) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE Role SET userid = ? WHERE userid = ?");
			st.setNull(1, java.sql.Types.INTEGER);
			st.setInt(2, userId);
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
