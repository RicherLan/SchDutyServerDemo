package netty.protocol.packet;

import static netty.protocol.command.Command.GroupChatFile_PACKET;

import netty.protocol.Packet;
/*
 * 	群聊发送消息   语音 照片  接收方接收
 */
public class GroupChatFile_PACKET extends Packet{
	
	int msgid;
	String senderid;
	String sendergroupname;
	int groupid;
	
	String msgtype;
	long msgtime;
	double voicetime;
	
	byte[] bs;
	
	@Override
	public int getCommand() {
		
		return GroupChatFile_PACKET;
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

	public double getVoicetime() {
		return voicetime;
	}

	public void setVoicetime(double voicetime) {
		this.voicetime = voicetime;
	}

	public byte[] getBs() {
		return bs;
	}

	public void setBs(byte[] bs) {
		this.bs = bs;
	}
	
}
