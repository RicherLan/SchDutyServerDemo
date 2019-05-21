package test;

import db.DBManager;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Year;
import java.util.Vector;
import java.util.PrimitiveIterator.OfDouble;

public class MoniDBService {
	
	
	public static void adduser(String ph,String nickname,String password,String schoolname,
			String departmentname,String majorname) {
		
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "insert into users(phonenumber,nickname,password,schoolname,departmentname,majorname)"
					+ "values(?,?,?,?,?,?)";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, ph);
			pStatement.setString(2, nickname);
			pStatement.setString(3,password);
			pStatement.setString(4, schoolname);
			pStatement.setString(5, departmentname);
			pStatement.setString(6, majorname);
			pStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	//加群
	public static void addCorpGroup(String ph,int groupid,long jointime,String nickname,String auth,String corppos) {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "insert into usertogroup(userid,groupid,jointime,groupnickname,userauthority,corppos)"
					+ "values(?,?,?,?,?,?)";
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, ph);
			pStatement.setInt(2, groupid);
			pStatement.setLong(3,jointime);
			pStatement.setString(4, nickname);
			pStatement.setString(5, auth);
			pStatement.setString(6, corppos);
			pStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	//保存某用户课表到数据库   
		public static void saveCourse(String ph,String course,int year,int xueqi) {

			Connection conn = null;
			try {
				conn = DBManager.getConnection();
				String sql = "delete from studentcourse where ph='"+ph+"' and year='"+year+"' and xueqi='"+xueqi+"'";
				conn.createStatement().executeUpdate(sql);
			
				sql = "insert into studentcourse(ph,course,year,xueqi) values(?,?,?,?)";
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, ph);
				preparedStatement.setString(2, course);
				preparedStatement.setInt(3, year);
				preparedStatement.setInt(4, xueqi);
				preparedStatement.executeUpdate();
				
				
			}catch (Exception e) {
				e.printStackTrace();
				return ;
			} 
			finally {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}	
			
			
		}
	
		
		public static Vector<TestStudentCourse> getStuCouByCorp(String corp){
			
			Connection conn = null;
			try {
				conn = DBManager.getConnection();
				Vector<TestStudentCourse> testStudentCourses = new Vector<TestStudentCourse>();
				String sql = "select * from testStudentCourse where corp='"+corp+"'";
			
				ResultSet resultSet = conn.createStatement().executeQuery(sql);
				while(resultSet.next()) {
					TestStudentCourse testStudentCourse = new TestStudentCourse();
					testStudentCourse.setCount(resultSet.getString(1));
					testStudentCourse.setName(resultSet.getString(2));
					testStudentCourse.setCorp(resultSet.getString(3));
					testStudentCourse.setCourse(resultSet.getString(4));
					testStudentCourse.setYear(resultSet.getInt(5));
					testStudentCourse.setXueqi(resultSet.getShort(6));
					
					testStudentCourses.add(testStudentCourse);
				}
				
				return testStudentCourses;
				
			}catch (Exception e) {
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
		
		//保存某用户课表到数据库   
		public static void saveCourse2(String count,String name,String corp,String course,int year,int xueqi) {

					Connection conn = null;
					try {
						conn = DBManager.getConnection();
						String sql = "delete from testStudentCourse where count='"+count+"' and year='"+year+"' and xueqi='"+xueqi+"'";
						conn.createStatement().executeUpdate(sql);
					
						sql = "insert into testStudentCourse(count,name,corp,course,year,xueqi) values(?,?,?,?,?,?)";
						PreparedStatement preparedStatement = conn.prepareStatement(sql);
						preparedStatement.setString(1, count);
						preparedStatement.setString(2, name);
						preparedStatement.setString(3, corp);
						preparedStatement.setString(4, course);
						preparedStatement.setInt(5, year);
						preparedStatement.setInt(6, xueqi);
						preparedStatement.executeUpdate();
						
						
					}catch (Exception e) {
						e.printStackTrace();
						return ;
					} 
					finally {
						try {
							conn.close();
						} catch (Exception e) {
						}
					}	
					
					
				}
		
		
		// 修改用户密码
		public static String updatePersonPassword(String phonenumber, String oldpassword,String newpwd) {
			Connection connection = null;
			try {
				connection = DBManager.getConnection();
				String sql2 = "select * from userinfo where phonenumber='" + phonenumber + "' and password='"+oldpassword+"'";
				ResultSet resultSet = connection.createStatement().executeQuery(sql2);
				if(resultSet.next()) {
					String sql = "update userinfo set password = '" + newpwd + "' where phonenumber='" + phonenumber + "'";
					int row = connection.createStatement().executeUpdate(sql);
					if (row <= 0) {
						return "errdb";
					}
					return "ok";
				}else {
					return "erroldpwd";
				}
				
				
				
			} catch (Exception e) {
				return "errdb";
			} finally {
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		
		
		
	public static void saveToUsers() {
		Connection connection = null;
		try {
			connection = DBManager.getConnection();
			String sql = "select * from teststudentcourse limit 40,80";
			ResultSet resultSet = connection.createStatement().executeQuery(sql);
			while(resultSet.next()) {
				
				String count = resultSet.getString(1);
				String name = resultSet.getString(2);
			
				String sql2 = "insert into users(phonenumber,nickname,password,schoolname,departmentname,majorname) values(?,?,?,?,?,?)";
				PreparedStatement preparedStatement = connection.prepareStatement(sql2);
				preparedStatement.setString(1, count);
				preparedStatement.setString(2, name);
				preparedStatement.setString(3, "110");
				preparedStatement.setString(4, "济南大学");
				preparedStatement.setString(5, "信息科学与工程学院");
				preparedStatement.setString(6, "计算机科学与技术");
				int row = preparedStatement.executeUpdate();
				if(row>0) {
					long time = System.currentTimeMillis()/1000;
					String sql3 = "insert into usertogroup(userid,groupid,jointime,groupnickname) values(?,?,?,?)";
					PreparedStatement preparedStatement2 = connection.prepareStatement(sql3);
					preparedStatement2.setString(1, count);
					preparedStatement2.setInt(2, 600012);
					preparedStatement2.setLong(3, time);
					preparedStatement2.setString(4, name);
					preparedStatement2.executeUpdate();
				}
				
				
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void saveToCourse() {
		Connection connection = null;
		try {
			connection = DBManager.getConnection();
			String sql2 = "select userid from usertogroup where userid<>'546244' and userid<>'12345678910' and userid<>'546245' and userid<>'15254150058'"; 
			ResultSet resultSet = connection.createStatement().executeQuery(sql2);
			Vector<String> phStrings = new Vector<String>();
			while(resultSet.next()) {
				phStrings.add(resultSet.getString(1));
			}
			
			
			for(int i=0;i<phStrings.size();++i) {
				String ph = phStrings.get(i);
				String sql = "select * from teststudentcourse where count='"+ph+"'";
				ResultSet resultSet2 = connection.createStatement().executeQuery(sql);
				if(resultSet2.next()){
					String course = resultSet2.getString(4);
					int year = resultSet2.getInt(5);
					int xueqi = resultSet2.getInt(6);
					
					sql = "insert into studentcourse(ph,course,year,xueqi) values(?,?,?,?)";
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, ph);
					preparedStatement.setString(2, course);
					preparedStatement.setInt(3, year);
					preparedStatement.setInt(4, xueqi);
					
					preparedStatement.executeUpdate();
					
				}
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
