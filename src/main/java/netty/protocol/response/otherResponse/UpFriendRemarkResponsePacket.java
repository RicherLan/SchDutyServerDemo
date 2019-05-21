package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.UpFriendRemark_RESPONSE;

/*
    修改好友的备注
 */
public class UpFriendRemarkResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return UpFriendRemark_RESPONSE;
    }

}
