package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ConnectionFactory {
	
	public static Connection createConnection() throws Exception {
		
		Connection conn = null;
		
		//read properties file
		try {
    		Properties props = new Properties();
    		//FileInputStream fis = new FileInputStream("db.properties");
	        //props.load(fis);
	        //fis.close();

	        //create the datasource
	        MysqlDataSource ds = new MysqlDataSource();
	        ds.setURL(props.getProperty("jdbc:mysql://easel2.fulgentcorp.com:3306/lgy513"));
	        ds.setUser(props.getProperty("lgy513"));
	        ds.setPassword(props.getProperty("password"));
			
			//create connection
	        conn = ds.getConnection();
	        
		/*} catch (IOException | SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} */
		} catch(SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		System.out.println("Connection successful!");
		return conn;
	}
}
