package netty.protocol.response.friendGroupOperResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetUserIcOfAddGroup_RESPONSE;

/*
 * 	 添加群  群管理员要获得对方的头像
 */
public class GetUserIcOfAddGroupResponsePacket extends Packet{
	int groupid;
	String ph;
	byte[] icon;
	@Override
	public int getCommand() {
		
		return GetUserIcOfAddGroup_RESPONSE;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

}
