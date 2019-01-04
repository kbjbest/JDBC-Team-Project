package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private static Connection conn;
	
	/**
	 * DB 연결
	 * @return DB 커넥션
	 */
	public static Connection getConnection() {
		if (conn == null) {
			try {
				String url = "jdbc:oracle:thin:@localhost:1521:orcl";
				String user = "east";
				String password = "1234";
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, user, password);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	/**
	 * DB 연결
	 * @param url 연결할 오라클 DB 주소
	 * @param user DB 계정명
	 * @param password DB 계정 비밀번호
	 * @return DB 커넥션
	 */
	public static Connection getConnection(String url, String user, String password) {
		if (conn == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, user, password);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	/**
	 * DB 연결 종료
	 */
	public static void close() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		conn = null;
	}
	
}
