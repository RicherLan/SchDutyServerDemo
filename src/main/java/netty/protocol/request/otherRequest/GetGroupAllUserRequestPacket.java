package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetGroupAllUser_REQUEST;

/*
    获得某群的所有成员   没有头像
 */
public class GetGroupAllUserRequestPacket extends Packet {
    int groupid;
    String type;       //从哪个活动请求的
    public GetGroupAllUserRequestPacket() {

	}
    public GetGroupAllUserRequestPacket(int groupid,String type){
        this.groupid = groupid;
        this.type = type;
    }
    @Override
    public int getCommand() {

        return GetGroupAllUser_REQUEST;
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
