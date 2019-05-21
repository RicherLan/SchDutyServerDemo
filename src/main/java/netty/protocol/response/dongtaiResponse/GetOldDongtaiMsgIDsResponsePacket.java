package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetOldDongtaiMsgIDs_RESPONSE;

/*
        用户上拉刷新动态消息的页面  就是加载旧的动态消息        返回6条动态消息的id
    //从当前的id开始往前找6条以前的
 */
public class GetOldDongtaiMsgIDsResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return GetOldDongtaiMsgIDs_RESPONSE;
    }
}
