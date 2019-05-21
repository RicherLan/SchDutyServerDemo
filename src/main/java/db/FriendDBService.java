package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import object.ChatMsg;
import object.MyFriendEasy;

/*
 * 好友 数据库操作
 */
public class FriendDBService {

	

	// 修改好友备注
	public static String changeFriendRemark(String myph, String friendph, String remark) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update friends set friendnickname='" + remark + "' where myphonenumber = '" + myph
					+ "' and friendphonenumber='" + friendph + "'";
			int row = conn.createStatement().executeUpdate(sql);
			if (row <= 0) {
				return "error";
			}

			return "ok";

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	
	// 获得当前账号所有好友信息
	public static Vector<MyFriendEasy> getAllFriendInfo(String myphonenumber) {
		Connection connection = null;
		Vector<MyFriendEasy> users = new Vector<MyFriendEasy>();
		try {
			connection = DBManager.getConnection();

			String sql = "SELECT * FROM users WHERE phonenumber in "
					+ "(select friendphonenumber from friends where myphonenumber='" + myphonenumber + "')";
			ResultSet rSet = connection.createStatement().executeQuery(sql);
			while (rSet.next()) {
				MyFriendEasy user = new MyFriendEasy();
				String ph = rSet.getString(1);
				sql = "select friendnickname,friendgroup from friends where myphonenumber='" + myphonenumber
						+ "' and friendphonenumber='" + ph + "'";
				ResultSet resultSet = connection.createStatement().executeQuery(sql);
				if (resultSet.next()) {
					String remark =resultSet.getString(1) ;
					if(remark==null||remark.equals("")) {
						remark=UserDBService.getNicknameOfPhonenumber(ph);
					}
					user.setRemark(remark);
					user.setFriendgroup(resultSet.getInt(2));
				}
				user.setPhonenumber(ph);
				user.setNickname(rSet.getString(2));
				// user.setIcon(rSet.getString(4));
				user.setSex(rSet.getString(7));
				
				users.add(user);
			}
			return users;

		} catch (Exception e) {
			e.printStackTrace();
			return users;
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}

	// 获得当前账号的所有在线好友
	public static Vector<String> getFriendOnline(String myphonenumber) {
		Vector<String> onlinephone = new Vector<String>();
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select friendphonenumber from friends where myphonenumber='" + myphonenumber
					+ "' and friendphonenumber in" + " (select phonenumber from users where state='在线')";
			ResultSet rSet = conn.createStatement().executeQuery(sql);

			while (rSet.next()) {
				onlinephone.add(rSet.getString(1));
			}
			return onlinephone;

		} catch (Exception e) {
			e.printStackTrace();
			return onlinephone;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	

	// 某两个用户是否是好友
	public static boolean isFriend(String ph1, String ph2) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select id from friends where myphonenumber='" + ph1 + "' and friendphonenumber='" + ph2
					+ "' or myphonenumber='" + ph2 + "' and friendphonenumber='" + ph1 + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
