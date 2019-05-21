package netty.protocol.request.friendGroupOperRequest;
import static netty.protocol.command.Command.GetUICOfSearchAddUser_Request;

import netty.protocol.Packet;

/*
 * 	添加好友时    首先查询     获得对方的头像
 */
public class GetUICOfSearchAddUserRequestPacket extends Packet{

	String ph;
	public GetUICOfSearchAddUserRequestPacket() {
		
	}
	public GetUICOfSearchAddUserRequestPacket(String ph) {
		this.ph = ph;
	}
	@Override
	public int getCommand() {
		
		return GetUICOfSearchAddUser_Request;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}

}
