package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.UpFriendRemark_REQUEST;

/*
    修改好友的备注
 */
public class UpFriendRemarkRequestPacket extends Packet {
    String friendph ;
    String remark;
    public UpFriendRemarkRequestPacket() {

   	}
    public UpFriendRemarkRequestPacket(String friendph ,String remark){
        this.friendph = friendph;
        this.remark = remark;
    }
    @Override
    public int getCommand() {

        return UpFriendRemark_REQUEST;
    }

    public String getFriendph() {
        return friendph;
    }

    public void setFriendph(String friendph) {
        this.friendph = friendph;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
