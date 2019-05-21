package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetUDtICByDTICID_RESPONSE;

/*
    进入用户资料界面  请求某一条动态
    获得动态的的一张图片
 */
public class GetUDtICByDTICIDResponsePacket extends Packet{
	
	int dtid;
	String ph;
	int fileid;
	byte[] ic;
	
    @Override
    public int getCommand() {

        return GetUDtICByDTICID_RESPONSE;
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

	public byte[] getIc() {
		return ic;
	}

	public void setIc(byte[] ic) {
		this.ic = ic;
	}

	public int getFileid() {
		return fileid;
	}

	public void setFileid(int fileid) {
		this.fileid = fileid;
	}
    
}
