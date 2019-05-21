package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SearchAddGroup_REQUEST;

/*
客户端添加群时   搜索账号 只需要看到其账号 网名 头像就行
 */
public class SearchAddGroupRequestPacket extends Packet {

	int groupid;

	public SearchAddGroupRequestPacket() {

	}

	public SearchAddGroupRequestPacket(int groupid) {
		this.groupid = groupid;
	}

	@Override
	public int getCommand() {

		return SearchAddGroup_REQUEST;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
}
