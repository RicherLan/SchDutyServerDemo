package netty.protocol.response.friendGroupOperResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetUICOfSearchAddUser_RESPONSE;

import com.mchange.v2.async.StrandedTaskReporting;

/*
 * 		添加好友时    首先查询     获得对方的头像
 */
public class GetUICOfSearchAddUserResponsePacket extends Packet{

	String ph;
	byte[] ic;
	@Override
	public int getCommand() {
		
		return GetUICOfSearchAddUser_RESPONSE;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public byte[] getIc() {
		return ic;
	}
	public void setIc(byte[] ic) {
		this.ic = ic;
	}

}
