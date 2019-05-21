package netty.protocol.request.friendGroupOperRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetFriendListUIcByPh_Request;

/*
 * 	进入好友列表   如果本地没有好友的头像   获取好友的的头像
 */
public class GetFriendListUIcByPhRequestPacket extends Packet{
	String ph;
	public GetFriendListUIcByPhRequestPacket() {
		
	}
	public GetFriendListUIcByPhRequestPacket(String ph) {
		this.ph = ph;
	}
	@Override
	public int getCommand() {
		
		return GetFriendListUIcByPh_Request;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}

}
