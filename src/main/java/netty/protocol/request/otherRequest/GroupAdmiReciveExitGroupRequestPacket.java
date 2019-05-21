package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GroupAdmiReciveExitGroup_REQUEST;
/*
群成员退群时   群管理员收到退群消息时   回执
 */
public class GroupAdmiReciveExitGroupRequestPacket extends Packet {

    int msgid;
    public GroupAdmiReciveExitGroupRequestPacket() {

	}
    public GroupAdmiReciveExitGroupRequestPacket(int msgid){
        this.msgid = msgid;
    }
    @Override
    public int getCommand() {
    	
        return GroupAdmiReciveExitGroup_REQUEST;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }
}
