package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.print.DocFlavor.STRING;

import object.ChatMsg;

/*
 * 好友添加  群添加   删除等    数据库操作
 */
public class FriengOrGroupRequestDBService {

	// 好友或群添加申请 返回消息的id
	// 退出群时 也要在数据库中保存 因为要让群主和群管理员知道
	public static int requestFriendOrGroup(String senderid,  String type, String reciverid, long msgtime, String msg,
			String state, int groupid) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "delete from requestfriendorgroup where senderid='" + senderid + "' and reciverid='"
					+ reciverid + "' and state='未读' and type='" + type + "'";
			conn.createStatement().executeUpdate(sql);
			sql = "select nickname,sex from users where phonenumber='" + senderid + "'";
			ResultSet eResultSet = conn.createStatement().executeQuery(sql);
			String sendernickname = "";
			String sendersex = "";
			String sendericon = "";
			if (eResultSet.next()) {
				sendernickname = eResultSet.getString(1);
				sendersex = eResultSet.getString(2);
				// sendericon = eResultSet.getString(3);
			}

			if (type.equals("exitgroup")) {
				String[] strings = senderid.split(" ");
				sendernickname = strings[1];
				senderid = strings[0];
			}

			sql = "INSERT INTO requestfriendorgroup(senderid,sendernickname,sendersex,sendericon,type,reciverid, msgtime,msg,state,groupid) VALUES(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, senderid);
			pst.setString(2, sendernickname);
			pst.setString(3, sendersex);
			pst.setString(4, sendericon);
			pst.setString(5, type);
			pst.setString(6, reciverid);
			pst.setLong(7, msgtime);
			pst.setString(8, msg);
			pst.setString(9, state);
			pst.setInt(10, groupid);
			int row = pst.executeUpdate();
			if (row <= 0) {
				return -1;
			}

			ResultSet rs = pst.getGeneratedKeys();
			int uid = -1;
			if (rs.next()) {
				uid = rs.getInt(1);
			}

