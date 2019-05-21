package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetGroupUserIconByPh_RESPONSE;

/*
 * 	获得某群的所有成员后 向服务器回执 来让服务器发头像
 */
public class GetGroupUserIconByPhResponsePacket extends Packet{

	int groupid;
	String ph;
	byte[] icon;
	String type;
	@Override
	public int getCommand() {
		// TODO Auto-generated method stub
		return GetGroupUserIconByPh_RESPONSE;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

}
