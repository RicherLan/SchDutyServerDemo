package netty.protocol.request.friendGroupOperRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.UpGroupIcon_Request;

/*
 * 	更改群头像
 */
public class UpGroupIconRequestPacket extends Packet{

	int groupid;
	byte[] ic;
	
	public UpGroupIconRequestPacket() {
		
	}
	public UpGroupIconRequestPacket(int groupid,byte[] icon) {
		this.groupid = groupid;
		this.ic = icon;
	}
	@Override
	public int getCommand() {
		
		return UpGroupIcon_Request;
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
