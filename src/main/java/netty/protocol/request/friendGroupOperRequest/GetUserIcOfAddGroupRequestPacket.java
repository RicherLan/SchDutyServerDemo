package netty.protocol.request.friendGroupOperRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetUserIcOfAddGroup_REQUEST;

/*	
 * 添加群  群管理员要获得对方的头像
 */
public class GetUserIcOfAddGroupRequestPacket extends Packet{
	
	int groupid;
	String ph;
	public GetUserIcOfAddGroupRequestPacket() {
		
	}
	public GetUserIcOfAddGroupRequestPacket(int groupid,String ph) {
		this.groupid = groupid;
		this.ph = ph;
	}
	@Override
	public int getCommand() {
		
		return GetUserIcOfAddGroup_REQUEST;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

}
