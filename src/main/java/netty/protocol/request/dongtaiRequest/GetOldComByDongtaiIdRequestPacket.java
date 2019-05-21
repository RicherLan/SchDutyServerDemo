package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetOldComByDongtaiId_REQUEST;


/*
        进入某动态的所有评论界面  上拉刷新   返回根评论总共10条   大的评论下最多回执3条  没有头像
 */
public class GetOldComByDongtaiIdRequestPacket extends Packet {
    String ph;
    int dtid;
    int comid;
    public GetOldComByDongtaiIdRequestPacket() {

   	}
    public GetOldComByDongtaiIdRequestPacket(String ph,int dtid,int comid){
        this.ph = ph;
        this.dtid = dtid;
        this.comid = comid;

    }
    @Override
    public int getCommand() {

        return GetOldComByDongtaiId_REQUEST;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public int getDtid() {
        return dtid;
    }

    public void setDtid(int dtid) {
        this.dtid = dtid;
    }

    public int getComid() {
        return comid;
    }

    public void setComid(int comid) {
        this.comid = comid;
    }
}
