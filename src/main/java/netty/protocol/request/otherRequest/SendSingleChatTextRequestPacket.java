package netty.protocol.request.otherRequest;
import netty.protocol.Packet;

import static netty.protocol.command.Command.SendSingleChatText_REQUEST;

public class SendSingleChatTextRequestPacket extends Packet{

    String senderid ;
    String reciverid ;
    String message;
    String msgtype;
    long time;
    public SendSingleChatTextRequestPacket() {

   	}
    public SendSingleChatTextRequestPacket( String senderid ,String reciverid ,String message,String msgtype,long time){
        this.senderid = senderid;
        this.reciverid = reciverid;
        this.message = message;
        this.msgtype = msgtype;
        this.time = time;

    }

    @Override
    public int getCommand() {

        return SendSingleChatText_REQUEST;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getReciverid() {
        return reciverid;
    }

    public void setReciverid(String reciverid) {
        this.reciverid = reciverid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
