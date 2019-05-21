package netty.protocol.response.userOtherInfoResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetUserIcOfPh_RESPONSE;

public class GetUserIcOfPhResponsePacket extends Packet{

	String ph;
	byte[] ic;
	@Override
	public int getCommand() {
		
		return GetUserIcOfPh_RESPONSE;
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
