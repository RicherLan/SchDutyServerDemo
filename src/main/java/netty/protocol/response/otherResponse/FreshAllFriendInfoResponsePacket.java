package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import object.MyFriendEasy;

import static netty.protocol.command.Command.FreshAllFriendInfo_RESPONSE;

import java.util.Vector;

public class FreshAllFriendInfoResponsePacket extends Packet {

	Vector<MyFriendEasy> users;
	
    @Override
    public int getCommand() {

        return FreshAllFriendInfo_RESPONSE;
    }

	public Vector<MyFriendEasy> getUsers() {
		return users;
	}

	public void setUsers(Vector<MyFriendEasy> users) {
		this.users = users;
	}

}
