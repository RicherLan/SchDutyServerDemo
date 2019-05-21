package netty.protocol.request.otherRequest;

import netty.protocol.Packet;

import static netty.protocol.command.Command.ReadGroupChatMsg_REQUEST;

/*
群聊   接收方接收消息后   回执该消息已读
 */
public class ReadGroupChatMsgRequestPacket extends Packet {

    int msgid;
    int groupid;
    public ReadGroupChatMsgRequestPacket() {

   	}
    public ReadGroupChatMsgRequestPacket(int msgid, int groupid){
        this.msgid = msgid;
        this.groupid = groupid;

    }

    @Override
    public int getCommand() {

        return ReadGroupChatMsg_REQUEST;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
}
