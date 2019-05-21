package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 本服务器 配置类
 * @author Role
 *
 */
public class config {

	public static int ServerMsgPort = 8989;      //负责聊天的服务器的端口
	public static int ServerLoginPort = 8980;   //负责其他所有请求的服务器的端口
	public static int ServerNoticePort = 8080;   //服务器给客户端系统通知的端口
	public static int RecordClientPort = 8090;    //给客户端发送聊天记录的  客户端的port
//	public static String ServerIP = "47.106.84.47";
	public static String ServerIP = "192.168.8.104";
	
	public static int sendMsgClientPort = 8888;   //聊天时服务器给客户端发消息时   客户端指定的接收端口
	
	
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
