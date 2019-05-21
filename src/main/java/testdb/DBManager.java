package testdb;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {

	private static DataSource dataSource = null;
	static {
		
		try {
			
			ComboPooledDataSource pool = new ComboPooledDataSource();
			pool.setDriverClass("com.mysql.jdbc.Driver");
			pool.setJdbcUrl("jdbc:mysql:///edu?useUnicode=true&characterEncoding=UTF-8");
			pool.setUser("root");
//			pool.setPassword("!Qq199712272012");
			pool.setPassword("134827");
			pool.setMaxPoolSize(5);
			pool.setMinPoolSize(2);
			//连接超过3秒  就断开
			pool.setLoginTimeout(3000);
			
			dataSource = pool;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("数据库连接失败!");
			
		}
	}
	
	public static Connection getConnection() throws SQLException {
		
		return dataSource.getConnection();
	}
	/*public static void main(String[] args) throws Exception{
		System.out.println(DBManager.getConnection());
	}*/
	
}
