package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import object.ChatMsg;

import static netty.protocol.command.Command.GetRequestFriendOrGroup_RESPONSE;

import java.util.Vector;

/*
拿到所有的好友、群请求信息  一般在刚登陆的时候
服务器响应
 */
public class GetRequestFriendOrGroupResponsePacket extends Packet {

	Vector<ChatMsg> requestFriendOrGroups;
	
    @Override
    public int getCommand() {

        return GetRequestFriendOrGroup_RESPONSE;
    }

	public Vector<ChatMsg> getRequestFriendOrGroups() {
		return requestFriendOrGroups;
	}

	public void setRequestFriendOrGroups(Vector<ChatMsg> requestFriendOrGroups) {
		this.requestFriendOrGroups = requestFriendOrGroups;
	}

}
