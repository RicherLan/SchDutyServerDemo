package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SendSingleChatText_RESPONSE;

public class SendSingleChatTextResponsePacket extends Packet {
    @Override
    public int getCommand() {

        return SendSingleChatText_RESPONSE;
    }

}
