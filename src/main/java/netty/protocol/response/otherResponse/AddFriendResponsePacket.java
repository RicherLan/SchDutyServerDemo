package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AddFriend_RESPONSE;

/*
添加好友   服务器回执
 */
public class AddFriendResponsePacket extends Packet {

	String result;
	String friendPh;
    @Override
    public int getCommand() {

        return AddFriend_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFriendPh() {
		return friendPh;
	}
	public void setFriendPh(String friendPh) {
		this.friendPh = friendPh;
	}

}
