package netty.protocol.packet;

import static netty.protocol.command.Command.SendAdmiAddGroupHandlered_PACKET;

import netty.protocol.Packet;

/*
 * 	通知其他管理员 加群请求已经被其他管理员处理了
 */
public class SendAdmiAddGroupHandlered_PACKET extends Packet{

	int groupid;
	String handlerid;
	String handlernickname;
	String otherphonenumber;
	String othernickname;
	String type;               //同意还是拒绝
	
	@Override
	public int getCommand() {
		
		return SendAdmiAddGroupHandlered_PACKET;
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

	public String getOtherphonenumber() {
		return otherphonenumber;
	}

	public void setOtherphonenumber(String otherphonenumber) {
		this.otherphonenumber = otherphonenumber;
	}

	public String getOthernickname() {
		return othernickname;
	}

	public void setOthernickname(String othernickname) {
		this.othernickname = othernickname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
