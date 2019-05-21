package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetSDOfGid_REQUEST;

/*
    获得自己加入的某个组织的值班表
 */
public class GetSDOfGidRequestPacket extends Packet {
	int dnid;
	int groupid;
    public GetSDOfGidRequestPacket() {

	}
    public GetSDOfGidRequestPacket(int groupid,int dnid){
        this.groupid = groupid;
        this.dnid = dnid;
    }
    @Override
    public int getCommand() {

        return GetSDOfGid_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
	public int getDnid() {
		return dnid;
	}
	public void setDnid(int dnid) {
		this.dnid = dnid;
	}
	
    
}
