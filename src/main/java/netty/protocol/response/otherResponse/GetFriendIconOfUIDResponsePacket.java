package netty.protocol.response.otherResponse;


import netty.protocol.Packet;
import static  netty.protocol.command.Command.GetFriendIconOfUID_RESPONSE;

/*
 * 获得好友头像
 */
public class GetFriendIconOfUIDResponsePacket extends Packet{
	@Override
	public int getCommand() {
		
		return GetFriendIconOfUID_RESPONSE;
	}
	
}
