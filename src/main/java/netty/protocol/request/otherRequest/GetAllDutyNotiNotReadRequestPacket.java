package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetAllDutyNotiNotRead_REQUEST;

/*
    获得自己的未读的值班的通知
 */
public class GetAllDutyNotiNotReadRequestPacket extends Packet {
	public GetAllDutyNotiNotReadRequestPacket() {

	}

	@Override
	public int getCommand() {

		return GetAllDutyNotiNotRead_REQUEST;
	}

}
