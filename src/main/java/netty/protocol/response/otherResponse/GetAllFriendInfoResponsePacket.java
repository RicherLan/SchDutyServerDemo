package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import object.MyFriendEasy;

import static netty.protocol.command.Command.GetAllFriendInfo_RESPONSE;

import java.util.Vector;

/*
 * 获得自己的所有好友信息    一般是刚登陆
 */
public class GetAllFriendInfoResponsePacket extends Packet {
	Vector<MyFriendEasy> users;
	
    @Override
    public int getCommand() {

        return GetAllFriendInfo_RESPONSE;
    }

	public Vector<MyFriendEasy> getUsers() {
		return users;
	}

	public void setUsers(Vector<MyFriendEasy> users) {
		this.users = users;
	}

}
