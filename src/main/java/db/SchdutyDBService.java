package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import object.CorpUserNotLoadCourse;
import object.SearchEmptyCourse;
import object.UserCorp;
import schedulearrangement.ClientArrangement;
import schedulearrangement.ScheduleArrangement;
import schedulearrangement.UserKebiao;

/*
 * 值班表相关的操作   包括成绩和课表的查询
 */
public class SchdutyDBService {

	// 保存值班表之前 把值班表中的还未值班的成员的arrangement置为0
	public static void resetArrBeforeSaveArr(int groupid, int year, int month, int daytime, int week, int way,
			int section) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			Vector<String> phStrings = new Vector<String>();
			String sql = "select id,year,month,daytime,week,section,phs from dutytable where groupid='" + groupid + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				int year1 = resultSet.getInt(2);
				int month1 = resultSet.getInt(3);
				int daytime1 = resultSet.getInt(4);
				int week1 = resultSet.getInt(5);
				// int day1 = resultSet.getInt(6);
				int section1 = resultSet.getInt(6);
				String phs = resultSet.getString(7);
				boolean flag = false;
				if (year1 > year || year1 == year && month1 > month
						|| year1 == year && month1 == month && daytime1 > way
						|| year1 == year && month1 == month && daytime1 == way && section1 >= section) {
					flag = true;
				}

