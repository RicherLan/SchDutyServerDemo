package netty.protocol.packet;

import static netty.protocol.command.Command.DisAgreeYourFriendRequest_PACKET;

import netty.protocol.Packet;

/*
 * 添加好友
 * 对方不同意你的好友请求
 */
public class DisAgreeYourFriendRequest_PACKET extends Packet{
	int msgid;
	String phonenumber;
	String sex;
	String nickname;
	long time;
	
	@Override
	public int getCommand() {
		
		return DisAgreeYourFriendRequest_PACKET;
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
