package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SendSingleChatFile_REQUEST;

public class SendSingleChatFileRequestPacket  extends Packet{

    String senderid ;
    String reciverid ;
    String message ;
    String msgtype ;
    long time ;
    double voicetime;
    byte[] bytes;
    public SendSingleChatFileRequestPacket() {

	}
    public SendSingleChatFileRequestPacket(String senderid, String reciverid ,
                                          String message, String msgtype,long time ,double voicetime ,byte[] bytes){


        this.senderid = senderid;
        this.reciverid = reciverid;
        this.message = message;
        this.msgtype = msgtype;
        this.time = time;
        this.voicetime = voicetime;
        this.bytes = bytes;
    }

    @Override
    public int getCommand() {

        return SendSingleChatFile_REQUEST;
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
