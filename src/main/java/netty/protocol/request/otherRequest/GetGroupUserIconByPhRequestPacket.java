package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetGroupUserIconByPh_REQUEST;

/*
    获得某群的所有成员后  向服务器回执     来让服务器发头像
 */
public class GetGroupUserIconByPhRequestPacket extends Packet {
    String userph;
	int groupid;
	String type;
    public GetGroupUserIconByPhRequestPacket() {

	}
    public GetGroupUserIconByPhRequestPacket(int groupid ,String userph,String type){
        this.groupid = groupid;
        this.userph = userph;
        this.type = type;
    }
    @Override
    public int getCommand() {

        return GetGroupUserIconByPh_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
	public String getUserph() {
		return userph;
	}
	public void setUserph(String userph) {
		this.userph = userph;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    
}
