package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * 用户对数据库的操作 的方法   都封装在该类中
 */

public class DBService {

	public static boolean addUpdateDelete(String sql, Object[] params) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			PreparedStatement pStatement = conn.prepareStatement(sql);
			if (params != null && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					pStatement.setObject(i + 1, params[i]);
				}
			}

			int row = pStatement.executeUpdate();
			if (row > 0) {
				return true;
			}

			return false;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
