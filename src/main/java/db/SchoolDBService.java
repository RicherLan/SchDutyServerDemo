package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * 学校的数据库操作
 */

public class SchoolDBService {

	// 创建新的学校
	// 同时 创建 用户表 成绩表 课程表 教务账号表 社团表
	public static String createSchool(String schoolname) throws Exception {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String sql = "insert into schools(schoolname) values(?)";
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, schoolname);
			if (pst.executeUpdate() <= 0) {
				return "errordatabase";
			}
			ResultSet rs = pst.getGeneratedKeys();
			int schoolid = -1;
			if (rs.next()) {
				schoolid = rs.getInt(1);
			}
			String tableuser = schoolid + "users";
			String tablecourse = schoolid + "studentkebiao";
			String tablescore = schoolid + "studentscore";
			String tableedu = schoolid + "studentedu";
			String tablecorporation = schoolname + "corporation";

			sql = "CREATE TABLE " + tableuser
					+ "(uname varchar(100) not null, password varchar(100) not null,phonenumber varchar(20) not null,qq varchar(30),weixin varchar(30),address varchar(100) not null,score decimal(15,0) ,sex varchar(10) not null,utype varchar(10) not null,time int(11) not null,position varchar(100) not null,noticemsg varchar(200) ,isnoticed int(11) not null,schoolid int(11) not null,schoolname varchar(100) not null,majorname varchar(100) not null,departmentname varchar(100) not null)charset=utf8;";

			Statement statement = conn.createStatement();
			if (statement.executeUpdate(sql) <= 0) {
				return "errordatabase";

			}
			sql = "CREATE TABLE " + tablecourse
					+ "(name varchar(100) not null, phonenumber varchar(100) not null,oneone int(5) not null,onetwo int(5) not null,onethree int(5) not null,onefour int(5) not null,twoone int(5) not null,twotwo int(5) not null,twothree int(5) not null,twofour int(5) not null,threeone int(5) not null,threetwo int(5) not null,threethree int(5) not null,threefour int(5) not null,fourone int(5) not null,fourtwo int(5) not null,fourthree int(5) not null,fourfour int(5) not null,fiveone int(5) not null,fivetwo int(5) not null,fivethree int(5) not null,fivefour int(5) not null,satone int(5) not null,sattwo int(5) not null,satthree int(5) not null,satfour int(5) not null,sunone int(5) not null,suntwo int(5) not null,sunthree int(5) not null,sunfour int(5) not null,num int(5) not null,arrangement int(5) not null)charset=utf8;";
			statement = conn.createStatement();
			if (statement.executeUpdate(sql) <= 0) {
				return "errordatabase";

			}

			sql = "CREATE TABLE " + tablescore
					+ "(username varchar(50) not null, userphonenumber varchar(50) not null,userxuehao varchar(50) not null,usersex varchar(50),courseName varchar(50) not null,courseScore double not null,userclass varchar(50) ,usercollege varchar(50),courseTeacher varchar(50) ,courseType int(50) ,examInfo varchar(50))charset=utf8;";

			statement = conn.createStatement();
			if (statement.executeUpdate(sql) <= 0) {
				return "errordatabase";

			}

			sql = "CREATE TABLE " + tableedu
					+ "(username varchar(50) not null, phonenumber varchar(50) not null,schoolname varchar(50) not null,educount varchar(50) not null,edupassword varchar(50) not null)charset=utf8;";

			statement = conn.createStatement();
			if (statement.executeUpdate(sql) <= 0) {
				return "errordatabase";

			}

			sql = "CREATE TABLE " + tablecorporation
					+ "(corporationname varchar(50) not null, corporationtype varchar(50) not null,corporationinfo varchar(500) not null,creatorname varchar(50) not null,creatorphonenumber varchar(50) not null)charset=utf8;";

			statement = conn.createStatement();
			if (statement.executeUpdate(sql) <= 0) {
				return "errordatabase";

			}

			return "ok";

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
