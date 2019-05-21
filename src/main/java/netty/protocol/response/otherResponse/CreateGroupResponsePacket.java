package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.CreateGroup_RESPONSE;

/*
创建群
 */
public class CreateGroupResponsePacket extends Packet {

	String result;
	int groupid;
	
    @Override
    public int getCommand() {

        return CreateGroup_RESPONSE;
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

}
