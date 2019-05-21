package netty.protocol.request.dongtaiRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetDongtaiICByDTID_REQUEST;

/*
    请求某一条动态  获得动态的图片
 */
public class GetDongtaiICByDTIDRequestPacket extends Packet{

    int dtid;
    int fileid;
    public GetDongtaiICByDTIDRequestPacket() {

	}
    public GetDongtaiICByDTIDRequestPacket(int dtid,int fileid){
        this.dtid = dtid;
        this.fileid = fileid;
    }
    @Override
    public int getCommand() {

        return GetDongtaiICByDTID_REQUEST;
    }

    public int getDtid() {
        return dtid;
    }

    public void setDtid(int dtid) {
        this.dtid = dtid;
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }
}
