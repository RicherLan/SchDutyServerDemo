package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import object.Dongtai;

import static netty.protocol.command.Command.GetUDtByDTID_RESPONSE;

/*
    进入用户资料界面  请求某一条动态
 */
public class GetUDtByDTIDResponsePacket extends Packet {
	Dongtai dongtai;
	
    @Override
    public int getCommand() {

        return GetUDtByDTID_RESPONSE;
    }

	public Dongtai getDongtai() {
		return dongtai;
	}

	public void setDongtai(Dongtai dongtai) {
		this.dongtai = dongtai;
	}

}
