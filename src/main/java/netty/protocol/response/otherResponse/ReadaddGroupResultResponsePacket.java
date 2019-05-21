package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.ReadAddGroupResult_RESPONSE;


/*
歧义
 */
public class ReadaddGroupResultResponsePacket extends Packet {


    @Override
    public int getCommand() {

        return ReadAddGroupResult_RESPONSE;
    }

}
