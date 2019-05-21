package netty.protocol.packet;

import static netty.protocol.command.Command.SendAdmiAddGroup_Packet;

import netty.protocol.Packet;

/*
 * 	加群请求发给管理员
 */
public class SendAdmiAddGroup_Packet extends Packet{
	
	int msgid;
	int groupid;
	String ph;
	String sex;
	String  nickname;
	long time;
	String addmsg;
	
	@Override
	public int getCommand() {
		
		return SendAdmiAddGroup_Packet;
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

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getAddmsg() {
		return addmsg;
	}

	public void setAddmsg(String addmsg) {
		this.addmsg = addmsg;
	}
	

}
