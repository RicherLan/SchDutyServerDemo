package netty.protocol.packet;

import static netty.protocol.command.Command.SingleChatFile_PACKET;

import netty.protocol.Packet;

/*
 * 	单人聊天发送消息   图片语音  信息包
 */
public class SingleChatFile_PACKET extends Packet{
	
	int msgid;
	String senderid;
	String msgtype;
	long msgtime;
	double voicetime;
	byte[] bs;
	
	@Override
	public int getCommand() {
		
		return SingleChatFile_PACKET;
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
