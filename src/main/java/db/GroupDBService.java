package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import object.GroupBasicInfo;
import object.UserGroup;
import object.UserInGroupInfo;
import util.User;

/*
 * 用户群的数据库操作
 */
public class GroupDBService {

	// 修改群头像
	public static String changeGroupIconBygid(int groupid, byte[] bs) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select groupid from groupicon where groupid=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, groupid);
			ResultSet rSet = pStatement.executeQuery();
			if (!rSet.next()) {
				sql = "insert into groupicon(groupid,icon) values(?,?)";
				pStatement = conn.prepareStatement(sql);
				pStatement.setInt(1, groupid);
				pStatement.setBytes(2, bs);
				int row = pStatement.executeUpdate();
				if (row > 0) {
					return "ok";
				}
			} else {
				sql = "update groupicon set icon=? where groupid=?";
				pStatement = conn.prepareStatement(sql);
				pStatement.setBytes(1, bs);
				pStatement.setInt(2, groupid);
				int row = pStatement.executeUpdate();
				if (row > 0) {
					return "ok";
				}
			}

			return "error";
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

	// 获取群的头像
	public static byte[] getGroupIcByGid(int groupid) {

		Connection conn = null;
		byte[] bs = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select icon from groupicon where groupid=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, groupid);

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

	// 更新群人数
	public static void upGroupUsernum(int groupid, int groupusernum) {
		Connection conn = null;
		byte[] bs = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update usergroup set number=? where groupid=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, groupusernum);
			pStatement.setInt(2, groupid);
			pStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 拿到该群所有人的账号 不包括发送者 给每个人发送该信息
	// 此处一般用于某人在某群发送信息时
	public static Vector<String> getGroupUsersExceptPhonenumber(String phonenumber, int groupid) {

		Connection conn = null;
		Vector<String> groupusers = new Vector<String>();
		try {
			conn = DBManager.getConnection();
			String sql = "select userid from usertogroup where groupid='" + groupid + "' and userid<>'" + phonenumber
					+ "'";
			ResultSet rSet = conn.createStatement().executeQuery(sql);
			while (rSet.next()) {

				groupusers.add(rSet.getString(1));
			}
			return groupusers;
		} catch (Exception e) {
			e.printStackTrace();
			return groupusers;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 获得多名成员各自的群名片
	public static Vector<String> getGroupNamesByPhs(int groupid, Vector<String> phs) {
		Connection conn = null;
		Vector<String> names = new Vector<String>();
		try {
			conn = DBManager.getConnection();
			String sql = "select groupnickname from usertogroup where userid=? and groupid=?";
			for (int i = 0; i < phs.size(); ++i) {
				String ph = phs.get(i);
				PreparedStatement pStatement = conn.prepareStatement(sql);
				pStatement.setString(1, ph);
				pStatement.setInt(2, groupid);
				ResultSet resultSet = pStatement.executeQuery();
				if (resultSet.next()) {
					names.add(resultSet.getString(1));
				}
			}

			return names;

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

	// 获得某人在某群中的昵称
	public static String getGroupNameByph(String ph, int groupid) {
		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			String sql = "select groupnickname from usertogroup where userid='" + ph + "' and groupid=" + groupid;
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				String name = resultSet.getString(1);
				return name;
			}
			return null;
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

	// 修改自己的群名片
	public static String changeGroupRemark(String ph, int groupid, String remark) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update usertogroup set groupnickname='" + remark + "' where userid = '" + ph
					+ "' and groupid='" + groupid + "'";
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

	// 创建群 创建出来的普通群
	public static int createGroup(String phonenumber, String groupname) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			long time = System.currentTimeMillis();
			String sql = "insert into usergroup(groupname,createtime,creatorid) values(?,?,?)";

			PreparedStatement pStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pStatement.setString(1, groupname);
			pStatement.setLong(2, time);
			pStatement.setString(3, phonenumber);
			int row = pStatement.executeUpdate();

			if (row <= 0) {
				return -1;
			}

			ResultSet rs = pStatement.getGeneratedKeys();
			int id = -1;
			if (rs.next()) {
				id = rs.getInt(1);
				String sql2 = "insert into usertogroup(userid,groupid,jointime,groupnickname,userauthority) values(?,?,?,?,?)";
				PreparedStatement pStatement2 = conn.prepareStatement(sql2);
				String sql3 = "select nickname from users where phonenumber='" + phonenumber + "'";
				ResultSet resultSet = conn.createStatement().executeQuery(sql3);
				if (resultSet.next()) {
					String name = resultSet.getString(1);
					pStatement2.setString(1, phonenumber);
					pStatement2.setInt(2, id);
					pStatement2.setLong(3, time);
					pStatement2.setString(4, name);
					pStatement2.setString(5, "群主");
					int row1 = pStatement2.executeUpdate();
					if (row1 > 0) {
						return id;
					} else {
						// 撤销
						sql = "delete from usergroup where groupid='" + id + "'";
						conn.createStatement().executeUpdate(sql);
						return -1;
					}
				} else {
					// 撤销
					sql = "delete from usergroup where groupid='" + id + "'";
					conn.createStatement().executeUpdate(sql);
					return -1;
				}
			}
			return -1;
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

	// 添加至表usergroup 群的基本信息 创建群 刚开始默认头像 这个还没写
	// ******************************************************************************
	public static String Addusergroup(String groupid, String groupname, String groupicon, String groupintro,
			long createtime, String creatorid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "INSERT INTO usergroup(groupid,groupname,groupicon,groupintro,createtime,creatorid) VALUES(?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, groupid);
			pst.setString(2, groupname);
			pst.setString(3, groupicon);
			pst.setString(4, groupintro);
			pst.setLong(5, createtime);
			pst.setString(6, creatorid);

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

	// 添加至表usergroupannouncement 群公告
	public static String Addusergroupannouncement(String groupid, String msg, String msgtime) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "INSERT INTO usergroupannouncement(groupid,msg, msgtime) VALUES(?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, groupid);
			pst.setString(2, msg);
			pst.setString(3, msgtime);

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

	// 添加至表usertogroup 用户对群的依赖关系 ： 用户所在群号 加群时间 群昵称 群权限
	public static String Addusertogroup(String userid, int groupid, long jointime, String groupnickname,
			String userauthority) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();

			String sql = "select * from usertogroup where userid=? and groupid=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, userid);
			pStatement.setInt(2, groupid);

			ResultSet rSet = pStatement.executeQuery();
			if (rSet.next()) {
				return "ok";
			} else {
				sql = "update usergroup set number=number+1 where groupid=?";
				pStatement = conn.prepareStatement(sql);
				pStatement.setInt(1, groupid);
				int row = pStatement.executeUpdate();
				if (row > 0) {
					sql = "INSERT INTO usertogroup(userid,groupid,jointime, groupnickname,userauthority) VALUES(?,?,?,?,?)";
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setString(1, userid);
					pst.setInt(2, groupid);
					pst.setLong(3, jointime);
					pst.setString(4, groupnickname);
					pst.setString(5, userauthority);
					row = pst.executeUpdate();
					if (row <= 0) {
						sql = "update errordatabase set number=number-1";
						conn.createStatement().executeUpdate(sql);
						return "errordatabase";
					}
					return "ok";
				} else {
					sql = "update usergroup set number=number-1 where groupid=?";
					pStatement = conn.prepareStatement(sql);
					pStatement.setInt(1, groupid);
					pStatement.executeUpdate();
				}
			}

			return "errordatabase";
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

	// 获得某用户的某个群的信息
	public static UserGroup getUsergroupInfoByGroupid(String ph, int groupid) {

		Connection conn = null;
		try {

			conn = DBManager.getConnection();
			String sql = "select * from usertogroup where userid='" + ph + "' and groupid='" + groupid + "'";
			ResultSet rs = conn.createStatement().executeQuery(sql);

			if (rs.next()) {
				UserGroup s = new UserGroup();
				s.groupid = (rs.getInt(3));
				s.jointime = (rs.getLong(4));
				s.groupnickname = (rs.getString(5));
				s.userauthority = (rs.getString(6));
				s.corppos = rs.getString(7);

				String sql2 = "select * from usergroup where groupid='" + groupid + "'";
				ResultSet rs2 = conn.createStatement().executeQuery(sql2);
				if (rs2.next()) {
					s.groupname = (rs2.getString(2));
					s.groupicon = (rs2.getString(3));
					s.groupintro = (rs2.getString(4));
					s.createtime = (rs2.getLong(5));
					s.creatorid = (rs2.getString(6));
					s.grouptype = (rs2.getString(7));

					String corppart = rs2.getString(8);
					if (corppart != null) {
						s.corppart = corppart.split(" ");
					}

					return s;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 获得某群的所有管理员包括群主
	public static Vector<String> getGroupAdministrators(int groupid) {

		Connection conn = null;
		Vector<String> groupusers = new Vector<String>();
		try {
			conn = DBManager.getConnection();
			String sql = "select userid from usertogroup where groupid='" + groupid
					+ "' and (userauthority='管理员' or userauthority='群主')";

			ResultSet rSet = conn.createStatement().executeQuery(sql);

			while (rSet.next()) {

				groupusers.add(rSet.getString(1));
			}
			return groupusers;
		} catch (Exception e) {
			e.printStackTrace();
			return groupusers;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 获得某群的所有管理员包括群主以及社团群的部长
	public static Vector<String> getGroupAdministratorsAndBuzhang(int groupid) {

		Connection conn = null;
		Vector<String> groupusers = new Vector<String>();
		try {
			conn = DBManager.getConnection();
			String sql = "select userid from usertogroup where groupid='" + groupid
					+ "' and (userauthority='管理员' or userauthority='群主' or corppos='主席' or corppos='部长' or corppos='官方账号')";

			ResultSet rSet = conn.createStatement().executeQuery(sql);

			while (rSet.next()) {

				groupusers.add(rSet.getString(1));
			}
			return groupusers;
		} catch (Exception e) {
			e.printStackTrace();
			return groupusers;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 获得某群的基本信息
	public static GroupBasicInfo getGroupInfoByGroupId(int otherid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select * from usergroup where groupid=" + otherid;
			ResultSet rs = conn.createStatement().executeQuery(sql);
			GroupBasicInfo s = new GroupBasicInfo();

			if (rs.next()) {
				s.setGroupid(rs.getInt(1));
				s.setGroupname(rs.getString(2));
				s.setGroupicon(rs.getString(3));
				s.setGroupintro(rs.getString(4));
				s.setCreatetime(rs.getLong(5));
				s.setCreatorid(rs.getString(6));
				s.setGrouptype(rs.getString(7));
				s.setUsernum(rs.getInt(12));

				return s;
			}

			return null;

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

	// 获得某群的所有成员
	public static Vector<String> getAllUsersInGroupByGroupid(int groupid) {

		Connection conn = null;
		Vector<String> strings = new Vector<String>();
		try {
			conn = DBManager.getConnection();
			String sql = "select userid from usertogroup where groupid='" + groupid + "'";

			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			while (resultSet.next()) {
				strings.add(resultSet.getString(1));
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

	// 获得某群所有成员的详细信息
	public static Vector<User> getAllUsersInfoInGroupByGroupid(int groupid) {

		Connection conn = null;
		Vector<User> users = new Vector<User>();
		try {
			conn = DBManager.getConnection();
			String sql = "select userid from usertogroup where groupid='" + groupid + "'";

			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			while (resultSet.next()) {
				String ph = resultSet.getString(1);
				sql = "select * from users where phonenumber='" + ph + "'";
				ResultSet rs = conn.createStatement().executeQuery(sql);

				if (rs.next()) {

					User s = new User();
					s.setPhonenumber(rs.getString(1));
					s.setNickname(rs.getString(2));
					s.setPassword(rs.getString(3));
					// s.setIcon(rs.getString(4));
					s.setQq(rs.getString(4));
					s.setWeixin(rs.getString(5));
					s.setAddress(rs.getString(6));
					s.setSex(rs.getString(7));
					s.setSchoolname(rs.getString(8));
					s.setDepartmentname(rs.getString(9));
					s.setMajorname(rs.getString(10));
					// s.setState(rs.getString(11));
					s.setCorporationname(rs.getString(12));
					s.setCorporationposition(rs.getString(13));
					s.setBirthday(rs.getString(14));
					s.setRuxueyear(rs.getInt(15));
					s.setFrom(rs.getString(16));
					s.setIntroduce(rs.getString(17));

					users.add(s);
				}
			}
			return users;

		} catch (Exception e) {
			e.printStackTrace();
			return users;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 获得某群所有成员的详细信息
	public static Vector<UserInGroupInfo> getAllUsersByGroupid(int groupid) {

		Connection conn = null;
		Vector<UserInGroupInfo> users = new Vector<UserInGroupInfo>();
		try {
			conn = DBManager.getConnection();

			String sql = "select * from usertogroup where groupid=" + groupid;
			ResultSet rs = conn.createStatement().executeQuery(sql);

			while (rs.next()) {
				UserInGroupInfo userInGroupInfo = new UserInGroupInfo();
				userInGroupInfo.setGroupid(rs.getInt(3));
				userInGroupInfo.setPh(rs.getString(2));
				userInGroupInfo.setGroupnickname(rs.getString(5));
				userInGroupInfo.setAuth(rs.getString(6));
				userInGroupInfo.setCorppart(rs.getString(7));
				userInGroupInfo.setCorpos(rs.getString(8));

				users.add(userInGroupInfo);
			}

			return users;

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

	// 获得某群所有成员的账号
	public static Vector<String> getAllUsersPhByGroupid(int groupid) {

		Connection conn = null;
		Vector<String> phs = new Vector<String>();
		try {
			conn = DBManager.getConnection();

			String sql = "select userid from usertogroup where groupid=" + groupid;
			ResultSet rs = conn.createStatement().executeQuery(sql);

			while (rs.next()) {
				String ph = rs.getString(1);
				phs.add(ph);
			}

			return phs;

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

	// 获得某用户的群的基本信息
	public static Vector<UserGroup> getGroupsInfoOfUser(String phonenumber) {

		Connection conn = null;
		Vector<UserGroup> userGroups = new Vector<UserGroup>();
		try {

			conn = DBManager.getConnection();
			String sql = "select * from usertogroup where userid='" + phonenumber + "'";
			ResultSet rs = conn.createStatement().executeQuery(sql);

			while (rs.next()) {
				UserGroup s = new UserGroup();
				int groupid = rs.getInt(3);
				s.groupid = groupid;
				s.jointime = (rs.getLong(4));
				s.groupnickname = (rs.getString(5));
				s.userauthority = (rs.getString(6));
				s.part = rs.getString(7);
				s.corppos = rs.getString(8);

				String sql2 = "select * from usergroup where groupid='" + groupid + "'";
				ResultSet rs2 = conn.createStatement().executeQuery(sql2);
				if (rs2.next()) {
					s.groupname = (rs2.getString(2));
					s.groupicon = (rs2.getString(3));
					s.groupintro = (rs2.getString(4));
					s.createtime = (rs2.getLong(5));
					s.creatorid = (rs2.getString(6));
					s.grouptype = (rs2.getString(7));
					String corppart = rs2.getString(8);

					if (corppart != null) {
						s.corppart = corppart.split(" ");
						if (s.corppart.length == 1 && s.corppart[0].equals("")) {
							s.corppart = null;
						}
					}

					s.year = rs2.getInt(9);
					s.xueqi = rs2.getInt(10);
					s.zhou = rs2.getInt(11);
					s.usernum = rs2.getInt(12);
					s.authid = rs2.getInt(13);
					userGroups.add(s);
				}
			}
			return userGroups;

		} catch (Exception e) {
			e.printStackTrace();
			return userGroups;
		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 获得用户的某群的信息
	public static UserGroup getGroupInfoOfGid(String phonenumber,int groupid) {

		Connection conn = null;
		try {

			conn = DBManager.getConnection();
			String sql = "select * from usertogroup where userid=? and groupid=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, phonenumber);
			pStatement.setInt(2, groupid);
			ResultSet rs = pStatement.executeQuery();
			if (rs.next()) {
				UserGroup s = new UserGroup();
				s.groupid = groupid;
				s.jointime = (rs.getLong(4));
				s.groupnickname = (rs.getString(5));
				s.userauthority = (rs.getString(6));
				s.part = rs.getString(7);
				s.corppos = rs.getString(8);

				String sql2 = "select * from usergroup where groupid='" + groupid + "'";
				ResultSet rs2 = conn.createStatement().executeQuery(sql2);
				if (rs2.next()) {
					s.groupname = (rs2.getString(2));
					s.groupicon = (rs2.getString(3));
					s.groupintro = (rs2.getString(4));
					s.createtime = (rs2.getLong(5));
					s.creatorid = (rs2.getString(6));
					s.grouptype = (rs2.getString(7));
					String corppart = rs2.getString(8);

					if (corppart != null) {
						s.corppart = corppart.split(" ");
						if (s.corppart.length == 1 && s.corppart[0].equals("")) {
							s.corppart = null;
						}
					}

					s.year = rs2.getInt(9);
					s.xueqi = rs2.getInt(10);
					s.zhou = rs2.getInt(11);
					s.usernum = rs2.getInt(12);
					s.authid = rs2.getInt(13);
				}else {
					return null;
				}
				
				return s;
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 某用户是否在某群中
	public static boolean isPhJoinGroup(String ph, int groupid) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select id from usertogroup where userid='" + ph + "' and groupid=" + groupid;
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
