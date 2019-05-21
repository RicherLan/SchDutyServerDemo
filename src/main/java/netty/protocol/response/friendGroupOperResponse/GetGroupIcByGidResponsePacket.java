package netty.protocol.response.friendGroupOperResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetGroupIcByGid_RESPONSE;

/*
 * 	获得某群头像
 */
public class GetGroupIcByGidResponsePacket extends Packet{

	int groupid;
	byte[] icon;
	@Override
	public int getCommand() {
		
		return GetGroupIcByGid_RESPONSE;
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
