package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetDongtaiICByDTID_RESPONSE;

/*
    请求某一条动态  获得动态的图片
 */
public class GetDongtaiICByDTID_ResponsePacket extends Packet{

    int dtid;
    int fileid;
    byte[] ic;
    @Override
    public int getCommand() {

        return GetDongtaiICByDTID_RESPONSE;
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

	public byte[] getIc() {
        return ic;
    }

    public void setIc(byte[] ic) {
        this.ic = ic;
    }
}
