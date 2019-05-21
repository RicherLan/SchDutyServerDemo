package netty.protocol.packet;

import netty.protocol.Packet;

import static netty.protocol.command.Command.loginByOther_Packet;

/*
 * 	自己登陆   有人异地登陆  顶替自己
 */
public class LoginByOther_PACKET extends Packet{

	long time;
	String msg;
	
	@Override
	public int getCommand() {
		
		return loginByOther_Packet;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
