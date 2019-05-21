package netty.protocol.response.notificationResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetNotiIconOfGroup_RESPONSE;

/*
 * 	获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
 */
public class GetNotiIconOfGroupResponsePacket extends Packet{
	int groupid;
	byte[] icon;
	@Override
	public int getCommand() {
		
		return GetNotiIconOfGroup_RESPONSE;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

}
