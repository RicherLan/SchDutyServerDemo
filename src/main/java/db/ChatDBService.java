package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import object.ChatMsg;

/*
 * 聊天的数据库操作
 */
public class ChatDBService {

	// 拿到单人聊天 未读的所欲信息 包括图片语音 一般是刚登陆的时候
	public static Vector<ChatMsg> getSingleChatMsgNotRead(String phonenumber) {

		Connection conn = null;
		Vector<ChatMsg> strings = new Vector<ChatMsg>();
		try {
			conn = DBManager.getConnection();
			String sql = "select * from friendtofriendmessage where reciverid='" + phonenumber + "' and msgstate='未读'";

			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			while (resultSet.next()) {
				ChatMsg singleChatMsg = new ChatMsg();
				singleChatMsg.setMsgid(resultSet.getInt(1));
				String senderid = resultSet.getString(2);
				singleChatMsg.setSenderid(senderid);
				// 是自己好友的话 名字就写备注 陌生人的话 名字写他的网名
				sql = "select friendnickname from friends where myphonenumber='" + phonenumber
						+ "' and friendphonenumber='" + senderid + "'";
				ResultSet resultSet2 = conn.createStatement().executeQuery(sql);
				if (resultSet2.next()) {
					singleChatMsg.setSendername(resultSet2.getString(1));
				} else {
					sql = "select nickname from users where phonenumber='" + resultSet.getString(2) + "'";
					ResultSet resultSet3 = conn.createStatement().executeQuery(sql);
					if (resultSet3.next()) {
						singleChatMsg.setSendername(resultSet3.getString(1));
					}
				}
				singleChatMsg.setReceiverid(resultSet.getString(3));
				singleChatMsg.setMsgbody(resultSet.getString(4));
				singleChatMsg.setMsgtype(resultSet.getString(5));
				singleChatMsg.setMsgtime(resultSet.getLong(6));
				singleChatMsg.setVoicetime(resultSet.getFloat(8));
				singleChatMsg.setType("single");

				strings.add(singleChatMsg);
			}
			return strings;

		} catch (Exception e) {
			e.printStackTrace();
			return strings;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 拿到群聊天 未读信息 一般是刚登陆的时候
	public static Vector<ChatMsg> getGroupChatMsgNotRead(String phonenumber) {

		Connection conn = null;
		Vector<ChatMsg> strings = new Vector<ChatMsg>();
		try {
			conn = DBManager.getConnection();
			String sql = "select * from groupmsgtouser where userid='" + phonenumber + "' and msgstate='未读'";

			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			while (resultSet.next()) {
				int msgid1 = resultSet.getInt(1);
				int msgid2 = resultSet.getInt(3);
				int groupid = resultSet.getInt(2);
				sql = "select * from groupmsgcontent where id='" + msgid2 + "'";
				ResultSet resultSet2 = conn.createStatement().executeQuery(sql);
				if (resultSet2.next()) {
					String senderid = resultSet2.getString(3);
					String sendergroupname = resultSet2.getString(4);
					String msg = resultSet2.getString(5);
					String msgtype = resultSet2.getString(6);
					long msgtime = resultSet2.getLong(7);
					float voicetime = resultSet2.getFloat(8);

					sql = "select groupnickname from usertogroup where userid='" + phonenumber + "'";
					ResultSet resultSet3 = conn.createStatement().executeQuery(sql);
					if (resultSet3.next()) {

						String receivername = resultSet3.getString(1);
						ChatMsg singleChatMsg = new ChatMsg();
						singleChatMsg.setMsgid(msgid1);
						singleChatMsg.setGroupid(groupid);
						singleChatMsg.setSenderid(senderid);
						singleChatMsg.setSendername(sendergroupname);
						singleChatMsg.setReceiverid(sendergroupname);
						singleChatMsg.setMsgbody(msg);
						singleChatMsg.setMsgtype(msgtype);
						singleChatMsg.setMsgtime(msgtime);
						singleChatMsg.setVoicetime(voicetime);
						singleChatMsg.setType("group");
						strings.add(singleChatMsg);
					}

				}

			}
			return strings;

		} catch (Exception e) {
			e.printStackTrace();
			return strings;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 添加至表friendtofriendmessage
	public static int Addfriendtofriendmessage(String senderid, String reciverid, String msg, String msgtype,
			long msgtime, String msgstate, double voicetime, int fileid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "INSERT INTO friendtofriendmessage(senderid,reciverid,msg,msgtype,msgtime,"
					+ "msgstate,voicetime,fileid) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, senderid);
			pst.setString(2, reciverid);
			pst.setString(3, msg);
			pst.setString(4, msgtype);
			pst.setLong(5, msgtime);
			pst.setString(6, msgstate);
			pst.setDouble(7, voicetime);
			pst.setInt(8, fileid);

			int row = pst.executeUpdate();
			if (row < 0) {
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

	// 某用户在群里聊天
	// 添加至表groupmsgcontent 返回该消息在数据库群消息记录的id
	// 返回-1是失败 那么该用户的发言没有发出去
	public static int Addgroupmsgcontent(int groupid, String senderid, String sendergroupname, String msg,
			String msgtype, long msgtime, double voicetime, int fileid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "INSERT INTO groupmsgcontent(groupid,senderid,sendergroupname,msg,msgtype,msgtime,voicetime,fileid) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, groupid);
			pst.setString(2, senderid);
			pst.setString(3, sendergroupname);
			pst.setString(4, msg);
			pst.setString(5, msgtype);
			pst.setLong(6, msgtime);
			pst.setDouble(7, voicetime);
			pst.setInt(8, fileid);

			int row = pst.executeUpdate();
			if (row < 0) {
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

	// 添加至表groupmsgusertouser
	public static String Addgroupmsgusertouser(String groupid, String senderid, String sendernickname, String reciverid,
			String recivername, String msg, String msgtype, String msgtime, String msgstate) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "INSERT INTO groupmsgusertouser(groupid,senderid,sendernickname,reciverid,recivername,msg,msgtype,msgtime"
					+ "msgstate) VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, groupid);
			pst.setString(2, senderid);
			pst.setString(3, sendernickname);
			pst.setString(4, reciverid);
			pst.setString(5, recivername);
			pst.setString(6, msg);
			pst.setString(7, msgtype);
			pst.setString(8, msgtime);
			pst.setString(9, msgstate);

			int row = pst.executeUpdate();
			if (row <= 0) {
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
				e.printStackTrace();
			}
		}
	}

	// 添加至表groupmsgtouser
	public static int Addgroupmsgtouser(int groupid, int msgid, String userid, String msgstate) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "INSERT INTO groupmsgtouser(groupid,msgid,userid,msgstate) VALUES(?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, groupid);
			pst.setInt(2, msgid);
			pst.setString(3, userid);
			pst.setString(4, msgstate);

			int row = pst.executeUpdate();
			if (row < 0) {
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

	// 单人聊天时 接收方收到消息后 会给服务器把该消息的id发送回来
	// 这样说明该消息已经被读取 同时修改数据库所有消息的状态为已读
	public static String changeFriendToFriendMessageState(String senderid, String reciverid, int msgid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update friendtofriendmessage set msgstate='已读' where senderid='" + senderid
					+ "' and reciverid='" + reciverid + "' and msgstate='未读' and id<='" + msgid + "'";
			int row = conn.createStatement().executeUpdate(sql);
			if (row <= 0) {
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
				e.printStackTrace();
			}
		}

	}

	

	// 群聊 接收方接收消息后 回执该消息已读
	// 将数据库中该用户的所有该群消息编号小于该消息编号的消息状态都置为已读
	public static String changeGroupMsgToUserMessageState(String reciverid, int msgid, int groupid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update groupmsgtouser set msgstate='已读' where userid='" + reciverid + "' and groupid='"
					+ groupid + "' and msgstate='未读' and id<='" + msgid + "'";
			int row = conn.createStatement().executeUpdate(sql);
			if (row <= 0) {
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
				e.printStackTrace();
			}
		}

	}

}
