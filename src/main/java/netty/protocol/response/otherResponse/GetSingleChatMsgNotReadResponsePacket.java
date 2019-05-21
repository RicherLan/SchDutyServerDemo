package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetSingleChatMsgNotRead_RESPONSE;

/*
拿到单人聊天  未读信息     一般是刚登陆的时候
 */
public class GetSingleChatMsgNotReadResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return GetSingleChatMsgNotRead_RESPONSE;
    }

}
