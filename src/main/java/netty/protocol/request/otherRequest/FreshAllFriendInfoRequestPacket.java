package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.FreshAllFriendInfo_REQUEST;

public class FreshAllFriendInfoRequestPacket extends Packet {
	public FreshAllFriendInfoRequestPacket() {

	}
    @Override
    public int getCommand() {

        return FreshAllFriendInfo_REQUEST;
    }

}
