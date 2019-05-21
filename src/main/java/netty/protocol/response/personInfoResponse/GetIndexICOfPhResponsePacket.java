package netty.protocol.response.personInfoResponse;

import static netty.protocol.command.Command.GetIndexICOfPh_RESPONSE;

import netty.protocol.Packet;

/*
 * 	进入某人的个人页面时  获得其头像
 */
public class GetIndexICOfPhResponsePacket extends Packet {
	
	String ph;
	byte[] icon;
	@Override
	public int getCommand() {

		return GetIndexICOfPh_RESPONSE;
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

}
