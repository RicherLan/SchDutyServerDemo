package netty.protocol.packet;

import static netty.protocol.command.Command.SendAllUsersDutyNotiNO_PACKET;

import netty.protocol.Packet;

/*
 * 	安排值班表
 * 把值班结果的编号发送给该社团的所有人
 */
public class SendAllUsersDutyNotiNO_PACKET extends Packet{
	
	
	int groupid;
	int dnid;
	long time;
	String corpname;
	String myduty;
	
	
	@Override
	public int getCommand() {
		
		return SendAllUsersDutyNotiNO_PACKET;
	}


	public int getGroupid() {
		return groupid;
	}


	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}


	public int getDnid() {
		return dnid;
	}


	public void setDnid(int dnid) {
		this.dnid = dnid;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}


	public String getCorpname() {
		return corpname;
	}


	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}


	public String getMyduty() {
		return myduty;
	}


	public void setMyduty(String myduty) {
		this.myduty = myduty;
	}
	
}