			return uid;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 退群
	public static String exitGroup(String myphonenumber, int groupid) {

		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			String sql2 = "update usergroup set number=number-1";
			int row = conn.createStatement().executeUpdate(sql2);
			if (row > 0) {
				String sql = "delete  from usertogroup where groupid='" + groupid + "' and userid='" + myphonenumber
						+ "'";

				row = conn.createStatement().executeUpdate(sql);
				if (row <= 0) {
					sql2 = "update usergroup set number=number+1";
					conn.createStatement().executeUpdate(sql2);
					return "errdb";
				}
				return "ok";
			}

			return "errdb";

		} catch (Exception e) {
			e.printStackTrace();
			return "errdb";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 群成员退群时 管理员收到消息后 回执消息 把该消息的状态置为已读
	public static String changeExitGroupMsgState(int msgid, String state) {

		Connection conn = null;
		Vector<String> groupusers = new Vector<String>();
		try {
			conn = DBManager.getConnection();
			String sql = "update requestfriendorgroup set state='" + state + "' where id='" + msgid + "'";

			int row = conn.createStatement().executeUpdate(sql);
			if (row <= 0) {
				return "errordatabase";
			}
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "errordatabase";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 删除好友时 被删除的一方收到被删除的消息时 要回执 修改数据库该消息的状态
	public static String changeDeleteFriendMsgState(int msgid, String state) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update requestfriendorgroup set state='" + state + "' where id='" + msgid + "'";

			int row = conn.createStatement().executeUpdate(sql);
			if (row <= 0) {
				return "errordatabase";
			}
			return "ok";

		} catch (Exception e) {
			e.printStackTrace();
			return "errordatabase";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 好友或群请求 收到消息后 更改消息的读取状态
	public static void changeRequestMsgState(int msgid, String state) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update requestfriendorgroup set state='" + state + "' where id='" + msgid + "'";

			conn.createStatement().executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 添加好友 还没有做好友分组
	// ***********************************************************************************
	public static String addfriend(String myphonenumber, String mynickname, String otherphonenumber,
			String othernickname, int friendgroup) {

		Connection conn = null;
		Long time = System.currentTimeMillis();
		try {
			conn = DBManager.getConnection();
			String sql = "INSERT INTO friends(myphonenumber,friendphonenumber, friendgroup,time) VALUES(?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, myphonenumber);
			pst.setString(2, otherphonenumber);
			pst.setInt(3, friendgroup);
			pst.setLong(4, time);

			int row = pst.executeUpdate();
			if (row <= 0) {

				return "errordatabase";
			}

			sql = "INSERT INTO friends(myphonenumber,friendphonenumber, friendgroup,time) VALUES(?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, otherphonenumber);
			pst.setString(2, myphonenumber);
			pst.setInt(3, friendgroup);
			pst.setLong(4, time);

			row = pst.executeUpdate();
			if (row <= 0) {
				// 失败的话 就要撤销
				sql = "delete from friends where myphonenumber='" + myphonenumber + "' and friendphonenumber='"
						+ otherphonenumber + "'";
				conn.createStatement().executeUpdate(sql);
				return "errordatabase";
			}
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "errordatabase";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 修改requestfriendorgroup表中信息的状态
	public static String changeRequestFriendOrGroupState(int msgid, String state) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update requestfriendorgroup set state='" + state + "' where id='" + msgid + "'";

			int row = conn.createStatement().executeUpdate(sql);
			if (row <= 0) {
				return "errordatabase";
			}
			return "ok";

		} catch (Exception e) {
			e.printStackTrace();
			return "errordatabase";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	// 加入社团群
	public static String addCorporationGroup(String phonenumber, String corporationname, String position) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select corporationname,corporationposition from users where phonenumber='" + phonenumber
					+ "'";
			ResultSet rSet = conn.createStatement().executeQuery(sql);
			if (rSet.next()) {
				String [] corporationnames = null;
				String corps = rSet.getString(1);
				if(corps==null) {
					corps = "";
				}
				
				corporationnames = corps.split(" ");
				 
				if(corporationnames!=null) {
					for(int i=0;i<corporationnames.length;++i) {
						if(corporationnames[i].equals(corporationname)) {
							return "ok";
						}
					}
				}
				corporationname = corps + " " + corporationname;
				String positions = rSet.getString(2);
				if(positions==null) {
					positions = "";
				}
				position =  positions+ " " + position;
				position = position.trim();
				corporationname = corporationname.trim();
				position = position.trim();

				String sql2 = "update users set corporationname='" + corporationname + "',corporationposition='"
						+ position + "' where phonenumber='" + phonenumber + "'";

				int row = conn.createStatement().executeUpdate(sql2);
				if (row <= 0) {
					//return "errordatabase";
					return "ok";
				}
				return "ok";

			}
			return "ok";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "errordatabase";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 处理表中该消息状态 修改的是对所有管理员的
	public static String changeAddGroupMsgState(int groupid, String senderid, String meph,String oldtype,String newtype, String state,String msg) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update requestfriendorgroup set state=?,type=?,msg=? where senderid=? and type=? and groupid=? and reciverid<>?";

			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, state);
			pStatement.setString(2, newtype);
			pStatement.setString(3, msg);
			
			pStatement.setString(4, senderid);
			pStatement.setString(5, oldtype);
			pStatement.setInt(6, groupid);
			pStatement.setString(7, meph);
			
			
			int row = pStatement.executeUpdate();
			if (row <= 0) {
				return "errordatabase";
			}
			return "ok";

		} catch (Exception e) {
			e.printStackTrace();
			return "errordatabase";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	// 添加好友 修改该消息的读取状态
	public static String changeAddFriendMsgState(int msgid, String state) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();

			String sql = "update requestfriendorgroup set state='" + state + "' where id='" + msgid + "'";
			int row = conn.createStatement().executeUpdate(sql);
			if (row <= 0) {
				return "errordatabase";
			}

			return "ok";

		} catch (Exception e) {
			e.printStackTrace();
			return "errordatabase";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 获得删除时 或者 添加好友时 验证信息
	public static Vector<ChatMsg> getRequestFriendOrGroup(String myphonenumber) {
		Connection connection = null;
		Vector<ChatMsg> users = new Vector<ChatMsg>();
		try {
			connection = DBManager.getConnection();

			String sql = "SELECT * FROM requestfriendorgroup WHERE reciverid ='" + myphonenumber + "' and state='未读'";
			ResultSet rSet = connection.createStatement().executeQuery(sql);
			while (rSet.next()) {
				ChatMsg user = new ChatMsg();
				user.setMsgid(rSet.getInt(1));
				user.setSenderid(rSet.getString(2));
				user.setSendername(rSet.getString(3));
				user.setSendersex(rSet.getString(4));
				user.setSendericon(rSet.getString(5));
				user.setType(rSet.getString(6));
				user.setReceiverid(rSet.getString(7));
				user.setMsgtime(rSet.getLong(8));
				user.setMsgbody(rSet.getString(9));
				user.setGroupid(rSet.getInt(11));

				users.add(0,user);
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

	// 删除好友 互删
	public static String deleteFriend(String myphonenumber, String friendphonenumber) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			int row = conn.createStatement()
					.executeUpdate("DELETE FROM friends WHERE (myphonenumber='" + myphonenumber
							+ "' and friendphonenumber='" + friendphonenumber + "') or (myphonenumber='"
							+ friendphonenumber + "' and friendphonenumber='" + myphonenumber + "')  ");

			if (row < 0) {
				return "errordatabase";
			}

			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "errorother";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

}
