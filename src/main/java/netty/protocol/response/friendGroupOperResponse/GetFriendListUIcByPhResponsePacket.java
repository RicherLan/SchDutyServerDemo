package netty.protocol.response.friendGroupOperResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetFriendListUIcByPh_RESPONSE;

import com.mchange.v2.async.StrandedTaskReporting;

/*
 * 	进入好友列表   如果本地没有好友的头像   获取好友的的头像
 */
public class GetFriendListUIcByPhResponsePacket extends Packet{

	String ph;
	byte[] ic;
	@Override
	public int getCommand() {
		
		return GetFriendListUIcByPh_RESPONSE;
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
