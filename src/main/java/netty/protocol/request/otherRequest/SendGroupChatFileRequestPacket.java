package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SendGroupChatFile_REQUEST;

public class SendGroupChatFileRequestPacket extends Packet {

	int groupid;
	String senderid;
	String sendergroupname;
	String message;
	String msgtype;
	long time;
	double voicetime;
	byte[] bytes;

	public SendGroupChatFileRequestPacket() {

	}

	public SendGroupChatFileRequestPacket(int groupid, String senderid, String sendergroupname, String message,
			String msgtype, long time, double voicetime, byte[] bytes) {

		this.groupid = groupid;
		this.senderid = senderid;
		this.sendergroupname = sendergroupname;
		this.message = message;
		this.msgtype = msgtype;
		this.time = time;
		this.voicetime = voicetime;
		this.bytes = bytes;
	}

	@Override
	public int getCommand() {

		return SendGroupChatFile_REQUEST;
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

	public double getVoicetime() {
		return voicetime;
	}

	public void setVoicetime(double voicetime) {
		this.voicetime = voicetime;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