				if (flag) {

					String[] strings = phs.split(" ");
					for (int i = 0; i < strings.length; ++i) {
						phStrings.add(strings[i]);
					}
					sql = "delete from dutytable where id=" + id;
					conn.createStatement().executeUpdate(sql);
				}

			}

			for (int i = 0; i < phStrings.size(); ++i) {
				String ph = phStrings.get(i);
				sql = "update usertogroup set arrangement='0' where groupid='" + groupid + "' and userid='" + ph + "'";
				conn.createStatement().executeUpdate(sql);
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

	// 保存值班表到数据库
	public static String saveScheduleArrangement2(int groupid, Vector<ClientArrangement> clientArrangements) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			 String sql = "delete from dutytable where groupid='"+groupid+"'";
			 conn.createStatement().executeUpdate(sql);
			
			 sql = "insert into dutytable(groupid,week,section,phs,year,month,daytime,way) values(?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			for (int i = 0; i < clientArrangements.size(); ++i) {

				ClientArrangement clientArrangement = clientArrangements.get(i);
				preparedStatement.setInt(1, groupid);
				preparedStatement.setInt(2, clientArrangement.week);
				// preparedStatement.setInt(3, clientArrangement.day);
				preparedStatement.setInt(3, clientArrangement.section);
				String phString = "";
				for (int j = 0; j < clientArrangement.phs.size(); ++j) {
					phString += clientArrangement.phs.get(j) + " ";
				}
				phString = phString.trim();
				preparedStatement.setString(4, phString);
				preparedStatement.setInt(5, clientArrangement.getYear());
				preparedStatement.setInt(6, clientArrangement.getMonth());
				preparedStatement.setInt(7, clientArrangement.getDaytime());
				preparedStatement.setInt(8, clientArrangement.getWay());
				preparedStatement.executeUpdate();
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

	// 安排完值班，把该次值班的开始时间记录 ， 以便成员根据开始时间从值班表中查找
	public static String saveSchDutyBeginTime(int groupid, int timeyear, int month, int day, int way) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "delete from dutytablebegintime where groupid=" + groupid;
			conn.createStatement().executeUpdate(sql);
			sql = "insert into dutytablebegintime(groupid,year,month,day,way) values(?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, groupid);
			preparedStatement.setInt(2, timeyear);
			preparedStatement.setInt(3, month);
			preparedStatement.setInt(4, day);
			preparedStatement.setInt(5, way);
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

	// 获得某组织的值班表
	public static Vector<ClientArrangement> getDutyTableBygroupid(int groupid) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select * from dutytable where groupid=" + groupid;
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			Vector<ClientArrangement> clientArrangements = new Vector<ClientArrangement>();
			while (resultSet.next()) {
				ClientArrangement clientArrangement = new ClientArrangement();

				clientArrangement.setGroupid(resultSet.getInt(2));
				clientArrangement.setWeek(resultSet.getInt(3));
				// clientArrangement.setDay(resultSet.getInt(4));
				clientArrangement.setSection(resultSet.getInt(5));
				String[] strings = resultSet.getString(6).split(" ");
				Vector<String> strings2 = new Vector<String>();
				Vector<String> names = new Vector<String>();
				Vector<String> poStrings = new Vector<String>();
				for (int i = 0; i < strings.length; ++i) {
					String ph = strings[i];
					strings2.add(ph);
					String name = GroupDBService.getGroupNameByph(ph, groupid);
					String pos = CorpDBService.getCorpPosByPh(ph, groupid);
					
					
					names.add(name);
					poStrings.add(pos);

				}

				clientArrangement.setPhs(strings2);
				clientArrangement.setNames(names);
				clientArrangement.setPoss(poStrings);

				clientArrangement.setYear(resultSet.getInt(7));
				clientArrangement.setMonth(resultSet.getInt(8));
				clientArrangement.setDaytime(resultSet.getInt(9));
				clientArrangement.setWay(resultSet.getInt(10));

				clientArrangements.add(clientArrangement);
			}
			return clientArrangements;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 获得某用户的所有社团组织排班的未读通知
	public static Vector<String> getAllDutynotiNOtreadByph(String ph) {
		Connection conn = null;
		try {
			Vector<String> strings = new Vector<String>();
			conn = DBManager.getConnection();
			String sql = "select id,groupid,time from dutynotice where userid='" + ph + "' and state='未读'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			while (resultSet.next()) {
				String string = "";
				string += resultSet.getInt(1) + " ";
				string += resultSet.getInt(2) + " ";
				string += resultSet.getLong(3);
				strings.add(string);
			}

			return strings;
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

	// 已经读取值班的通知 向服务器反馈
	public static String changeDutyNoticeState(String ph, int groupid, int dutynotiid) {

		Connection conn = null;
		try {

			conn = DBManager.getConnection();
			String sql = "update dutynotice set state='已读' where groupid=? and userid=? and id<=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, groupid);
			preparedStatement.setString(2, ph);
			preparedStatement.setInt(3, dutynotiid);

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

	// 安排完值班后 要把通知写入数据库
	public static int addDutyNotice(int groupid, String phString, String state, long time) {
		Connection conn = null;
		try {
			
			conn = DBManager.getConnection();
			
			String sql = "delete from dutynotice where groupid='" + groupid + "' and userid='" + phString + "'";
			conn.createStatement().executeUpdate(sql);
			sql = "insert into dutynotice(groupid,userid,state,time) values(?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, groupid);
			preparedStatement.setString(2, phString);
			preparedStatement.setString(3, state);
			preparedStatement.setLong(4, time);
			preparedStatement.executeUpdate();

			int id = -1;
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
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

	// 获得某社团或组织最大的arrangement
	public static int getMaxArrangementByCorpAccount(int account) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "select max(arrangement) from usertogroup where groupid in (select groupid from corporation where corpaccount='"
					+ account + "')";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				int arrangement = resultSet.getInt(1);
				return arrangement;
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

	// 获得某几节课都有空的人
	public static Vector<UserCorp> getStuByEptCou(int groupid, int year, int xueqi,
			Vector<SearchEmptyCourse> searchEmptyCourses) {

		Connection conn = null;
		Vector<UserCorp> userCorps = new Vector<UserCorp>();
		try {
			conn = DBManager.getConnection();
			Vector<String> phStrings = new Vector<String>();
			Vector<String> sexs = new Vector<String>();
			String sql = "select userid from usertogroup where groupid='" + groupid + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(sql);
			while (resultSet.next()) {
				String ph = resultSet.getString(1);
				if (ph.length() >= 11) { // 去除社团组织的官方账号
					phStrings.add(ph);
				}

			}
			for (int i = 0; i < phStrings.size(); ++i) {
				String ph = phStrings.get(i);
				sql = "select sex from users where phonenumber='" + ph + "'";
				resultSet = conn.createStatement().executeQuery(sql);
				if (resultSet.next()) {
					String sex = resultSet.getString(1);
					sexs.add(sex);
				} else {
					sexs.add(null);
				}
			}

			if (phStrings.size() != 0) {
				for (int i = 0; i < phStrings.size(); ++i) {
					String ph = phStrings.get(i);
					sql = "select course from studentcourse where ph='" + ph + "' and year='" + year + "' and xueqi='"
							+ xueqi + "'";
					resultSet = conn.createStatement().executeQuery(sql);
					if (resultSet.next()) {
						String course = resultSet.getString(1);
						if (ScheduleArrangement.isOkEmpty2(course, searchEmptyCourses)) {
							UserCorp userCorp = new UserCorp();
							userCorp.setPh(ph);
							userCorp.setGroupid(groupid);
							userCorp.setSex(sexs.get(i));
							sql = "select groupnickname,part,corppos from usertogroup where groupid='" + groupid
									+ "' and userid='" + ph + "'";
							ResultSet resultSet2 = conn.createStatement().executeQuery(sql);
							if (resultSet2.next()) {
								String groupnickname = resultSet2.getString(1);
								String part = resultSet2.getString(2);
								String corppos = resultSet2.getString(3);
								userCorp.setPart(part);
								userCorp.setCorppos(corppos);
								userCorp.setGroupname(groupnickname);
								userCorps.add(userCorp);
							}
						}
					}
				}

			}
//			System.out.println(userCorps.size() + "  iiiiiiiiiiiiiiii");
			return userCorps;

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

	//查看课表导入情况
	public static Map<Integer, Vector<CorpUserNotLoadCourse>> getCorpLoadCourseRs(int groupid) {

		Connection conn = null;
		
		Vector<CorpUserNotLoadCourse> names = new Vector<CorpUserNotLoadCourse>();
		try {
			conn = DBManager.getConnection();
			String sql = "select year,xueqi from usergroup where groupid=?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, groupid);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {

				int year = resultSet.getInt(1);
				int xueqi = resultSet.getInt(2);
				sql = "select userid,groupnickname,part,corppos from usertogroup where groupid=? and corppos<>?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1, groupid);
				statement.setString(2, "官方账号");
				ResultSet resultSet2 = statement.executeQuery();
				while (resultSet2.next()) {
					String ph = resultSet2.getString(1);

					String groupnickname = resultSet2.getString(2);
					String part = resultSet2.getString(3);
					String corppos = resultSet2.getString(4);
					CorpUserNotLoadCourse corpUserNotLoadCourse = new CorpUserNotLoadCourse();
					corpUserNotLoadCourse.setPh(ph);
					corpUserNotLoadCourse.setGroupnickname(groupnickname);
					corpUserNotLoadCourse.setPart(part);
					corpUserNotLoadCourse.setPos(corppos);
					names.add(corpUserNotLoadCourse);
				}
				
				//群人数
				int groupusernum = names.size()+1;
				
				sql = "select * from studentcourse where ph=? and year=? and xueqi=?";
				PreparedStatement preparedStatement2 = conn.prepareStatement(sql);

				Vector<CorpUserNotLoadCourse> delete = new Vector<CorpUserNotLoadCourse>();
				for (CorpUserNotLoadCourse corpUserNotLoadCourse : names) {

					preparedStatement2.setString(1, corpUserNotLoadCourse.getPh());
					preparedStatement2.setInt(2, year);
					preparedStatement2.setInt(3, xueqi);

					resultSet2 = preparedStatement2.executeQuery();
					if (resultSet2.next()) {
						delete.add(corpUserNotLoadCourse);
					}

				}

				for (CorpUserNotLoadCourse corpUserNotLoadCourse : delete) {

					names.remove(corpUserNotLoadCourse);
				}
				
				Map<Integer, Vector<CorpUserNotLoadCourse>> map = new HashMap<Integer, Vector<CorpUserNotLoadCourse>>();
				map.put(groupusernum, names);
				return map;
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
			}
		}

	}

	// 修改成员课表的arrangement
	public static boolean changeUserCourseArrangement(Map<String, Integer> map, int groupid) {
		Connection conn = null;

		try {

			conn = DBManager.getConnection();
			boolean flag = false;
			for (String ph : map.keySet()) {
				int arrangement = map.get(ph);
				String sql = "update usertogroup set arrangement='" + arrangement + "' where userid='" + ph
						+ "' and groupid='" + groupid + "'";
				int row = conn.createStatement().executeUpdate(sql);
				if (row > 0) {
					flag = true;
				}
			}

			return flag;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	// 获得某社团组织 某职位的所有人 的课表 type：部长 干事
	public static Vector<UserKebiao> getKebiaoByCorpAccount(String pos, int groupid, int year, int grade) {
		Connection conn = null;
		Vector<UserKebiao> userKebiaos = new Vector<UserKebiao>();
		try {
			conn = DBManager.getConnection();

			String sql = "select userid,arrangement from usertogroup where groupid='" + groupid + "' and corppos='"
					+ pos + "' order by arrangement asc";
			ResultSet resultSet2 = conn.createStatement().executeQuery(sql);

			while (resultSet2.next()) {
				String phString = resultSet2.getString(1);
				int arrangement = resultSet2.getInt(2);
				sql = "select course from studentcourse where ph='" + phString + "' and year='" + year + "' and xueqi='"
						+ grade + "'";
				ResultSet resultSet3 = conn.createStatement().executeQuery(sql);
				if (resultSet3.next()) {
					String course = resultSet3.getString(1);
					UserKebiao userKebiao = new UserKebiao();
					userKebiao.setPh(phString);
					userKebiao.setCourse(course);
					userKebiao.setArrangement(arrangement);
					userKebiaos.add(userKebiao);

				}

			}

			return userKebiaos;
		} catch (Exception e) {
			e.printStackTrace();
			return userKebiaos;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}
}
