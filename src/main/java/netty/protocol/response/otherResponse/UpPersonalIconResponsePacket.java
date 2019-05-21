package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.UpPersonalIcon_RESPONSE;


/*
更改自己的头像
 */
public class UpPersonalIconResponsePacket extends Packet {

	String result;
    @Override
    public int getCommand() {

        return UpPersonalIcon_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
