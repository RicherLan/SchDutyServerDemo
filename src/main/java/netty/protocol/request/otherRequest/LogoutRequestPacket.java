package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.LOGOUT_REQUEST;

public class LogoutRequestPacket extends Packet {
	
	public LogoutRequestPacket() {

	}
    @Override
    public int getCommand() {

        return LOGOUT_REQUEST;
    }
    
    
}
