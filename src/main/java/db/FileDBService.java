package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * 文件的数据库操作
 */
public class FileDBService {

	// 图片或语音插入filemsg表中
	public static int addFileMsg(byte[] bs2) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "insert into filemsg(filebody) values(?)";
			PreparedStatement pStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pStatement.setBytes(1, bs2);
			pStatement.executeUpdate();
			int id = -1;
			ResultSet resultSet = pStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 获得filemsg表中的文件
	public static byte[] getFileMsgByFileid(int id) {

		Connection conn = null;
		byte[] bs = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select filebody from filemsg where id='" + id + "'";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				bs = resultSet.getBytes(1);
			}
			return bs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 从 friendtofriendmessage 获得某文件消息的fileid
	public static int getFileidFromfriendtofriendmessageByMsgid(int msgid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select fileid from friendtofriendmessage where id='" + msgid + "'";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			ResultSet resultSet = pStatement.executeQuery();
			int id = -1;
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

}
