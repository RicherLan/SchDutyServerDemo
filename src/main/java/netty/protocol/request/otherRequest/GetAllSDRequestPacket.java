package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetAllSD_REQUEST;

/*
    获得自己加入的所有组织的值班表
 */
public class GetAllSDRequestPacket extends Packet {

	public GetAllSDRequestPacket() {

	}
    @Override
    public int getCommand() {

        return GetAllSD_REQUEST;
    }

}
