package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import object.UserGroup;

import static netty.protocol.command.Command.GetGroupsInfoOfUser_RESPONSE;

import java.util.Vector;

/*
 * 	获得某用户的群的基本信息
 */

public class GetGroupsInfoOfUserResponsePacket extends Packet {
	Vector<UserGroup> userGroups;
	
    @Override
    public int getCommand() {

        return GetGroupsInfoOfUser_RESPONSE;
    }

	public Vector<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Vector<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}
    
}
