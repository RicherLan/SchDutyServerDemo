package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.ReciveDongtaiComment_RESPONSE;

/*
        收到了别人给自己的动态的评论  回执服务器已读
 */
public class ReciveDongtaiCommentResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return ReciveDongtaiComment_RESPONSE;
    }


}
