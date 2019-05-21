package netty.protocol.request.dongtaiRequest;

import netty.protocol.Packet;

import static netty.protocol.command.Command.GetUDtICByDTICID_REQUEST;

/*
    进入用户资料界面  请求某一条动态    获得图片
 */
public class GetUDtICByDTICIDRequestPacket extends Packet{

    int dtid;
    String ph;
    int fileid;
    public GetUDtICByDTICIDRequestPacket() {

   	}
    public GetUDtICByDTICIDRequestPacket(int dtid,String ph,int fileid){
        this.dtid = dtid;
        this.ph = ph;
        this.fileid = fileid;
    }
    @Override
    public int getCommand() {

        return GetUDtICByDTICID_REQUEST;
    }

    public int getDtid() {
        return dtid;
    }

    public void setDtid(int dtid) {
        this.dtid = dtid;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }
}
