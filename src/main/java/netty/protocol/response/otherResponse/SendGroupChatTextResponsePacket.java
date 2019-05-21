package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SendGroupChatText_RESPONSE;




public class SendGroupChatTextResponsePacket extends Packet{

	
	
    @Override
    public int getCommand() {
        return SendGroupChatText_RESPONSE;
    }

	
    
}
