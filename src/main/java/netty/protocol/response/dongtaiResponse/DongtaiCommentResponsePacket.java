package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.DongtaiComment_RESPONSE;

/*
    给动态评论
 */
public class DongtaiCommentResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return DongtaiComment_RESPONSE;
    }

}
