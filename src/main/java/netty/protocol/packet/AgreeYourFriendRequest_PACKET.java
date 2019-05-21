package netty.protocol.packet;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AgreeYourFriendRequest_PACKET;

/*
 * 添加好友
 * 对方同意了你的好友请求
 */
public class AgreeYourFriendRequest_PACKET extends Packet{
	
	int msgid;
	String phonenumber;
	String sex;
	String nickname;
	long time;
	
	@Override
	public int getCommand() {
		
		return AgreeYourFriendRequest_PACKET;
	}

	public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
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
	

}
