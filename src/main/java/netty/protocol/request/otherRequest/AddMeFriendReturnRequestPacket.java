package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.AddMeFriendReturn_REQUEST;
/*
用户添加自己为好友   自己给回复  同意还是不同意
 */
public class AddMeFriendReturnRequestPacket extends Packet {

    int msgid;
    String otherphonenumber;
    String othernickname;
    String result;
    public AddMeFriendReturnRequestPacket() {

	}
    public AddMeFriendReturnRequestPacket(int msgid,String otherphonenumber,String othernickname,String result){
        this.msgid = msgid;
        this.otherphonenumber = otherphonenumber;
        this.othernickname = othernickname;
        this.result = result;
    }

    @Override
    public int getCommand() {

        return AddMeFriendReturn_REQUEST;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public String getOtherphonenumber() {
        return otherphonenumber;
    }

    public void setOtherphonenumber(String otherphonenumber) {
        this.otherphonenumber = otherphonenumber;
    }

    public String getOthernickname() {
        return othernickname;
    }

    public void setOthernickname(String othernickname) {
        this.othernickname = othernickname;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
