package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetNewComByDongtaiId_RESPONSE;

/*
    进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条
 */
public class GetNewComByDongtaiIdResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return GetNewComByDongtaiId_RESPONSE;
    }

}
