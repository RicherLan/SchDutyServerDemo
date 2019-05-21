package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetGroupChatMsgNotRead_REQUEST;

/*
拿到群聊天  未读信息     一般是刚登陆的时候
 */
public class GetGroupChatMsgNotReadRequestPacket extends Packet {

	public GetGroupChatMsgNotReadRequestPacket() {

	}
    @Override
    public int getCommand() {

        return GetGroupChatMsgNotRead_REQUEST;
    }

}
