package util;

import java.net.Socket;
import java.util.Vector;

/*
 * 存放所有在线的  成员的ip和port id name    服务器转发消息时 给每个人发
 * 每一台终端启动时  就会给服务器发送一条消息   ip+port
 * 下线时 也发一条命令   
 * 
 */


//*********************
//我没有动态刷新name  因为用不到      聊天消息用到  ip  port    如果要显示在线用户的话会用到uid     
//
//*********************
public class AllStudentsOnline {

	
	public static Vector<Online> onlines = new Vector<Online>();
	
	public static void add(Socket socket , String ip,int port,String phonenumber) {
		
		try {
			onlines.add(new Online(socket,ip,port,phonenumber));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void remove(String ip) {
		
		int index = -1;
		for(int i=0;i<onlines.size();++i) {
			if(onlines.get(i).ip==ip) {
				index = i;
				break;
			}
		}
		
		if(index!=-1) {
			onlines.remove(index);
		}
		
	}
	
}
