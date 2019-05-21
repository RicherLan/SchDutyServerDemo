package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetGroupUserIconByPh_RESPONSE;

/*
    获得某群的所有成员后  向服务器回执     来让服务器发头像
 */
public class GetGroupAllUserIconResponsePacket extends Packet {

	
	String ph;
	int groupid;
	byte[] icon;
    @Override
    public int getCommand() {

        return GetGroupUserIconByPh_RESPONSE;
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
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

}
