package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.UpPersonalIcon_REQUEST;

/*
更改自己的头像
 */
public class UpPersonalIconRequestPacket extends Packet {

    byte[] bytes;
    public UpPersonalIconRequestPacket() {

   	}
    public UpPersonalIconRequestPacket(byte[] bytes){
        this.bytes = bytes;
    }

    @Override
    public int getCommand() {

        return UpPersonalIcon_REQUEST;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
