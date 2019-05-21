package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetOldDongtaiMsgIDs_REQUEST;

/*
        用户上拉刷新动态消息的页面  就是加载旧的动态消息        返回6条动态消息的id
    //从当前的id开始往前找6条以前的
 */
public class GetOldDongtaiMsgIDsRequestPacket extends Packet {
    int dongtaimsgid;
    public GetOldDongtaiMsgIDsRequestPacket() {

   	}
    public GetOldDongtaiMsgIDsRequestPacket(int dongtaimsgid){
        this.dongtaimsgid = dongtaimsgid;
    }
    @Override
    public int getCommand() {

        return GetOldDongtaiMsgIDs_REQUEST;
    }

    public int getDongtaimsgid() {
        return dongtaimsgid;
    }

    public void setDongtaimsgid(int dongtaimsgid) {
        this.dongtaimsgid = dongtaimsgid;
    }
}
