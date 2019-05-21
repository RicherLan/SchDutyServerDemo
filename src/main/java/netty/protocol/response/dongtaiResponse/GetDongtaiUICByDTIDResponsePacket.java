package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetDongtaiUICByDTID_RESPONSE;

/*
    请求某一条动态  获得动态主人头像
 */
public class GetDongtaiUICByDTIDResponsePacket extends Packet{

	int dtid;
	byte[] uic;
	
    @Override
    public int getCommand() {

        return GetDongtaiUICByDTID_RESPONSE;
    }

	public int getDtid() {
		return dtid;
	}

	public void setDtid(int dtid) {
		this.dtid = dtid;
	}

	public byte[] getUic() {
		return uic;
	}

	public void setUic(byte[] uic) {
		this.uic = uic;
	}
    

}
