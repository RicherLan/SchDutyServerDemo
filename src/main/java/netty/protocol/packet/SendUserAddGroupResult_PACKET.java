package netty.protocol.packet;

import static netty.protocol.command.Command.SendUserAddGroupResult_PACKET;

import netty.protocol.Packet;

/*
 * 	管理员做出同意或不同意 发给用户
 */
public class SendUserAddGroupResult_PACKET extends Packet{
	
	int msgid;
	String type;
	int groupid;
	String handlerid;
	String handlernickname;
	long time;
	
	@Override
	public int getCommand() {
		
		return SendUserAddGroupResult_PACKET;
	}


	public int getMsgid() {
		return msgid;
	}


	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}


	


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getGroupid() {
		return groupid;
	}


	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}


	public String getHandlerid() {
		return handlerid;
	}


	public void setHandlerid(String handlerid) {
		this.handlerid = handlerid;
	}


	public String getHandlernickname() {
		return handlernickname;
	}


	public void setHandlernickname(String handlernickname) {
		this.handlernickname = handlernickname;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}
	

}
