package netty.protocol.request.friendGroupOperRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetUIcOfAgreeYourFriend_Request;

/*
 * 	对方同意了你的好友请求    获得对方的头像
 */
public class GetUIcOfAgreeYourFriendRequestPacket extends Packet{

	String ph;
	public GetUIcOfAgreeYourFriendRequestPacket() {
		
	}
	public GetUIcOfAgreeYourFriendRequestPacket(String ph) {
		this.ph = ph;
	}
	@Override
	public int getCommand() {
		// TODO Auto-generated method stub
		return GetUIcOfAgreeYourFriend_Request;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}

}
