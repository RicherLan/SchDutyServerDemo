package netty.protocol.packet;

import netty.protocol.Packet;

import static  netty.protocol.command.Command.GroupChatText_PACKET;

/*
 * 	群聊发送消息   字符串  接收方接收
 */
public class GroupChatText_PACKET extends Packet{
	int msgid;
	String senderid;
	String sendergroupname;
	int groupid;
	String message;
	String msgtype;
	long msgtime;
	
	@Override
	public int getCommand() {
		
		return GroupChatText_PACKET;
	}

	public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
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

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
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

	public long getMsgtime() {
		return msgtime;
	}

	public void setMsgtime(long msgtime) {
		this.msgtime = msgtime;
	}
	

}
