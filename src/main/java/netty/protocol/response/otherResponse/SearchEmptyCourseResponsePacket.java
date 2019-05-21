package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import object.UserCorp;

import static netty.protocol.command.Command.SearchEmptyCourse_RESPONSE;

import java.util.Vector;

/*
    社团组织获得   获得某几节课都有空的人
 */
public class SearchEmptyCourseResponsePacket extends Packet {

	Vector<UserCorp> userCorps;
	int groupid;
    @Override
    public int getCommand() {

        return SearchEmptyCourse_RESPONSE;
    }
	public Vector<UserCorp> getUserCorps() {
		return userCorps;
	}
	public void setUserCorps(Vector<UserCorp> userCorps) {
		this.userCorps = userCorps;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

}
