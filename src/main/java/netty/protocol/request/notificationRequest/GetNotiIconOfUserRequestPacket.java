package netty.protocol.request.notificationRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetNotiIconOfUser_Request;

/*
 * 	获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
 */
public class GetNotiIconOfUserRequestPacket extends Packet{
	String ph;
	public GetNotiIconOfUserRequestPacket() {
		
	}
	public GetNotiIconOfUserRequestPacket(String ph) {
		this.ph = ph;
	}
	@Override
	public int getCommand() {
		
		return GetNotiIconOfUser_Request;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}

}
