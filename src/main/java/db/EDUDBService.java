package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * 教务系统相关的数据库操作
 */
public class EDUDBService {

	// 存储教务账号和密码
	public static void saveEdu(String ph, String schoolname, String educount, String edupassword) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "delete from studentedu where phonenumber='" + ph + "' and educount='" + educount + "'";
			conn.createStatement().executeUpdate(sql);

			sql = "insert into studentedu(phonenumber,schoolname,educount,edupassword) values(?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, ph);
			preparedStatement.setString(2, schoolname);
			preparedStatement.setString(3, educount);
			preparedStatement.setString(4, edupassword);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 保存某用户课表到数据库
	public static void saveCourse(String ph, String course, int year, int xueqi) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "delete from studentcourse where ph='" + ph + "' and year='" + year + "' and xueqi='" + xueqi
					+ "'";
			conn.createStatement().executeUpdate(sql);

			sql = "insert into studentcourse(ph,course,year,xueqi) values(?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, ph);
			preparedStatement.setString(2, course);
			preparedStatement.setInt(3, year);
			preparedStatement.setInt(4, xueqi);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 用户请求教务的成绩或者课表时 将其教务账号和密码拿到
	public static void saveEdu(String username, String phonenumber, String schoolname, String educount,
			String edupassword) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select schoolid from schools where schoolname='" + schoolname + "'";
			ResultSet rSet = conn.createStatement().executeQuery(sql);
			int schoolid = -1;
			if (rSet.next()) {
				schoolid = rSet.getInt(0);
			}
			String tablename = schoolid + "studentedu";

			sql = "delete from " + tablename + " where phonenumber='" + phonenumber + "'";

			conn.createStatement().executeUpdate(sql);

			sql = "insert into " + tablename
					+ "(username,phonenumber,schoolname,educount,edupassword) values(?,?,?,?,?)";
			conn.createStatement().executeUpdate(sql);

		} catch (Exception a) {
			a.printStackTrace();
		}

	}

}
