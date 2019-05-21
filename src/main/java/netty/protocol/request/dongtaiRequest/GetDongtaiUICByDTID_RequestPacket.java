package netty.protocol.request.dongtaiRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetDongtaiUICByDTID_REQUEST;

/*
    请求某一条动态  获得动态主人头像
 */
public class GetDongtaiUICByDTID_RequestPacket extends Packet{

	int dtid;
    String ph;
    public GetDongtaiUICByDTID_RequestPacket() {

	}
    public GetDongtaiUICByDTID_RequestPacket(String ph,int dtid){
        this.ph = ph;
        this.dtid = dtid;
    }

    @Override
    public int getCommand() {

        return GetDongtaiUICByDTID_REQUEST;
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
    
}
