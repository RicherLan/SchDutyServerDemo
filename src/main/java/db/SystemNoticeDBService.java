package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.Notice;

/*
 *系统通知的  数据库操作 
 */

public class SystemNoticeDBService {

	
	// 添加某人的系统通知
	public static boolean UpdateNoticeMsgByPhonenumber(String phonenumber, String noticemsg, int isnoticed) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update users set noticemsg=?,isnoticed=? where phonenumber=?";
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setString(1, noticemsg);
			pst.setInt(2, isnoticed);
			pst.setString(3, phonenumber);

			if (pst.executeUpdate() <= 0) {

				return false;
			}
			// System.out.println("1111111111111111111111111111");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 得到某人的系统通知
	public static Notice getNoticeByPhonenumber(String phonenumber) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select noticemsg,isnoticed from 1users where phonenumber='" + phonenumber + "'";
			ResultSet rs = conn.createStatement().executeQuery(sql);

			if (rs.next()) {

				Notice s = new Notice();
				s.setPhonenumber(phonenumber);
				s.setNoticemsg(rs.getString(1));
				s.setIsnoticed(rs.getInt(2));

				return s;
			} else {

				return null;
			}

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

	// 更改某人的系统通知 用户是否已经阅读
	public static boolean updateNoticeByPhonenumber(String phonenumber, int isnoticed) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update users set isnoticed=? where phonenumber=?";
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setInt(1, isnoticed);
			pst.setString(2, phonenumber);

			if (pst.executeUpdate() <= 0) {

				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
