package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;

import static  netty.protocol.command.Command.GetAllFriendInfo_REQUEST;

public class GetAllFriendInfoRequestPacket extends Packet {

	public GetAllFriendInfoRequestPacket() {

	}
    @Override
    public int getCommand() {
        return GetAllFriendInfo_REQUEST;
    }

}
