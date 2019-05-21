package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.ReadSingleChatMsg_RESPONSE;

public class ReceiveSingleChatTextResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return ReadSingleChatMsg_RESPONSE;
    }
}
