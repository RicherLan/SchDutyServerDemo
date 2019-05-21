package netty.protocol.response.friendGroupOperResponse;

import netty.protocol.Packet;
import netty.session.Session;
import netty.util.SessionUtil;
import object.UserGroup;

import static netty.protocol.command.Command.GetGroupInfoByGid_RESPONSE;

import java.util.Vector;

import db.GroupDBService;

/*
 * 	获得用户的某群的信息   一般用在更新某群信息时
 */
public class GetGroupInfoByGidResponsePacket extends Packet{
	int groupid;
	
	UserGroup UserGroup;
	
	@Override
	public int getCommand() {
		
		return GetGroupInfoByGid_RESPONSE;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public UserGroup getUserGroup() {
		return UserGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		UserGroup = userGroup;
	}

}
