package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetPerIcon_RESPONSE;

/*
获取自己的的头像
 */
public class GetPerIconResponsePacket extends Packet {


	String icph;
	byte[] bs;
	
    @Override
    public int getCommand() {

        return GetPerIcon_RESPONSE;
    }


	public String getIcph() {
		return icph;
	}

	public void setIcph(String icph) {
		this.icph = icph;
	}

	public byte[] getBs() {
		return bs;
	}

	public void setBs(byte[] bs) {
		this.bs = bs;
	}

}
