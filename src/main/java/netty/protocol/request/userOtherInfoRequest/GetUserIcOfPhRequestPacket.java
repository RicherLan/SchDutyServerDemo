package netty.protocol.request.userOtherInfoRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetUserIcOfPh_REQUEST;


/*
 * 	获得某用户头像
 */
public class GetUserIcOfPhRequestPacket extends Packet{

	String ph;
	public GetUserIcOfPhRequestPacket() {
		
	}
	public GetUserIcOfPhRequestPacket(String ph) {
		this.ph = ph;
	}
	@Override
	public int getCommand() {
		// TODO Auto-generated method stub
		return GetUserIcOfPh_REQUEST;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	
}
