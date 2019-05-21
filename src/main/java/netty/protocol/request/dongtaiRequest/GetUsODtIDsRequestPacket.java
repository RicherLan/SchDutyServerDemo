package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetUsODtIDs_REQUEST;

/*
    进入用户资料界面   用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    //从当前的dongtaiid开始往前找6条以前的
 */
public class GetUsODtIDsRequestPacket extends Packet {

    String ph;
    int dongtaiid;
    public GetUsODtIDsRequestPacket() {

   	}
    public GetUsODtIDsRequestPacket(String ph1,int dongtaiid){
        this.ph = ph;
        this.dongtaiid = dongtaiid;
    }

    @Override
    public int getCommand() {

        return GetUsODtIDs_REQUEST;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public int getDongtaiid() {
        return dongtaiid;
    }

    public void setDongtaiid(int dongtaiid) {
        this.dongtaiid = dongtaiid;
    }
}
