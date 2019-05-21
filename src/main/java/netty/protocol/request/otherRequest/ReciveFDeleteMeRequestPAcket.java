package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.ReciveFDeleteMe_REQUEST;

/*
好友删除自己时   自己收到被删除的消息   要回执
 */
public class ReciveFDeleteMeRequestPAcket extends Packet {
    int msgid;
    public ReciveFDeleteMeRequestPAcket() {

   	}
    public ReciveFDeleteMeRequestPAcket( int msgid){
        this.msgid = msgid;
    }
    @Override
    public int getCommand() {

        return ReciveFDeleteMe_REQUEST;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }
}
