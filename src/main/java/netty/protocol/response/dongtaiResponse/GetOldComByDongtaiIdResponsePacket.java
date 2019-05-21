package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetOldComByDongtaiId_RESPONSE;

/*
        进入某动态的所有评论界面  上拉刷新   返回根评论总共10条   大的评论下最多回执3条  没有头像
 */
public class GetOldComByDongtaiIdResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return GetOldComByDongtaiId_RESPONSE;
    }

}
