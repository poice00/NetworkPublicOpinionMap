package com.om.calEmotionFn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * @author  fengni <1392579733@qq.com>
 * @author  yunpeng
 * @author  songmingshuang
 * @author  xuxinyang
 * @version   v1.0
 */
public class ConnectionManager {

	private static Connection con = null;

	private final static String driver = "com.mysql.jdbc.Driver";
	private final static String url = "jdbc:mysql://localhost:3306/minsheng";
	private final static String user = "root";
	private final static String password = "123456";

	public static Connection getConnection() {
		if (con == null) {
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, user, password);
			} catch (SQLException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return con;
	}

	public static void close() {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				con = null;
			}
		}
	}

	public static void main(String[] args) {
		Connection c = ConnectionManager.getConnection();
		System.out.println(c);
		ConnectionManager.close();
	}
}
