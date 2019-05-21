package netty.protocol.response.personInfoResponse;

import static netty.protocol.command.Command.GetIndexInfoOfPh_RESPONSE;

import netty.protocol.Packet;
import util.User;

/*
 * 	进入某人的个人页面时  获得其基本信息
 */
public class GetIndexInfoOfPhResponsePacket extends Packet {
	User user;
	@Override
	public int getCommand() {

		return GetIndexInfoOfPh_RESPONSE;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
