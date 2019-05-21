package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AddGroup_RESPONSE;
/*
申请加群
服务器响应
 */
public class AddGroupResponsePacket extends Packet {

	int groupid;
	String result;
	
    @Override
    public int getCommand() {

        return AddGroup_RESPONSE;
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
