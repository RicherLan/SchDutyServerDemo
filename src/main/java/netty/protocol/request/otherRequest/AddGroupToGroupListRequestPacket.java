package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.AddGroupToGroupList_REQUEST;

/*
    加群  或  创建群成功后    把该群的信息加入到自己的群列表中
 */
public class AddGroupToGroupListRequestPacket extends Packet {
    int groupid;
    public AddGroupToGroupListRequestPacket() {

	}
    public AddGroupToGroupListRequestPacket(int groupid ){
        this.groupid = groupid;
    }
    @Override
    public int getCommand() {

        return AddGroupToGroupList_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
}
