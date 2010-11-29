package qcs.base.web.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JDBCUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:oracle:thin:@192.168.216.128:1521:orcl";
	private static final String username = "system";
	private static final String password = "qazwsx";

	public static Connection getOracleJDBCConnection(){

		try {
			return DriverManager.getConnection(url, username, password);
		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
	public Connection getConnection(){
		try{
		InitialContext ic = new InitialContext();
		DataSource ds = (DataSource)ic.lookup("java:/RHDefensoriaNovo-OracleDS");
		return ds.getConnection();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
