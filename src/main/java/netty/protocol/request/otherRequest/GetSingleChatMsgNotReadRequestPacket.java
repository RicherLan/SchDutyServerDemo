package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetSingleChatMsgNotRead_REQUEST;

/*
拿到单人聊天  未读信息     一般是刚登陆的时候
 */
public class GetSingleChatMsgNotReadRequestPacket extends Packet {

	public GetSingleChatMsgNotReadRequestPacket() {

	}
    @Override
    public int getCommand() {

        return GetSingleChatMsgNotRead_REQUEST;
    }

}
