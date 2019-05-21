package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.UpGroupRemark_REQUEST;

/*
修改自己的群名片
 */
public class UpGroupRemarkRequestPacket extends Packet {
    int groupid ;
    String remark;
    public UpGroupRemarkRequestPacket() {

   	}
    public UpGroupRemarkRequestPacket(int groupid ,String remark){
        this.groupid = groupid;
        this.remark = remark;
    }
    @Override
    public int getCommand() {
    	 
        return UpGroupRemark_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
