package netty.protocol.request.otherRequest;

import netty.protocol.Packet;

import static netty.protocol.command.Command.SendGroupChatText_REQUEST;



public class SendGroupChatTextRequestPacket  extends Packet {

    int groupid;
    String senderid ;
    String sendergroupname;
    String message ;
    String msgtype ;
    long time ;

    @Override
    public int getCommand() {
        return SendGroupChatText_REQUEST;
    }
    public SendGroupChatTextRequestPacket() {

	}
    public SendGroupChatTextRequestPacket(int groupid,String senderid ,String sendergroupname,
                                          String message ,String msgtype ,long time ){
        this.groupid = groupid;
        this.senderid = senderid;
        this.sendergroupname = sendergroupname;
        this.message = message;
        this.msgtype = msgtype;
        this.time = time;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getSendergroupname() {
        return sendergroupname;
    }

    public void setSendergroupname(String sendergroupname) {
        this.sendergroupname = sendergroupname;
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
