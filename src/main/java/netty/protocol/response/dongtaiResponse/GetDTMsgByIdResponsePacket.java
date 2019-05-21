package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetDTMsgById_RESPONSE;

/*
    获得动态消息的内容
 */
public class GetDTMsgByIdResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return GetDTMsgById_RESPONSE;
    }
}
