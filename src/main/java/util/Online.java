package util;

import java.net.Socket;

/*
 * 在线的终端
 */

public class Online {

	public Socket socket = null;
	public String ip;
	public int port ;
	public String phonenumber;
	
	public Online(Socket socket,String ip,int port,String phonenumber) {
		this.socket = socket;
		this.ip = ip;
		this.port = port;
		this.phonenumber = phonenumber;
	}
}
