package netty.protocol.packet;


import netty.protocol.Packet;
import util.User;

import static netty.protocol.command.Command.AddMeAsFriend_PACKET;

/*
 * 添加好友   被添加方收到
 */
public class AddMeAsFriend_PACKET extends Packet{
	int msgid;
	
	String sex;
	String nickname;
	
	long time;
	String phonenumber;
	String addmsg;
	
	
	@Override
	public int getCommand() {
		
		return AddMeAsFriend_PACKET;
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


	public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddmsg() {
		return addmsg;
	}

	public void setAddmsg(String addmsg) {
		this.addmsg = addmsg;
	}
	

}
