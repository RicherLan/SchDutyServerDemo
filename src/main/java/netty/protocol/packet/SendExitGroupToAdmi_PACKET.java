package netty.protocol.packet;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SendExitGroupToAdmi_PACKET;

/*
 *  用户退群   发给管理员
 */
public class SendExitGroupToAdmi_PACKET extends Packet{
	
	int msgid;
	int groupid;
	String groupname;
	String ph;
	String groupnickname;
	long time;
	
	
	@Override
	public int getCommand() {
		
		return SendExitGroupToAdmi_PACKET;
	}


	public int getMsgid() {
		return msgid;
	}


	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}


	public int getGroupid() {
		return groupid;
	}


	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}


	public String getGroupname() {
		return groupname;
	}


	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}


	public String getPh() {
		return ph;
	}


	public void setPh(String ph) {
		this.ph = ph;
	}


	public String getGroupnickname() {
		return groupnickname;
	}


	public void setGroupnickname(String groupnickname) {
		this.groupnickname = groupnickname;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}
	

}
