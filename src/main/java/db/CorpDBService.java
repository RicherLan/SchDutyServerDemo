package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.management.relation.RelationTypeNotFoundException;
import javax.print.attribute.ResolutionSyntax;

import org.apache.commons.collections.functors.ForClosure;
import org.apache.commons.collections.map.StaticBucketMap;

import util.MyTools;

/*
 * 社团组织下相关的数据库操作
 */
public class CorpDBService {

	public static String alterCorpTerm(int groupid, int year, int xueqi, int zhou) {

		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			String sql = "update usergroup set year=?,xueqi=?,zhou=? where groupid=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, year);
			preparedStatement.setInt(2, xueqi);
			preparedStatement.setInt(3, zhou);
			preparedStatement.setInt(4, groupid);
			int row = preparedStatement.executeUpdate();
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

	// 添加社团组织的某一个部门
	public static String addCorpPart(int groupid, String name) {

		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			String sql = "select corppart from usergroup where groupid=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, groupid);
			ResultSet resultSet = pStatement.executeQuery();
			String ss = "";
			if (resultSet.next()) {
				ss = resultSet.getString(1);
				if(ss==null) {
					ss="";
				}
				String[] strings = ss.split(" ");
				for (String part : strings) {
					if (part.equals(name)) {
						return "hasexist";
					}
				}
			} else {
				return "error";
			}

			ss += " " + name;
			ss = ss.trim();

			sql = "update usergroup set corppart=? where groupid=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
				
			preparedStatement.setString(1, ss);
			preparedStatement.setInt(2, groupid);
			int row = preparedStatement.executeUpdate();
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

	// 删除社团组织的某一个部门
	// 同时设置社团的该部门的成员的部门为空  
	public static String deleteCorpPart(int groupid, String name) {

		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			String sql = "select corppart from usergroup where groupid=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, groupid);
			ResultSet resultSet = pStatement.executeQuery();
			String ss = "";
			String string = "";
			boolean flag = false;
			if (resultSet.next()) {
				ss = resultSet.getString(1).trim();
				String[] strings = ss.split(" ");
				for (String part : strings) {
					if (part.equals(name)) {
						flag = true;
					} else {
						string += part + " ";
					}
				}
			} else {
				return "error";
			}

			if (!flag) {
				return "notexist";
			}

			string = string.trim();

			sql = "update usergroup set corppart=? where groupid=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, string);
			preparedStatement.setInt(2, groupid);
			int row = preparedStatement.executeUpdate();
			if (row > 0) {
				sql = "update usertogroup set part=? where groupid=? and part=?";
				preparedStatement = conn.prepareStatement(sql);
					
				preparedStatement.setString(1, "");
				preparedStatement.setInt(2, groupid);
				preparedStatement.setString(3, name);
				preparedStatement.executeUpdate();
				
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

	// 修改社团组织的某一个部门的名字
	// 同时修改成员的部门名字
	public static String alterCorpPart(int groupid, String oldname, String newname) {

		Connection conn = null;

		try {
			conn = DBManager.getConnection();
			String sql = "select corppart from usergroup where groupid=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, groupid);

			ResultSet resultSet = pStatement.executeQuery();
			String ss = "";
			String string = "";
			boolean flag = false;
			if (resultSet.next()) {
				ss = resultSet.getString(1).trim();

				String[] strings = ss.split(" ");
				for (String part : strings) {
					if (part.equals(oldname)) {
						flag = true;
						string += newname + " ";
					} else {
						string += part + " ";
					}
				}
			} else {
				return "error";
			}

			if (!flag) {
				return "notexist";
			}

			string = string.trim();
			sql = "update usergroup set corppart=? where groupid=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, string);
			preparedStatement.setInt(2, groupid);
			int row = preparedStatement.executeUpdate();
			if (row > 0) {
				
				sql = "update usertogroup set part=? where groupid=? and part=?";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, newname);
				preparedStatement.setInt(2, groupid);
				preparedStatement.setString(3, oldname);
				preparedStatement.executeUpdate();
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

	// 更改自己的部室
	public static String changeGroupPart(String ph, int groupid, String part) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update usertogroup set part='" + part + "' where userid = '" + ph + "' and groupid='"
					+ groupid + "'";
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

	// 修改自己在社团的职位
	public static String alterCorpPos(String ph, int groupid, String pos) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			
			String sql = "update usertogroup set corppos=? where userid = ? and groupid=?";
			if(pos.equals("主席")){
				sql = "update usertogroup set part='',corppos=? where userid = ? and groupid=?";
			}
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, pos);
			pStatement.setString(2, ph);
			pStatement.setInt(3, groupid);

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

	// 设置某社团组织的某部门的所有成员的部门 并返回修改成功的成员的账号
	public static Vector<String> getPhOfCorpPart(int groupid, String partname, String newname) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select userid from usertogroup where groupid=? and part=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, groupid);
			pStatement.setString(2, partname);

			Vector<String> phStrings = new Vector<String>();
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {

				String ph = resultSet.getString(1);
				sql = "update usertogroup set part=? where groupid=? and userid=?";
				PreparedStatement pStatement2 = conn.prepareStatement(sql);
				pStatement2.setString(1, newname);
				pStatement2.setInt(2, groupid);
				pStatement2.setString(3, ph);

				int row = pStatement2.executeUpdate();
				if (row > 0) {
					phStrings.add(ph);
				}

			}

			return phStrings;

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

	// 设置某社团组织的某部门的所有成员的部门
	public static void setUsersCorpPartByPart(int groupid, String partname, String newname, Vector<String> phs) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();

			for (String ph : phs) {

				String sql = "update usertogroup set part=? where groupid=? and userid=?";
				PreparedStatement pStatement2 = conn.prepareStatement(sql);
				pStatement2.setString(1, newname);
				pStatement2.setInt(2, groupid);
				pStatement2.setString(3, ph);

				int row = pStatement2.executeUpdate();

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 更新社团组织的当前周 +1
	public static String updateCorpZhou() {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update usergroup set zhou=zhou+1 where grouptype=?";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, "社团群");
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

	// 获得多名成员各自在组织中的职位
	public static Vector<String> getCorppossByPhs(int groupid, Vector<String> phs) {
		Connection conn = null;
		Vector<String> poss = new Vector<String>();
		try {
			conn = DBManager.getConnection();
			String sql = "select corppos from usertogroup where userid=? and groupid=?";
			for (int i = 0; i < phs.size(); ++i) {
				String ph = phs.get(i);
				PreparedStatement pStatement = conn.prepareStatement(sql);
				pStatement.setString(1, ph);
				pStatement.setInt(2, groupid);

				ResultSet resultSet = pStatement.executeQuery();
				if (resultSet.next()) {
					poss.add(resultSet.getString(1));
				}
			}

			return poss;

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

	// 根据社团或组织的账号获得群号
	public static int getGroupidByCorpAccount(int count) {
		Connection conn = null;
		Vector<String> poss = new Vector<String>();
		try {
			conn = DBManager.getConnection();
			String sql = "select groupid from corporation where corpaccount='" + count + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				return resultSet.getInt(1);
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

	// 获得某人在某社团组织中的职位
	public static String getCorpPosByPh(String ph, int groupid) {
		Connection conn = null;

		try {

			conn = DBManager.getConnection();
			String sql = "select corppos from usertogroup where userid='" + ph + "' and groupid=" + groupid;
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

	// 创建社团
	// 分配群号 群主加入两个表 usergroup（群表） 和 usertogroup（用户与群的关系表）

	public static String createCorporation(String password,String creatorphonenumber, String corporationname, String corporationtype,
			String corporationinfo, String corppart, int year, int xueqi) {

		Connection conn = null;
		try {
			long createtime = System.currentTimeMillis();
			conn = DBManager.getConnection();

			long time = System.currentTimeMillis();

			String sql2 = "insert into usergroup(groupname,groupintro,createtime,creatorid,grouptype,corppart,year,xueqi) values(?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, corporationname);
			preparedStatement.setString(2, corporationinfo);

			preparedStatement.setLong(3, time);
			preparedStatement.setString(4, creatorphonenumber);
			preparedStatement.setString(5, "社团群");
			preparedStatement.setString(6, corppart);
			preparedStatement.setInt(7, year);
			preparedStatement.setInt(8, xueqi);

			int id = -1;
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getInt(1);

				String sql = "insert into corporation(groupid,corporationname,corporationtype,corporationinfo,creatorphonenumber,createtime,school) values(?,?,?,?,?,?,?)";
				PreparedStatement preparedStatement2 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement2.setInt(1, id);
				preparedStatement2.setString(2, corporationname);
				preparedStatement2.setString(3, corporationtype);
				preparedStatement2.setString(4, corporationinfo);
				preparedStatement2.setString(5, creatorphonenumber);
				preparedStatement2.setLong(6, time);
				sql = "select schoolname from users where phonenumber='" + creatorphonenumber + "'";
				ResultSet resultSet2 = conn.createStatement().executeQuery(sql);
				String schoolname = "";
				if (resultSet2.next()) {
					schoolname = resultSet2.getString(1);
					preparedStatement2.setString(7, schoolname);
				}

				int row = preparedStatement2.executeUpdate();
				if (row < 0) {
					return "errdb";
				}
				ResultSet rs = preparedStatement2.getGeneratedKeys();
				if (rs.next()) {
					int count = rs.getInt(1);

					sql = "update usergroup set authid=? where groupid=?";
					PreparedStatement pStatement = conn.prepareStatement(sql);
					pStatement.setInt(1, count);
					pStatement.setInt(2, id);
					pStatement.executeUpdate();

					sql = "insert into usertogroup(userid,groupid,jointime,groupnickname,userauthority,corppos) values(?,?,?,?,?,?)";
					PreparedStatement preparedStatement3 = conn.prepareStatement(sql);
					preparedStatement3.setString(1, creatorphonenumber);
					preparedStatement3.setInt(2, id);
					preparedStatement3.setLong(3, time);
					String name = UserDBService.getNicknameOfPhonenumber(creatorphonenumber);
					preparedStatement3.setString(4, name);
					preparedStatement3.setString(5, "群主");
					preparedStatement3.setString(6, "主席");

					row = preparedStatement3.executeUpdate();
					if (row > 0) {
						
						String rString = UserDBService.AddUser(schoolname, "", "", corporationname, count + "",
								password, "社团官方");
						if (rString.equals("ok")) {

							sql = "insert into usertogroup(userid,groupid,jointime,groupnickname,userauthority,corppos) values(?,?,?,?,?,?)";
							PreparedStatement preparedStatement4 = conn.prepareStatement(sql);
							preparedStatement4.setString(1, count + "");
							preparedStatement4.setInt(2, id);
							preparedStatement4.setLong(3, time);
							preparedStatement4.setString(4, corporationname);
							preparedStatement4.setString(5, "管理员");
							preparedStatement4.setString(6, "官方账号");

							row = preparedStatement4.executeUpdate();
							if (row > 0) {

								sql = "update usergroup set number=number+2";
								conn.createStatement().executeUpdate(sql);

								return count + " " + id;
							} else {

								// sql = "delete from usertogroup where groupid="+id+" and
								// userid='"+creatorphonenumber+"'";
								// conn.createStatement().executeUpdate(sql);
								// sql = "delete from usergroup where groupid="+id;
								// conn.createStatement().executeUpdate(sql);

								return "errdb";

							}

						} else {
							// sql = "delete from usertogroup where groupid="+id+" and
							// userid='"+creatorphonenumber+"'";
							// conn.createStatement().executeUpdate(sql);
							// sql = "delete from usergroup where groupid="+id;
							// conn.createStatement().executeUpdate(sql);

							return "errdb";
						}

					} else {
						sql = "delete from usergroup where groupid=" + id;
						conn.createStatement().executeUpdate(sql);
						return "errdb";
					}

				} else {

					sql = "delete from usergroup where groupid=" + id;
					conn.createStatement().executeUpdate(sql);
					return "errdb";
				}

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

	// 获得某用户参与的所有社团或组织的群号
	public static Vector<Integer> getAllCorpGroupid(String ph) {

		Connection conn = null;
		try {
			Vector<Integer> ids = new Vector<Integer>();
			conn = DBManager.getConnection();
			String sql = "select groupid from usergroup where grouptype='社团群' and groupid in (select groupid from usertogroup where userid='"
					+ ph + "')";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				ids.add(rs.getInt(1));
			}
			return ids;
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

	// 根据群号获得某社团组织的名字
	public static String getCorpNameBygroupid(int groupid) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select corporationname from corporation where groupid='" + groupid + "'";

			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
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
	
	//社团组织任命职位
	public static String appointCorpPos(int groupid,String ph,String postype,String oldph) {
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "update usertogroup set corppos=?,part=? where groupid=? and userid=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			if(postype.endsWith("部")) {
				preparedStatement.setString(1, "部长");
				preparedStatement.setString(2, postype);
			}else {
				preparedStatement.setString(1, postype);
				preparedStatement.setString(2, "");
			}
			
			preparedStatement.setInt(3, groupid);
			preparedStatement.setString(4, ph);
			
			int row = preparedStatement.executeUpdate();
			if(row>0) {
				if(!oldph.equals("")) {
					sql = "update usertogroup set corppos=? where groupid=? and userid=?";
					preparedStatement = conn.prepareStatement(sql);
					preparedStatement.setString(1, "干事");
					preparedStatement.setInt(2, groupid);
					preparedStatement.setString(3, oldph);
					preparedStatement.executeUpdate();
				}
				return "ok";
			}
				
			return "error";
			
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
	

}
