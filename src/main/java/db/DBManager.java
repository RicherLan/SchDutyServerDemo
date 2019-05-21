package db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.print.DocFlavor.STRING;
import javax.sql.DataSource;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

	private static DataSource dataSource = null;
	static {

		try {

			ComboPooledDataSource pool = new ComboPooledDataSource();
			pool.setDriverClass("com.mysql.jdbc.Driver");
			pool.setJdbcUrl("jdbc:mysql:///schedulearrangement?useUnicode=true&characterEncoding=UTF-8");
			pool.setUser("root");
//			 pool.setPassword("!Qq199712272012");
			pool.setPassword("134827");
			
			//c3p0反空闲设置，防止8小时失效问题28800
			pool.setTestConnectionOnCheckout(false);
			pool.setTestConnectionOnCheckin(true);
			pool.setIdleConnectionTestPeriod(3600);
			
			//连接池配置
			pool.setInitialPoolSize(10);
			pool.setMaxPoolSize(100);
			pool.setMinPoolSize(10);
			pool.setMaxIdleTime(3600);
		
			
			// 连接超过3秒 就断开
			pool.setLoginTimeout(3000);

			dataSource = pool;
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("数据库连接失败!");

		}
	}

	public static Connection getConnection() throws SQLException {

		return dataSource.getConnection();
	}
	
	  public static void main(String[] args) throws Exception{
		  Connection connection = DBManager.getConnection();
		  for(int i=250+32+1;i<=250+32+105;++i) {
			  
			  String sql = "insert into carpark(id,type,charge,info) values("
			  		+ "'"+i+"','3','2','not electrical e-bike park')";
			  Statement statement = connection.createStatement();
			  statement.executeUpdate(sql);
		  }
		}
	 

}
