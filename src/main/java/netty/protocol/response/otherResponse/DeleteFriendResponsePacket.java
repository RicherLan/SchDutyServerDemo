package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.DeleteFriend_RESPONSE;

public class DeleteFriendResponsePacket extends Packet {
	
	String result;
	String ph;
    @Override
    public int getCommand() {

        return DeleteFriend_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}

}
