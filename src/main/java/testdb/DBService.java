package testdb;

import java.awt.event.MouseWheelEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import db.DBManager;
import test.TestStuInfo;

public class DBService {

	public static Vector<TestStuInfo> getTestStuInfos(){
		
		Connection conn = null;
		try {
			Vector<TestStuInfo> testStuInfos = new Vector<TestStuInfo>();
			conn = testdb.DBManager.getConnection();
			String sql = "select * from edu";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			while(resultSet.next()) {
				TestStuInfo testStuInfo = new TestStuInfo();
				
				testStuInfo.setCount(resultSet.getString(2));
				testStuInfo.setPwd(resultSet.getString(3));
				testStuInfo.setCorp(resultSet.getString(4));
				testStuInfo.setName(resultSet.getString(5));
				
				testStuInfos.add(testStuInfo);
			}
			
			return testStuInfos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
