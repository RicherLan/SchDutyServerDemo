package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.ReadSingleChatMsg_REQUEST;

public class ReadSingleChatMsgRequestPacket extends Packet {

    String senderid;
    int msgid;
    public ReadSingleChatMsgRequestPacket() {

   	}
    public ReadSingleChatMsgRequestPacket(String senderid, int msgid){
        this.senderid = senderid;
        this.msgid = msgid;

    }


    @Override
    public int getCommand() {

        return ReadSingleChatMsg_REQUEST;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }
}
