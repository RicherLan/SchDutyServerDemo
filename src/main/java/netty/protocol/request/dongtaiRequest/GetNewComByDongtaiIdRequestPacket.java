package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetNewComByDongtaiId_REQUEST;

/*
        进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条
 */
public class GetNewComByDongtaiIdRequestPacket extends Packet {
    int dongtaiid;
    public GetNewComByDongtaiIdRequestPacket() {

   	}
    public GetNewComByDongtaiIdRequestPacket(int dongtaiid){
        this.dongtaiid = dongtaiid;
    }
    @Override
    public int getCommand() {

        return GetNewComByDongtaiId_REQUEST;
    }

    public int getDongtaiid() {
        return dongtaiid;
    }

    public void setDongtaiid(int dongtaiid) {
        this.dongtaiid = dongtaiid;
    }
}
