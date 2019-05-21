package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetPersonalInfo_REQUEST;

/*
获得个人信息     一般是刚登陆的时候
 */
public class GetPersonalInfoRequestPacket extends Packet {

	public GetPersonalInfoRequestPacket() {

	}

	@Override
	public int getCommand() {

		return GetPersonalInfo_REQUEST;
	}
}
