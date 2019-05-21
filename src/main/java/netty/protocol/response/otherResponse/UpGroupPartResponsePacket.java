package netty.protocol.response.otherResponse;
import netty.protocol.Packet;

import static netty.protocol.command.Command.UpGroupPart_RESPONSE;

/*
修改自己的部室
 */
public class UpGroupPartResponsePacket extends Packet{
	String result;
	int groupid;
	String part;
	
	@Override
    public int getCommand() {

        return UpGroupPart_RESPONSE;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}


}
