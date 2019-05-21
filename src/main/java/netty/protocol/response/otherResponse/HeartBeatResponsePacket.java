package netty.protocol.response.otherResponse;

import netty.protocol.Packet;

import static netty.protocol.command.Command.HEARTBEAT_RESPONSE;


public class HeartBeatResponsePacket extends Packet {
    @Override
    public int getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
