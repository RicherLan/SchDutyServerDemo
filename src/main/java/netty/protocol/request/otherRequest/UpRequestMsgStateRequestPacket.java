package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.UpRequestMsgState_REQUEST;

/*
好友或群请求   收到消息后   更改消息的读取状态
 */
public class UpRequestMsgStateRequestPacket extends Packet {

    int msgid;
    public UpRequestMsgStateRequestPacket() {

   	}
    public UpRequestMsgStateRequestPacket(int msgid){
        this.msgid = msgid;
    }
    @Override
    public int getCommand() {

        return UpRequestMsgState_REQUEST;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }
}
