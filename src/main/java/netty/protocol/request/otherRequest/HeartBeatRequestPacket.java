package netty.protocol.request.otherRequest;

import netty.protocol.Packet;

import static netty.protocol.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {
	public HeartBeatRequestPacket() {

	}

	@Override
	public int getCommand() {
		return HEARTBEAT_REQUEST;
	}
}
