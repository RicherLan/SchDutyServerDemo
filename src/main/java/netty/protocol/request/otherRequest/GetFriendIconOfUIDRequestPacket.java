package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetFriendIconOfUID_REQUEST;

/*
 * 	获得好友头像
 */
public class GetFriendIconOfUIDRequestPacket extends Packet {

	public GetFriendIconOfUIDRequestPacket() {

	}

	@Override
	public int getCommand() {

		return GetFriendIconOfUID_REQUEST;
	}

}
