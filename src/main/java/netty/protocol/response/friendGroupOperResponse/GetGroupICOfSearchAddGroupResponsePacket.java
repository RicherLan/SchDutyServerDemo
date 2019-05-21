package netty.protocol.response.friendGroupOperResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetGroupICOfSearchAddGroup_RESPONSE;

/*
 * 	添加群时    首先查询     获得群的头像
 */
public class GetGroupICOfSearchAddGroupResponsePacket extends Packet{
	
	int groupid;
	byte[] ic;
	@Override
	public int getCommand() {
		
		return GetGroupICOfSearchAddGroup_RESPONSE;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public byte[] getIc() {
		return ic;
	}
	public void setIc(byte[] ic) {
		this.ic = ic;
	}
	
	

}
