package netty.protocol.response.otherResponse;

import netty.protocol.Packet;

import static netty.protocol.command.Command.ReadGroupChatMsg_RESPONSE;

/*
    群聊   接收方接收消息后   回执该消息已读  服务器回执
 */
public class ReADGroupChatMsgResponsePacket extends Packet {
    @Override
    public int getCommand() {

        return ReadGroupChatMsg_RESPONSE;
    }


}
