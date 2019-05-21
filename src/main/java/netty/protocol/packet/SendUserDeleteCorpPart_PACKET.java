package netty.protocol.packet;

import static netty.protocol.command.Command.SendUserDeleteCorpPart_PACKET;

import netty.protocol.Packet;

/*
 * 	删除部门 还要通知该部室的成员 暂时只把成员的部室设为空 不进行通知
 */
public class SendUserDeleteCorpPart_PACKET extends Packet{
	
	int groupid;
	String name;
	
	@Override
	public int getCommand() {
		
		return SendUserDeleteCorpPart_PACKET;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
