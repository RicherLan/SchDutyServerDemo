package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 本服务器 配置类
 * @author Role
 *
 */
public class Config {


	public static int nettyPort = 8988;
	public static int verticlePort = 8989;
	
	public static int nDaysBetweenTwoDate(String firstString, String secondString) {  
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
        Date firstDate = null;  
        Date secondDate = null;  
        try {  
            firstDate = df.parse(firstString);  
            secondDate = df.parse(secondString);  
        } catch (Exception e) {  
            // 日期型字符串格式错误  
            System.out.println("日期型字符串格式错误");  
        }  
        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));  
        return nDay;  
    }
	
	
}
