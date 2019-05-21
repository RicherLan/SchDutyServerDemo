package netty.protocol.request.friendGroupOperRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetUserIcOfAddMeAsFriend_REQUEST;

import netty.protocol.Packet;

/*
 * 添加好友  被添加方要获得对方的头像
 */
public class GetUserIcOfAddMeAsFriendRequestPacket extends Packet{
	
	String ph;
	public GetUserIcOfAddMeAsFriendRequestPacket() {
		
	}
	public GetUserIcOfAddMeAsFriendRequestPacket(String ph) {
		this.ph = ph;
	}
	@Override
	public int getCommand() {
		
		return GetUserIcOfAddMeAsFriend_REQUEST;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}

}
