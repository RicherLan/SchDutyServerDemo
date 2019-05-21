package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.print.DocFlavor.STRING;
import javax.print.attribute.ResolutionSyntax;

import object.PersonalInfo;
import util.User;

/*
 * 对用户信息的数据库操作
 */
public class UserDBService {

	// 修改密码
	public static String changePassword(String phonenumber, String oldpassword, String newpassword) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select password from users where phonenumber='" + phonenumber + "'";

			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				String mypassword = resultSet.getString(1);
				if (!mypassword.equals(oldpassword)) {
					return "erropwd";
				}
				sql = "update users set password='" + newpassword + "' where phonenumber='" + phonenumber + "'";
				int row = conn.createStatement().executeUpdate(sql);
				if (row <= 0) {
					return "errdb";
				}
				return "ok";
			} else {
				return "errph";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "errdb";
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 登陆 成功的话 修改其状态为 在线
	// 里面有个地方还留白了 *****************************************************************
	public static String login(String phonenumber, String password) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT nickname,password,state FROM users where phonenumber='" + phonenumber + "'");

			if (rs.next()) {
				String nickname = rs.getString(1);
				String passwd = rs.getString(2);
				String state = rs.getString(3);
				if (!passwd.equals(password)) {
					return "errpwd";
				}else {

					int rs2 = st.executeUpdate("update  users set state='在线' where phonenumber='" + phonenumber + "'");
					if (rs2 > 0) {
						return "ok "+nickname;
					} else {
						return "errdb";
					}

				}

			} else {
				return "erruname";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "errorothers";
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 某用户离线
	public static String loginout(String phonenumber) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			Statement st = conn.createStatement();
			int rs = st.executeUpdate("update  users set state='离线' where phonenumber='" + phonenumber + "'");

			if (rs <= 0) {
				return "errordatabase";

			} else {
				return "ok";

			}

		} catch (Exception e) {
			e.printStackTrace();
			return "errorother";
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 注册用户，测试
	public static String regisUsertest(String schoolname, String collegename, String majorname, int ruxueyear,
			String phonenumber, String password) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String string = "select phonenumber from users where phonenumber='" + phonenumber + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(string);
			if (resultSet.next()) {
				return "hasregisted";
			}
			
			 
			
			String sql = "INSERT INTO users(phonenumber,password,schoolname,departmentname,majorname,ruxueyear) VALUES(?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, phonenumber);
			pst.setString(2, password);
			pst.setString(3, schoolname);
			pst.setString(4, collegename);
			pst.setString(5, majorname);
			pst.setInt(6, ruxueyear);

			int row = pst.executeUpdate();
			if (row <= 0) {
				return "errdb";
			}

			return "ok";

		} catch (Exception a) {
			a.printStackTrace();
			return "errorother";

		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 注册用户
	public static String AddUser(String schoolname, String departmentname, String majorname, String nickname,
			String phonenumber, String password, String type) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "insert into users(phonenumber,nickname,password,schoolname,departmentname,majorname,type) VALUES(?,?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, phonenumber);
			pst.setString(2, nickname);
			pst.setString(3, password);
			pst.setString(4, schoolname);
			pst.setString(5, departmentname);
			pst.setString(6, majorname);
			pst.setString(7, type);

			int row = pst.executeUpdate();
			if (row <= 0) {
				return "errordatabase";
			}

			return "ok";

		} catch (Exception a) {
			a.printStackTrace();
			return "errorother";

		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 用户注册账号时 判断提交的手机号是否已经被注册

	public static String isPhonenumberRegisted(String phonenumber) {
		Connection conn = null;
		try {

			conn = DBManager.getConnection();

			String sql = "select * from users where phonenumber='" + phonenumber + "'";
			ResultSet rSet = conn.createStatement().executeQuery(sql);

			if (rSet.next()) {
				return "hasregisted";
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

	// 获得用户的头像
	/*
	 * public static String getIconByPhonenumber(String phonenumber) {
	 * 
	 * 
	 * Connection conn = null; try { conn = DBManager.getConnection(); long time =
	 * System.currentTimeMillis()/1000; String sql =
	 * "select icon from users where phonenumber = '"+phonenumber+"'"; ResultSet
	 * resultSet = conn.createStatement().executeQuery(sql); if(resultSet.next()) {
	 * return resultSet.getString(1); }
	 * 
	 * return null;
	 * 
	 * }catch (Exception e) { e.printStackTrace(); return null; } finally { try {
	 * conn.close(); } catch (Exception e) { } }
	 * 
	 * }
	 */

	// 获得个人信息
	public static PersonalInfo getPersonalInfo(String phonenumber) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select * from users where phonenumber=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, phonenumber);
			ResultSet rs = pStatement.executeQuery();

			if (rs.next()) {

				PersonalInfo s = new PersonalInfo();

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
				s.setType(rs.getString(18));
				s.setXueqi(rs.getInt(19));
				s.setZhou(rs.getInt(20));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 获得某用户信息
	public static User getUserInfoByPhonenumber(String phonenumber) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select * from users where phonenumber='" + phonenumber + "'";
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
				s.setType(rs.getString(18));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 更新用户的当前周 +1
	public static String updateUserZhou() {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update user set zhou=zhou+1";
			// PreparedStatement pStatement = conn.prepareStatement(sql);
			// pStatement.setString(1, "社团群");
			Statement statement = conn.createStatement();
			if (statement != null) {
				int row = statement.executeUpdate(sql);
				if (row > 0) {
					return "ok";
				}
				return "error";
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

	// 获得某用户自己的学校
	public static String getSchoolNameOfph(String phonenumber) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select schoolname from users where phonenumber='" + phonenumber + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				String schoolname = resultSet.getString(1);
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

	// 获得某用户的网名
	public static String getNicknameOfPhonenumber(String phonenumber) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select nickname from users where phonenumber = '" + phonenumber + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				return resultSet.getString(1);
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

	// 删除
	public static boolean deleteStudent(String phonenumber) throws Exception {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "delete from users where phonenumber='" + phonenumber + "'";
			int row = conn.createStatement().executeUpdate(sql);
			// 删除失败
			if (row <= 0) {
				return false;
			}

			return true;
		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 修改用户头像
	public static String changeIconByPh(String ph, byte[] bs) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "delete from usericon where phonenumber='" + ph + "'";
			conn.createStatement().executeUpdate(sql);

			sql = "insert into usericon(phonenumber,icon) values(?,?)";
			PreparedStatement pStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pStatement.setString(1, ph);
			pStatement.setBytes(2, bs);
			int row = pStatement.executeUpdate();
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

	// 获取用户的头像
	public static byte[] getIconByPh(String ph) {

		Connection conn = null;
		byte[] bs = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select icon from usericon where phonenumber=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, ph);
			
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

	// 修改个人资料
	public static String changePersonalInfo(String phonenumber, String ni, String sex, String from, String add,
			String sch, String dep, String maj, String bir, int rxy, String info) {
		Connection conn = null;
		byte[] bs = null;
		try {
			conn = DBManager.getConnection();
			// String sql = "update users set
			// nickname=?,sex=?,fromwhere=?,address=?,schoolname=?,departmentname=?,majorname=?,birthday=?,ruxueyear=?
			// where phonenumber='"+phonenumber+"'";
			String sql = "update users set nickname=?,sex=?,fromwher=?,address=?,schoolname=?,departmentname=?,majorname=?,birthday=?,ruxueyear=?,info=? where phonenumber=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);

			pStatement.setString(1, ni);
			pStatement.setString(2, sex);
			pStatement.setString(3, from);
			pStatement.setString(4, add);
			pStatement.setString(5, sch);
			pStatement.setString(6, dep);
			pStatement.setString(7, maj);
			pStatement.setString(8, bir);
			pStatement.setInt(9, rxy);
			pStatement.setString(10, info);
			pStatement.setString(11, phonenumber);

			int row = pStatement.executeUpdate();
			if (row > 0) {
				
				return "ok";
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

}
