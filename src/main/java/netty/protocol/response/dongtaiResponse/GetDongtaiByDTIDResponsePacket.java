package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import object.Dongtai;

import static netty.protocol.command.Command.GetDongtaiByDTID_RESPONSE;

/*
    请求某一条动态
 */
public class GetDongtaiByDTIDResponsePacket extends Packet {
	
	Dongtai dongtai;
	
    @Override
    public int getCommand() {

        return GetDongtaiByDTID_RESPONSE;
    }

	public Dongtai getDongtai() {
		return dongtai;
	}

	public void setDongtai(Dongtai dongtai) {
		this.dongtai = dongtai;
	}

}
