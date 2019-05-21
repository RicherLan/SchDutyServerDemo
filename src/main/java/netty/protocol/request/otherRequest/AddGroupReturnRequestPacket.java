package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AddGroupReturn_REQUEST;

/*
用户申请加群时   管理员同意或不同意   回执
 */
public class AddGroupReturnRequestPacket extends Packet {
	int msgid;
	String otherphonenumber;
	int groupid;
	String rs;

	public AddGroupReturnRequestPacket() {

	}

	public AddGroupReturnRequestPacket(int msgid, String otherphonenumber, int groupid, String rs) {
		this.msgid = msgid;
		this.otherphonenumber = otherphonenumber;
		this.groupid = groupid;
		this.rs = rs;
	}

	@Override
	public int getCommand() {

		return AddGroupReturn_REQUEST;
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

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}
}
