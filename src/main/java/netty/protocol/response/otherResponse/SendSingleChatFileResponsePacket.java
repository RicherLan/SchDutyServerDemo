package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SendSingleChatFile_RESPONSE;


public class SendSingleChatFileResponsePacket extends Packet {
			
	String result;
	long msgtime;
    @Override
    public int getCommand() {

        return SendSingleChatFile_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public long getMsgtime() {
		return msgtime;
	}
	public void setMsgtime(long msgtime) {
		this.msgtime = msgtime;
	}

}
