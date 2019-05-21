package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import object.UserInGroupInfo;

import static netty.protocol.command.Command.GetGroupAllUser_RESPONSE;

import java.util.Vector;

/*
获得某群的所有成员   没有头像
 */
public class GetGroupAllUserResponsePacket extends Packet {
	Vector<UserInGroupInfo> users;
	int groupid;
	String type;       //从哪个活动请求的
	
    @Override
    public int getCommand() {

        return GetGroupAllUser_RESPONSE;
    }

	public Vector<UserInGroupInfo> getUsers() {
		return users;
	}

	public void setUsers(Vector<UserInGroupInfo> users) {
		this.users = users;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



}
