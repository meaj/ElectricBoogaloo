package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserGateway {

private Connection conn;
	
	public UserGateway(Connection con) {
		this.conn = con;
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
			st = conn.prepareStatement("SELECT password FROM User WHERE username = ?");
			st.setString(1, toLogin.getUsername());
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				user = new User(-1, "", rs.getString("password"));
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
			return true;
		} else{
			return false;
		}
	}
}
