package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetOldDongtaiIDs_REQUEST;

/*
    用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    //从当前的dongtaiid开始往前找6条以前的
 */
public class GetOldDongtaiIDsRequestPacket extends Packet {

    int dongtaiid;
    public GetOldDongtaiIDsRequestPacket() {

   	}
    public GetOldDongtaiIDsRequestPacket(int dongtaiid){

        this.dongtaiid = dongtaiid;
    }
    @Override
    public int getCommand() {

        return GetOldDongtaiIDs_REQUEST;
    }



    public int getDongtaiid() {
        return dongtaiid;
    }

    public void setDongtaiid(int dongtaiid) {
        this.dongtaiid = dongtaiid;
    }
}
