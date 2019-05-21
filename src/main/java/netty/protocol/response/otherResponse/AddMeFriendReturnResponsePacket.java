package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AddMeFriendReturn_RESPONSE;
/*
用户添加自己为好友   自己给回复  同意还是不同意    服务器响应
 */
public class AddMeFriendReturnResponsePacket extends Packet {

	
	String type;
	String otherphonenumber;
	int msgid;
	String rs;
    @Override
    public int getCommand() {

        return AddMeFriendReturn_RESPONSE;
    }
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOtherphonenumber() {
		return otherphonenumber;
	}
	public void setOtherphonenumber(String otherphonenumber) {
		this.otherphonenumber = otherphonenumber;
	}
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}

}
