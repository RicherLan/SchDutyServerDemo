package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.LOGIN_REQUEST;
/*
  歧义
 */
public class ReadaddGroupResultRequestPacket extends Packet {
    int msgid;
    public ReadaddGroupResultRequestPacket() {

	}
    public ReadaddGroupResultRequestPacket(int msgid){
        this.msgid = msgid;
    }
    @Override
    public int getCommand() {

        return LOGIN_REQUEST;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }
}
