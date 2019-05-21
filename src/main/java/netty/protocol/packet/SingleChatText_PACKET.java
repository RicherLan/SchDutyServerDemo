package netty.protocol.packet;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SingleChatText_PACKET;

/*
 * 单人聊天发送消息   字符串  信息包
 */
public class SingleChatText_PACKET extends Packet{
	

	int msgid;
	String senderid;
	String message;
	String msgtype;
	long msgtime;
	
	@Override
	public int getCommand() {
		
		return SingleChatText_PACKET;
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
