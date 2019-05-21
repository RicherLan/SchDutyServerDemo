package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.FreshNotification_RESPONSE;

public class FreshNotificationResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return FreshNotification_RESPONSE;
    }

}
