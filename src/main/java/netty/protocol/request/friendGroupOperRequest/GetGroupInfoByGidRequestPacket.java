package netty.protocol.request.friendGroupOperRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetGroupInfoByGid_REQUEST;

/*
 * 	获得用户的某群的信息   一般用在更新某群信息时
 */
public class GetGroupInfoByGidRequestPacket extends Packet{

	int groupid;
	public GetGroupInfoByGidRequestPacket() {
		
	}
	public GetGroupInfoByGidRequestPacket(int groupid) {
		this.groupid = groupid;
	}
	@Override
	public int getCommand() {
		
		return GetGroupInfoByGid_REQUEST;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

}
